package oui.sncf.xspeedit.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import oui.sncf.xspeedit.entity.CalculatorComparator;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.service.CalculatorComparatorService;
import oui.sncf.xspeedit.utils.XspeedItConstant;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CalculatorComparatorController.class)
public class CalculatorComparatorControllerTU {

    @MockBean
    private CalculatorComparatorService calculatorComparatorService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test du endpoint /api/calculator/comparator/compare
     *
     * @throws Exception
     */
    @Test
    public void compareTest() throws Exception {
        CalculatorComparator calculatorComparator = new CalculatorComparator("11/9/2", "91/21", 3, 2, 1);

        when(calculatorComparatorService.compareCalculator("1192", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY))
                .thenReturn(calculatorComparator);

        mockMvc.perform(get("/api/calculator/comparator/compare?items=1192")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boxesOptimizedCalculator", is(calculatorComparator.getBoxesOptimizedCalculator())))
                .andExpect(jsonPath("$.boxesActualCalculator", is(calculatorComparator.getBoxesActualCalculator())))
                .andExpect(jsonPath("$.nbBoxesOptimalCalculator", is(calculatorComparator.getNbBoxesOptimalCalculator())))
                .andExpect(jsonPath("$.nbBoxesActualCalculator", is(calculatorComparator.getNbBoxesActualCalculator())))
                .andExpect(jsonPath("$.nbBoxesSaved", is(calculatorComparator.getNbBoxesSaved())));
    }

    /**
     * Test du endpoint /api/calculator/comparator/compare
     *
     * @throws Exception
     */
    @Test
    public void compareTest2() throws Exception {when(calculatorComparatorService.compareCalculator("1192", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY))
                .thenThrow(new XspeedItException("Exception mockée"));

        mockMvc.perform(get("/api/calculator/comparator/compare?items=1192")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test du endpoint /api/calculator/comparator/compareGeneric
     *
     * @throws Exception
     */
    @Test
    public void compareGenericTest() throws Exception {
        CalculatorComparator calculatorComparator = new CalculatorComparator("11-9-2", "91-21", 3, 2, 1);

        when(calculatorComparatorService.compareCalculator("1,1,9,2", ",", "-", 10))
                .thenReturn(calculatorComparator);

        mockMvc.perform(get("/api/calculator/comparator/compareGeneric?items=1,1,9,2" +
                "&itemsRegexDelimiter=,&boxesDelimiter=-&boxesCapacity=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boxesOptimizedCalculator", is(calculatorComparator.getBoxesOptimizedCalculator())))
                .andExpect(jsonPath("$.boxesActualCalculator", is(calculatorComparator.getBoxesActualCalculator())))
                .andExpect(jsonPath("$.nbBoxesOptimalCalculator", is(calculatorComparator.getNbBoxesOptimalCalculator())))
                .andExpect(jsonPath("$.nbBoxesActualCalculator", is(calculatorComparator.getNbBoxesActualCalculator())))
                .andExpect(jsonPath("$.nbBoxesSaved", is(calculatorComparator.getNbBoxesSaved())));
    }

    /**
     * Test du endpoint /api/calculator/comparator/compareGeneric
     *
     * @throws Exception
     */
    @Test
    public void compareGenericTest2() throws Exception {when(calculatorComparatorService.compareCalculator("1,1,9,2", ",", "-", 10))
                .thenThrow(new XspeedItException("Exception mockée"));

        mockMvc.perform(get("/api/calculator/comparator/compareGeneric?items=1,1,9,2" +
                "&itemsRegexDelimiter=,&boxesDelimiter=-&boxesCapacity=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
