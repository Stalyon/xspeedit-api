package oui.sncf.xspeedit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.utils.XspeedItConstant;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorServiceTU {

    @Autowired
    @Qualifier("calculatorActualService")
    private CalculatorService calculatorActualService;

    @Autowired
    @Qualifier("calculatorOptimizedService")
    private CalculatorService calculatorOptimizedService;

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService.
     */
    @Test
    public void organizeTheBoxesWithActualCalculatorTest1() throws XspeedItException {
        String boxes = calculatorActualService.organizeTheBoxes("163841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("163/8/41/6/8/9/52/5/7/73", boxes);

        String boxes2 = calculatorActualService.organizeTheBoxes("9999999", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("9/9/9/9/9/9/9", boxes2);

        String boxes3 = calculatorActualService.organizeTheBoxes("11111111111111111111", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("1111111111/1111111111", boxes3);

        String boxes4 = calculatorActualService.organizeTheBoxes(null, XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("", boxes4);

        String boxes5 = calculatorActualService.organizeTheBoxes("", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("", boxes5);

        String boxes6 = calculatorActualService.organizeTheBoxes("1,6,3,8,4,1,6,8,9,5,2,5,7,7,3", ",", "-", 13);
        assertEquals("163-841-6-8-9-525-7-73", boxes6);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithActualCalculatorTest2() throws XspeedItException {
        String boxes = calculatorActualService.organizeTheBoxes("1638x1689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithActualCalculatorTest3() throws XspeedItException {
        String boxes = calculatorActualService.organizeTheBoxes("163 841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithActualCalculatorTest4() throws XspeedItException {
        String boxes = calculatorActualService.organizeTheBoxes("163841689525773", "d",
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithActualCalculatorTest5() throws XspeedItException {
        String boxes = calculatorActualService.organizeTheBoxes("163841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, -4);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorOptimizedService.
     */
    @Test
    public void organizeTheBoxesWithOptimizedCalculatorTest() throws XspeedItException {
        String boxes = calculatorOptimizedService.organizeTheBoxes("163841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("91/82/81/73/73/64/6/55", boxes);

        String boxes2 = calculatorOptimizedService.organizeTheBoxes("91399", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("91/9/9/3", boxes2);

        String boxes3 = calculatorOptimizedService.organizeTheBoxes("4123412323212", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("442/3331/222211", boxes3);

        String boxes4 = calculatorOptimizedService.organizeTheBoxes("9999999", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("9/9/9/9/9/9/9", boxes4);

        String boxes5 = calculatorOptimizedService.organizeTheBoxes("11111111111111111111", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("1111111111/1111111111", boxes5);

        String boxes6 = calculatorOptimizedService.organizeTheBoxes(null, XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("", boxes6);

        String boxes7 = calculatorOptimizedService.organizeTheBoxes("", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("", boxes7);

        String boxes8 = calculatorOptimizedService.organizeTheBoxes("1,6,3,8,4,1,6,8,9,5,2,5,7,7,3", ",", "-", 13);
        assertEquals("94-85-85-76-76-33211", boxes8);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithOptimizedCalculatorTest2() throws XspeedItException {
        String boxes = calculatorOptimizedService.organizeTheBoxes("1638x1689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithOptimizedCalculatorTest3() throws XspeedItException {
        String boxes = calculatorOptimizedService.organizeTheBoxes("163 841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithOptimizedCalculatorTest4() throws XspeedItException {
        String boxes = calculatorOptimizedService.organizeTheBoxes("163 841689525773", "d",
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
    }

    /**
     * Test de organizeTheBoxes pour l'implémentation calculatorActualService :
     *  -> doit throw une XspeedItException.
     */
    @Test(expected = XspeedItException.class)
    public void organizeTheBoxesWithOptimizedCalculatorTest5() throws XspeedItException {
        String boxes = calculatorOptimizedService.organizeTheBoxes("163 841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, -4);
    }
}
