package com.dev.desafio_itau_vaga_99_junior.services;

import com.dev.desafio_itau_vaga_99_junior.dtos.EstatisticaResponseDTO;
import com.dev.desafio_itau_vaga_99_junior.dtos.TransacaoRequestDTO;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
import com.dev.desafio_itau_vaga_99_junior.mappers.EstatisticaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final List<TransacaoRequestDTO> transacoes;
    private final EstatisticaMapper estatisticaMapper;

    public void adicionarTransacao(TransacaoRequestDTO transacao){

        OffsetDateTime dataHoraAtual = OffsetDateTime.now();

        if (transacao.dataHora().isAfter(dataHoraAtual)){
            throw new TempoFuturoException("O periodo da transacao nao pode ser futuro");
        }

        if (transacao.valor().compareTo(new BigDecimal(0)) < 0){
            throw new ValorNegativoException("A transacao nao deve ter valor negativo.");
        }

        transacoes.add(transacao);
        imprimirTransacoes();
    }

    public void apagarTransacoes(){
        transacoes.clear();
        imprimirTransacoes();
    }

    private void imprimirTransacoes(){
        if (this.transacoes.isEmpty()){
            System.out.println("Nao ha transacoes");
        }
        else{
            transacoes.forEach(System.out::println);
            System.out.println();
        }
    }

    public EstatisticaResponseDTO calcularEstatisticas(Long periodoTransacao){

        OffsetDateTime tempoAtual = OffsetDateTime.now();

        List<TransacaoRequestDTO> transacoesSelecionadas = this.transacoes.stream().filter(transacaoRealizada -> {
            Duration duracao = Duration.ofSeconds(periodoTransacao);
            Duration tempoRestante = Duration.between(transacaoRealizada.dataHora(), tempoAtual);
            return tempoRestante.compareTo(duracao) < 0;
        }).toList();

        if (transacoesSelecionadas.isEmpty()){
            return new EstatisticaResponseDTO(0L,0.0,0.0,0.0, 0.0);
        }
        DoubleSummaryStatistics dss = transacoesSelecionadas.stream()
                .collect(Collectors.summarizingDouble(t -> {
                    BigDecimal valor = t.valor();
                    return valor.doubleValue();
                }));
        return this.estatisticaMapper.toDTO(dss);
    }
}