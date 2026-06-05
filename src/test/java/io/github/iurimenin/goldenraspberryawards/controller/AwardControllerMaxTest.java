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
        properties = "app.csv-path=csv/movielist_max_test.csv"
)
@AutoConfigureMockMvc
class AwardControllerMaxTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnTwoMaxEntries() throws Exception {

        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.max", hasSize(2)))

                .andExpect(jsonPath("$.max[*].interval",
                        everyItem(is(10))))

                .andExpect(jsonPath("$.min[0].producer")
                        .value("Producer C"))

                .andExpect(jsonPath("$.min[0].interval")
                        .value(5));
    }
}