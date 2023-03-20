package br.com.ada.cardgame.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.ada.cardgame.services.dtos.BestRattingDto;
import br.com.ada.cardgame.services.dtos.MovieDto;
import br.com.ada.cardgame.services.dtos.RoundDto;
import br.com.ada.cardgame.services.impl.RoundServiceImpl;
import br.com.ada.security.services.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class RoundControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoundServiceImpl roundService;
    @Autowired
    private TokenService tokenService;

    @Test
    void guessBetterReviewSuccess() throws Exception {

        String token = "Bearer " + tokenService.createJwtToken("user1");

        BestRattingDto bestRatting = new BestRattingDto(false, 1,
                new RoundDto(5L, new MovieDto(3L, "Title Movie 1", "http://this.is.a/poster"),
                        new MovieDto(5L, "Title Movie 2", "http://this.is.a/poster")));

        //given
        when(this.roundService.guessBetterReviewMovie(anyLong(), anyInt())).thenReturn(bestRatting);

        //when
        ResultActions perform = mockMvc.perform(
                put("/round/5").header(HttpHeaders.AUTHORIZATION, token)
                        .queryParam("guess", "FIRST_MOVIE"));

        System.out.println(perform.andReturn().getResponse().getContentAsString());

        //them
        perform.andExpect(status().isOk()).andExpect(jsonPath("$.rightAnswer").value(false))
                .andExpect(jsonPath("$.wrongCount").value(1))
                .andExpect(jsonPath("$.movies[0].id").value(3))
                .andExpect(jsonPath("$.movies[0].title").value("Title Movie 1"))
                .andExpect(jsonPath("$.movies[0].poster").value("http://this.is.a/poster"))
                .andExpect(jsonPath("$.movies[1].id").value(5))
                .andExpect(jsonPath("$.movies[1].title").value("Title Movie 2"))
                .andExpect(jsonPath("$.movies[1].poster").value("http://this.is.a/poster"));

    }
}
