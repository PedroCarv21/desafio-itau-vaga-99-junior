package com.dev.desafio_itau_vaga_99_junior.transacoes;

import com.dev.desafio_itau_vaga_99_junior.controllers.EstatisticaController;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EstatisticaControllerTest {

    @InjectMocks
    EstatisticaController estatisticaController;

    @Mock
    TransacaoService transacaoService;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(estatisticaController)
                .build();
    }

    @Test
    void calcularEstatisticaComTempoPadraoTest() throws Exception{

        mockMvc.perform(get("/estatistica")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(transacaoService, times(1)).calcularEstatisticas(60L);
    }

    @Test
    void calcularEstatisticaComTempoPersonalizadoTest() throws Exception{

        Long tempo = 120L;

        mockMvc.perform(get("/estatistica")
                .accept(MediaType.APPLICATION_JSON)
                        .param("periodo_transacao", tempo.toString()))
                .andExpect(status().isOk());

        verify(transacaoService, times(1)).calcularEstatisticas(tempo);
    }
}
