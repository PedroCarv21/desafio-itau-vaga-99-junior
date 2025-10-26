package com.dev.desafio_itau_vaga_99_junior.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class Transacao {

    private BigDecimal valor;
    private OffsetDateTime dataHora;


}