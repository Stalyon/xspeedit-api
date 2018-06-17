package oui.sncf.xspeedit.service;

import oui.sncf.xspeedit.exception.XspeedItException;

public interface CalculatorService {

    /**
     * Permet de répartir des articles dans des cartons de façon à remplir les cartons au maximum de leur capacité, tout
     * en précisant le délimiteur entre les articles, la capacité des cartons ainsi que le délimiteur entre les cartons.
     *
     * @param items
     * @param itemsRegexDelimiter
     * @param boxesDelimiter
     * @param boxesCapacity
     * @return
     * @throws XspeedItException
     */
    String organizeTheBoxes(String items, String itemsRegexDelimiter, String boxesDelimiter, Integer boxesCapacity)
            throws XspeedItException;
}
