package oui.sncf.xspeedit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import oui.sncf.xspeedit.entity.CalculatorComparator;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.service.CalculatorComparatorService;
import oui.sncf.xspeedit.utils.XspeedItConstant;

@RestController
@RequestMapping("/api/calculator/comparator")
@Api(value="Comparator", description="Endpoints du comparateur de calculateur")
public class CalculatorComparatorController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CalculatorComparatorService calculatorComparatorService;

    @GetMapping("/compare")
    @ApiOperation(value="Organiser les cartons suivant les articles", response=String.class)
    public ResponseEntity<CalculatorComparator> compareCalculator(@RequestParam String items) {
        try {
            return new ResponseEntity<>(calculatorComparatorService.compareCalculator(items,
                    XspeedItConstant.DEFAULT_SPLIT_REGEX, XspeedItConstant.BOXES_DELIMITER,
                    XspeedItConstant.BOXES_CAPACITY), HttpStatus.OK);
        } catch (XspeedItException e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/compareGeneric")
    @ApiOperation(value="Organiser les cartons suivant les articles, en précisant le délimiteur " +
            "entre les articles, la capacité des cartons ainsi que le délimiteur entre les cartons.", response=String.class)
    public ResponseEntity<CalculatorComparator> compareGeneric(@RequestParam String items, @RequestParam String itemsRegexDelimiter,
                                                               @RequestParam String boxesDelimiter, @RequestParam Integer boxesCapacity) {
        try {
            return new ResponseEntity<>(
                    calculatorComparatorService.compareCalculator(items, itemsRegexDelimiter, boxesDelimiter,
                            boxesCapacity), HttpStatus.OK
            );
        } catch (XspeedItException e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
