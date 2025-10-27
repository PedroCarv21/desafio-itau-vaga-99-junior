package com.dev.desafio_itau_vaga_99_junior.dtos;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;

public record EstatisticaResponseDTO (
        Long count,
        Double sum,
        Double average,
        Double min,
        Double max){

}
