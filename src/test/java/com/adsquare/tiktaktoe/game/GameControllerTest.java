package com.adsquare.tiktaktoe.game;

import com.adsquare.tiktaktoe.game.dto.GameEntity;
import com.adsquare.tiktaktoe.game.dto.PlayInfo;
import com.adsquare.tiktaktoe.game.dto.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {GameController.class})
public class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    GameService gameService;

    @Test
    void playNewGameTest() throws Exception {
        when(gameService.playGame(any(), any()))
                .thenReturn(new GameEntity(1L, Player.O, null, new Date(), new Date(), false,
                        new Player[][]{{Player.O, null, null}, {null, null, null}, {null, null, null}}));

        mockMvc.perform(
            post("/game/play")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlayInfo(Player.O, 0, 0)))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ended", is(false)))
        .andExpect(jsonPath("$.lastPlayer", is("O")));
    }

    @Test
    void playExistGameTest() throws Exception {
        when(gameService.playGame(any(), any()))
                .thenReturn(new GameEntity(1L, Player.O, null, new Date(), new Date(), false,
                        new Player[][]{{Player.O, Player.O, null}, {Player.X, null, null}, {null, null, null}}));

        mockMvc.perform(
            post("/game/play/{gameId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlayInfo(Player.O, 0, 1)))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ended", is(false)))
        .andExpect(jsonPath("$.lastPlayer", is("O")));
    }
}
