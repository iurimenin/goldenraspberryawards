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
        properties = "app.csv-path=csv/movielist_multiple_producers.csv"
)
@AutoConfigureMockMvc
class AwardControllerMultipleProducersTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldSplitMultipleProducersCorrectly()
            throws Exception {

        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.min[0].producer")
                        .value("Producer A"))

                .andExpect(jsonPath("$.min[0].interval")
                        .value(5))

                .andExpect(jsonPath("$.max[0].producer")
                        .value("Producer B"))

                .andExpect(jsonPath("$.max[0].interval")
                        .value(10));
    }
}