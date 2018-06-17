package oui.sncf.xspeedit.utils;

import org.springframework.util.StringUtils;

public class XspeedItUtils {

    /**
     * Constructeur privé, ne doit pas être appelé.
     */
    private XspeedItUtils() {
        // Do nothing
    }

    public static Integer findNbBoxes(String boxes, String boxesDelimiter) {
        return (boxes == null || "".equals(boxes)) ? 0 : StringUtils.countOccurrencesOf(boxes, boxesDelimiter) +1;
    }
}
