package com.dev.desafio_itau_vaga_99_junior.controllers;

import com.dev.desafio_itau_vaga_99_junior.dtos.EstatisticaResponseDTO;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
public class EstatisticaController {

    private final TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<EstatisticaResponseDTO> calcularEstatistica(@RequestParam("periodo_transacao") Long periodoTransacao){
        EstatisticaResponseDTO estatisticaResponseDTO = this.transacaoService.calcularEstatisticas(periodoTransacao);
        return ResponseEntity.ok().body(estatisticaResponseDTO);
    }
}
