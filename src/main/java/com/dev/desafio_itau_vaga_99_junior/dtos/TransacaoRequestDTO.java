package com.dev.desafio_itau_vaga_99_junior.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoRequestDTO (
        BigDecimal valor,
        OffsetDateTime dataHora) {
}
