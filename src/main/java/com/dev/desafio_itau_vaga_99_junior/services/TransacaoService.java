package com.dev.desafio_itau_vaga_99_junior.services;

import com.dev.desafio_itau_vaga_99_junior.entities.Transacao;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class TransacaoService {

    private List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao){

        OffsetDateTime dataHoraAtual = OffsetDateTime.now();

        if (transacao.getDataHora().isAfter(dataHoraAtual)){
            throw new TempoFuturoException("O periodo da transacao nao pode ser futuro");
        }

        if (transacao.getValor().compareTo(new BigDecimal(0)) < 0){
            throw new ValorNegativoException("A transacao nao deve ter valor negativo.");
        }

        transacoes.add(transacao);
        transacoes.forEach(System.out::println);
    }
}