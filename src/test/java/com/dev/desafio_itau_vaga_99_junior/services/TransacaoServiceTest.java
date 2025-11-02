package com.dev.desafio_itau_vaga_99_junior.services;

import com.dev.desafio_itau_vaga_99_junior.dtos.EstatisticaResponseDTO;
import com.dev.desafio_itau_vaga_99_junior.dtos.TransacaoRequestDTO;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
import com.dev.desafio_itau_vaga_99_junior.mappers.EstatisticaMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    TransacaoService transacaoService;

    @Mock
    EstatisticaMapper estatisticaMapper;

    @Captor
    ArgumentCaptor<DoubleSummaryStatistics> dssCaptor;

    @Spy
    List<TransacaoRequestDTO> transacoes = new ArrayList<>();

    @Test
    void inserirDataFuturaEmTransacaoTest(){
        TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO(
                new BigDecimal(50.6),
                OffsetDateTime.parse("2026-01-25T09:30:00-03:00"));

        assertThrows(
                TempoFuturoException.class,
                () -> this.transacaoService.adicionarTransacao(transacaoRequestDTO));
    }

    @Test
    void inserirValorNegativoEmTransacaoTest(){
        TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO(
                new BigDecimal(-50.4),
                OffsetDateTime.parse("2025-01-25T09:30:00-03:00"));

        assertThrows(
                ValorNegativoException.class,
                () -> this.transacaoService.adicionarTransacao(transacaoRequestDTO));
    }

    @Test
    void inserirTransacaoCorretaTest(){
        TransacaoRequestDTO transacaoRequestDTO = new TransacaoRequestDTO(
                new BigDecimal(50.4),
                OffsetDateTime.parse("2025-01-25T09:30:00-03:00"));

        this.transacaoService.adicionarTransacao(transacaoRequestDTO);

       verify(transacoes, times(1)).add(transacaoRequestDTO);
    }

    @Test
    void apagarTodasAsTransacoesTest(){
        this.transacaoService.apagarTransacoes();
        verify(transacoes, times(1)).clear();
    }

    @Test
    void verificarCalculoDasEstatisticasTest(){
        TransacaoRequestDTO transacaoRequestDTO1 = new TransacaoRequestDTO(
                new BigDecimal(24.0),
                OffsetDateTime.now());

        TransacaoRequestDTO transacaoRequestDTO2 = new TransacaoRequestDTO(
                new BigDecimal(26.0),
                OffsetDateTime.now());

        transacaoService.adicionarTransacao(transacaoRequestDTO1);
        transacaoService.adicionarTransacao(transacaoRequestDTO2);

        EstatisticaResponseDTO calculoEsperado = new EstatisticaResponseDTO(2L, 50.0, 25.0, 24.0, 26.0);

        EstatisticaResponseDTO calculoEstatistica = this.transacaoService.calcularEstatisticas(120L);

        verify(estatisticaMapper).toDTO(this.dssCaptor.capture());

        DoubleSummaryStatistics calculoReal = this.dssCaptor.getValue();

        assertThat(calculoReal.getCount()).isEqualTo(calculoEsperado.count());
        assertThat(calculoReal.getSum()).isEqualTo(calculoEsperado.sum());
        assertThat(calculoReal.getAverage()).isEqualTo(calculoEsperado.average());
        assertThat(calculoReal.getMin()).isEqualTo(calculoEsperado.min());
        assertThat(calculoReal.getMax()).isEqualTo(calculoEsperado.max());
    }
}
