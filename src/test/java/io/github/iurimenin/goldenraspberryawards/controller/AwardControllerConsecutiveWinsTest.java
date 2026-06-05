package io.github.iurimenin.goldenraspberryawards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = "app.csv-path=csv/movielist_consecutive_wins.csv"
)
@AutoConfigureMockMvc
class AwardControllerConsecutiveWinsTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCalculateConsecutiveWinsIntervals()
            throws Exception {

        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.min[0].producer")
                        .value("Producer B"))

                .andExpect(jsonPath("$.min[0].interval")
                        .value(3))

                .andExpect(jsonPath("$.max", hasSize(2)))

                .andExpect(jsonPath("$.max[*].producer",
                        everyItem(is("Producer A"))))

                .andExpect(jsonPath("$.max[*].interval",
                        everyItem(is(5))));
    }
}