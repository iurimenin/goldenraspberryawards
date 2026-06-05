package io.github.iurimenin.goldenraspberryawards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = "app.csv-path=csv/movielist_simple.csv"
)
@AutoConfigureMockMvc
class AwardControllerSimpleTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCalculateMinAndMaxCorrectly() throws Exception {

        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.min[0].producer")
                        .value("Producer A"))
                .andExpect(jsonPath("$.min[0].interval")
                        .value(5))
                .andExpect(jsonPath("$.min[0].previousWin")
                        .value(2000))
                .andExpect(jsonPath("$.min[0].followingWin")
                        .value(2005))

                .andExpect(jsonPath("$.max[0].producer")
                        .value("Producer B"))
                .andExpect(jsonPath("$.max[0].interval")
                        .value(10))
                .andExpect(jsonPath("$.max[0].previousWin")
                        .value(2010))
                .andExpect(jsonPath("$.max[0].followingWin")
                        .value(2020));
    }
}