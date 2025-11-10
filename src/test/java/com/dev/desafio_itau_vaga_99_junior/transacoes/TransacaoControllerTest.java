package com.dev.desafio_itau_vaga_99_junior.transacoes;

import com.dev.desafio_itau_vaga_99_junior.controllers.TransacaoController;
import com.dev.desafio_itau_vaga_99_junior.dtos.TransacaoRequestDTO;
import com.dev.desafio_itau_vaga_99_junior.exceptions.GlobalExceptionHandler;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

    @InjectMocks
    TransacaoController transacaoController;

    @Mock
    TransacaoService transacaoService;

    MockMvc mockMvc;

    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController)
                .setControllerAdvice(globalExceptionHandler)
                .build();

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void criandoTransacaoComDataFuturaTest() throws Exception {

        TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO(
                new BigDecimal(100.5),
                OffsetDateTime.parse("2026-01-25T09:30:00-03:00"));

        String jsonContent = objectMapper.writeValueAsString(transacaoRequestDTO);

        doThrow(TempoFuturoException.class)
                .when(transacaoService)
                .adicionarTransacao(argThat(dto -> dto.dataHora().isAfter(OffsetDateTime.now())));

        mockMvc.perform(post("/transacao")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void inserirValorNegativoTest() throws Exception {

        TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO(
                new BigDecimal(-400.5),
                OffsetDateTime.parse("2025-01-25T09:30:00-03:00"));

        String jsonContent = objectMapper.writeValueAsString(transacaoRequestDTO);

        doThrow(ValorNegativoException.class)
                .when(transacaoService)
                .adicionarTransacao(argThat(dto -> dto.valor().compareTo(BigDecimal.ZERO) < 0));

        mockMvc.perform(post("/transacao")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void inserirTransacaoTest() throws Exception {

        TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO(
                new BigDecimal(230.6),
                OffsetDateTime.now());

        String jsonContent = objectMapper.writeValueAsString(transacaoRequestDTO);

        mockMvc.perform(post("/transacao")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    void apagarTransacoes() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());

        verify(transacaoService, times(1)).apagarTransacoes();
    }


}
