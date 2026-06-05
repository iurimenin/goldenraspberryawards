package io.github.iurimenin.goldenraspberryawards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = "app.csv-path=csv/movielist_min_test.csv"
)
@AutoConfigureMockMvc
class AwardControllerMinTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnTwoMinEntries() throws Exception {

        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.min", hasSize(2)))

                .andExpect(jsonPath("$.min[*].interval",
                        everyItem(is(1))))

                .andExpect(jsonPath("$.max[0].producer")
                        .value("Producer C"))

                .andExpect(jsonPath("$.max[0].interval")
                        .value(10));
    }
}