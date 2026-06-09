package io.github.iurimenin.goldenraspberryawards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AwardControllerOriginalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnJsonResponse() throws Exception {
        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnMinProducerJoelSilverWithInterval1() throws Exception {
        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min", hasSize(1)))
                .andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.min[0].previousWin").value(1990))
                .andExpect(jsonPath("$.min[0].followingWin").value(1991));
    }

    @Test
    void shouldReturnMaxProducerMatthewVaughnWithInterval13() throws Exception {
        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.max", hasSize(1)))
                .andExpect(jsonPath("$.max[0].producer").value("Matthew Vaughn"))
                .andExpect(jsonPath("$.max[0].interval").value(13))
                .andExpect(jsonPath("$.max[0].previousWin").value(2002))
                .andExpect(jsonPath("$.max[0].followingWin").value(2015));
    }

    @Test
    void shouldHaveOnlyInterval1InMinAndInterval13InMax() throws Exception {
        mockMvc.perform(get("/api/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[*].interval", everyItem(is(1))))
                .andExpect(jsonPath("$.max[*].interval", everyItem(is(13))));
    }
}
