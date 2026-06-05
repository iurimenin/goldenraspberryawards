package io.github.iurimenin.goldenraspberryawards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = "app.csv-path=csv/movielist_non_victory.csv"
)
@AutoConfigureMockMvc
class AwardControllerNonVictoryTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnEmptyListsWhenThereAreNoWinners()
            throws Exception {

        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.min", hasSize(0)))
                .andExpect(jsonPath("$.max", hasSize(0)));
    }
}