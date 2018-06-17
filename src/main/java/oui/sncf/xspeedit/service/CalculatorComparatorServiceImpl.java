package oui.sncf.xspeedit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import oui.sncf.xspeedit.entity.CalculatorComparator;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.utils.XspeedItUtils;

@Service
public class CalculatorComparatorServiceImpl implements CalculatorComparatorService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("calculatorOptimizedService")
    private CalculatorService calculatorOptimizedService;

    @Autowired
    @Qualifier("calculatorActualService")
    private CalculatorService calculatorActualService;

    @Override
    public CalculatorComparator compareCalculator(String items, String itemsRegexDelimiter, String boxesDelimiter,
                                                  Integer boxesCapacity) throws XspeedItException {
        // Organisation des cartons
        String boxesOptimizedCalculator = calculatorOptimizedService.organizeTheBoxes(items, itemsRegexDelimiter,
                boxesDelimiter, boxesCapacity);
        String boxesActualCalculator = calculatorActualService.organizeTheBoxes(items, itemsRegexDelimiter,
                boxesDelimiter, boxesCapacity);

        // Calcul du nombre de cartons
        Integer nbBoxesOptimized = XspeedItUtils.findNbBoxes(boxesOptimizedCalculator, boxesDelimiter);
        Integer nbBoxesActual = XspeedItUtils.findNbBoxes(boxesActualCalculator, boxesDelimiter);

        return new CalculatorComparator(
                boxesOptimizedCalculator,
                boxesActualCalculator,
                nbBoxesOptimized,
                nbBoxesActual,
                nbBoxesActual-nbBoxesOptimized // Gain entre CalculatorOptimized et CalculatorActual
        );
    }
}
