package oui.sncf.xspeedit.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import oui.sncf.xspeedit.exception.XspeedItException;
import oui.sncf.xspeedit.service.CalculatorService;
import oui.sncf.xspeedit.utils.XspeedItConstant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CalculatorOptimzedController.class)
public class CalculatorOptimizdControllerTU {

    @MockBean
    @Qualifier("calculatorOptimizedService")
    private CalculatorService calculatorService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test du endpoint /api/calculator/optimized/organizeTheBoxes
     *
     * @throws Exception
     */
    @Test
    public void organizeTheBoxesTest() throws Exception {

        when(calculatorService.organizeTheBoxes("163841689525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY))
                .thenReturn("91/82/81/73/73/64/6/55");

        mockMvc.perform(get("/api/calculator/optimized/organizeTheBoxes?items=163841689525773")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("91/82/81/73/73/64/6/55"));
    }

    /**
     * Test du endpoint /api/calculator/optimized/organizeTheBoxes
     *
     * @throws Exception
     */
    @Test
    public void organizeTheBoxesTest2() throws Exception {

        when(calculatorService.organizeTheBoxes("16384168e9525773", XspeedItConstant.DEFAULT_SPLIT_REGEX,
                XspeedItConstant.BOXES_DELIMITER, XspeedItConstant.BOXES_CAPACITY))
                .thenThrow(new XspeedItException("Exception mockée"));

        mockMvc.perform(get("/api/calculator/optimized/organizeTheBoxes?items=16384168e9525773")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Exception mockée"));
    }

    /**
     * Test du endpoint /api/calculator/optimized/organizeTheBoxesGeneric
     *
     * @throws Exception
     */
    @Test
    public void organizeTheBoxesGeneric() throws Exception {

        when(calculatorService.organizeTheBoxes("1,6,3,8,4,1,6,8,9,5,2,5,7,7,3", ",", "-", 13))
                .thenReturn("94-85-85-76-76-33211");

        mockMvc.perform(get("/api/calculator/optimized/organizeTheBoxesGeneric?items=1,6,3,8,4,1,6,8,9,5,2,5,7,7,3" +
                "&itemsRegexDelimiter=,&boxesDelimiter=-&boxesCapacity=13")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("94-85-85-76-76-33211"));
    }

    /**
     * Test du endpoint /api/calculator/optimized/organizeTheBoxesGeneric
     *
     * @throws Exception
     */
    @Test
    public void organizeTheBoxesGeneric2() throws Exception {

        when(calculatorService.organizeTheBoxes("1,6,3,8,4,1,6,8,9,5,2,5,7,7,3", "d", "-", 13))
                .thenThrow(new XspeedItException("Exception mockée"));

        mockMvc.perform(get("/api/calculator/optimized/organizeTheBoxesGeneric?items=1,6,3,8,4,1,6,8,9,5,2,5,7,7,3" +
                "&itemsRegexDelimiter=d&boxesDelimiter=-&boxesCapacity=13")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Exception mockée"));
    }
}
