package oui.sncf.xspeedit.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XspeedItUtilsTU {

    /**
     * Test de la méthode statique findNbBoxes.
     */
    @Test
    public void findNbBoxesTest() {
        Integer nbBoxes = XspeedItUtils.findNbBoxes("163/8/41/6/8/9/52/5/7/73", XspeedItConstant.BOXES_DELIMITER);
        assertEquals(10, (int) nbBoxes);

        Integer nbBoxes2 = XspeedItUtils.findNbBoxes("163-8-41-6-8-9-52-5-7-73", "-");
        assertEquals(10, (int) nbBoxes2);

        Integer nbBoxes3 = XspeedItUtils.findNbBoxes("", "-");
        assertEquals(0, (int) nbBoxes3);

        Integer nbBoxes4 = XspeedItUtils.findNbBoxes(null, "-");
        assertEquals(0, (int) nbBoxes4);
    }

    /**
     * Test que le constructeur de XspeedItUtils est bien privé.
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        Constructor<XspeedItUtils> constructor = XspeedItUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
