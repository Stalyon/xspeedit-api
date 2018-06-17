package oui.sncf.xspeedit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.service.CalculatorService;
import oui.sncf.xspeedit.utils.XspeedItConstant;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/calculator/optimized")
@Api(value="Optimzed calculator", description="Endpoints du calculateur optimisé (en phase de tests)")
public class CalculatorOptimzedController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("calculatorOptimizedService")
    private CalculatorService calculatorService;

    @GetMapping("/organizeTheBoxes")
    @ApiOperation(value="Organiser les cartons suivant les articles", response=String.class)
    public String organizeTheBoxes(@RequestParam String items, HttpServletResponse response) {
        try {
            return calculatorService.organizeTheBoxes(items, XspeedItConstant.DEFAULT_SPLIT_REGEX,
                    XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY);
        } catch (XspeedItException e) {
            LOGGER.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return e.getMessage();
        }
    }

    @GetMapping("/organizeTheBoxesGeneric")
    @ApiOperation(value="Organiser les cartons suivant les articles, en précisant le délimiteur " +
            "entre les articles, la capacité des cartons ainsi que le délimiteur entre les cartons.", response=String.class)
    public String organizeTheBoxesGeneric(@RequestParam String items, @RequestParam String itemsRegexDelimiter,
                                          @RequestParam String boxesDelimiter, @RequestParam Integer boxesCapacity,
                                          HttpServletResponse response) {
        try {
            return calculatorService.organizeTheBoxes(items, itemsRegexDelimiter, boxesDelimiter, boxesCapacity);
        } catch (XspeedItException e) {
            LOGGER.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return e.getMessage();
        }
    }
}
