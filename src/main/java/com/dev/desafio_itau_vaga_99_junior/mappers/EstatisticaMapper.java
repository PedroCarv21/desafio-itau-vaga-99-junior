package com.dev.desafio_itau_vaga_99_junior.mappers;

import com.dev.desafio_itau_vaga_99_junior.dtos.EstatisticaResponseDTO;
import org.mapstruct.Mapper;

import java.util.DoubleSummaryStatistics;

@Mapper(componentModel = "spring")
public interface EstatisticaMapper {

    EstatisticaResponseDTO toDTO(DoubleSummaryStatistics dss);
}
