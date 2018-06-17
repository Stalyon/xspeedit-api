package oui.sncf.xspeedit.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import oui.sncf.xspeedit.entity.CalculatorComparator;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.utils.XspeedItConstant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorComparatorServiceTU {

    @MockBean
    @Qualifier("calculatorActualService")
    private CalculatorService calculatorActualService;

    @MockBean
    @Qualifier("calculatorOptimizedService")
    private CalculatorService calculatorOptimizedService;

    @Autowired
    private CalculatorComparatorService calculatorComparatorService;

    @Before
    public void setup() {
        initMocks(this);
    }

    /**
     * Test de compareCalculator.
     */
    @Test
    public void compareCalculatorTest() throws XspeedItException {
        // Préparation des mocks
        when(calculatorActualService.organizeTheBoxes("14",
                XspeedItConstant.DEFAULT_SPLIT_REGEX, XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY))
                .thenReturn("14");
        when(calculatorOptimizedService.organizeTheBoxes("14",
                XspeedItConstant.DEFAULT_SPLIT_REGEX, XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY))
                .thenReturn("14");

        CalculatorComparator calculatorComparator = calculatorComparatorService.compareCalculator("14",
                XspeedItConstant.DEFAULT_SPLIT_REGEX, XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        assertEquals("14", calculatorComparator.getBoxesActualCalculator());
        assertEquals("14", calculatorComparator.getBoxesOptimizedCalculator());
        assertEquals(1, (int) calculatorComparator.getNbBoxesActualCalculator());
        assertEquals(1, (int) calculatorComparator.getNbBoxesOptimalCalculator());
        assertEquals(0, (int) calculatorComparator.getNbBoxesSaved());
    }
}
