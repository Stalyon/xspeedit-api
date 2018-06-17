package oui.sncf.xspeedit.service;

import oui.sncf.xspeedit.entity.CalculatorComparator;
import oui.sncf.xspeedit.exception.XspeedItException;

public interface CalculatorComparatorService {

    /**
     * Permet de comparer les performances de CalculatorActual et CalculatorOptimized pour les mêmes articles, en
     *  précisant le délimiteur entre les articles, la capacité des cartons ainsi que le délimiteur entre les cartons.
     *
     * @param items
     * @param itemsRegexDelimiter
     * @param boxesDelimiter
     * @param boxesCapacity
     * @return
     * @throws XspeedItException
     */
    CalculatorComparator compareCalculator(String items, String itemsRegexDelimiter, String boxesDelimiter,
                                           Integer boxesCapacity) throws XspeedItException;
}
