package com.dev.desafio_itau_vaga_99_junior.services;

import com.dev.desafio_itau_vaga_99_junior.dtos.EstatisticaResponseDTO;
import com.dev.desafio_itau_vaga_99_junior.dtos.TransacaoRequestDTO;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
import com.dev.desafio_itau_vaga_99_junior.mappers.EstatisticaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> transacoes;
    private final EstatisticaMapper estatisticaMapper;

    public void adicionarTransacao(TransacaoRequestDTO transacao){

        OffsetDateTime dataHoraAtual = OffsetDateTime.now();

        log.info("Verificando se o dado do campo 'dataHora' está no tempo futuro.");
        if (transacao.dataHora().isAfter(dataHoraAtual)){
            log.error("Ocorreu erro ao informar uma data/hora futura.");
            throw new TempoFuturoException("O periodo da transacao nao pode ser futuro.");
        }

        log.info("Verificando se o valor da transação é negativo");
        if (transacao.valor().compareTo(new BigDecimal(0)) < 0){
            log.error("Ocorreu um erro ao passar um valor negativo para a transação.");
            throw new ValorNegativoException("A transacao nao deve ter valor negativo.");
        }

        log.info("A transação foi adicionada a lista de transações.");
        transacoes.add(transacao);
    }

    public void apagarTransacoes(){
        log.info("Todas as transações foram excluídas da lista.");
        transacoes.clear();
    }

    public EstatisticaResponseDTO calcularEstatisticas(Long periodoTransacao){

        OffsetDateTime tempoAtual = OffsetDateTime.now();

        log.info("Filtrando as transações que ocorreram nos últimos X segundos (de acordo valor da variável 'periodoTransacao')");
        List<TransacaoRequestDTO> transacoesSelecionadas = this.transacoes.stream().filter(transacaoRealizada -> {
            Duration duracao = Duration.ofSeconds(periodoTransacao);
            Duration tempoRestante = Duration.between(transacaoRealizada.dataHora(), tempoAtual);
            return tempoRestante.compareTo(duracao) < 0;
        }).toList();

        log.info("Verificando se a lista de transações selecionadas continua vazia (mesmo depois da filtragem).");
        if (transacoesSelecionadas.isEmpty()){
            log.info("Nenhuma transação foi selecionada e, portanto, todos os valores estatísticos serão zero.");
            return new EstatisticaResponseDTO(0L,0.0,0.0,0.0, 0.0);
        }

        log.info("Preparando cálculos para a estatística e retornando os valores de soma, mínimo, média, máximo e quantidade de transações.");
        OffsetDateTime inicioCalculo = OffsetDateTime.now();
        DoubleSummaryStatistics dss = transacoesSelecionadas.stream()
                .collect(Collectors.summarizingDouble(t -> {
                    BigDecimal valor = t.valor();
                    return valor.doubleValue();
                }));

        OffsetDateTime fimCalculo = OffsetDateTime.now();

        Long tempoCalculo = Duration.between(inicioCalculo, fimCalculo).toMillis();
        System.out.printf("Tempo do cálculo das estatísticas (milissegundos): %d", tempoCalculo);
        return this.estatisticaMapper.toDTO(dss);
    }
}