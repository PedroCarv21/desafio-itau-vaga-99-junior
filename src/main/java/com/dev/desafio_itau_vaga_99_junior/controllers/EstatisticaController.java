package com.dev.desafio_itau_vaga_99_junior.controllers;

import com.dev.desafio_itau_vaga_99_junior.dtos.EstatisticaResponseDTO;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
public class EstatisticaController {

    private final TransacaoService transacaoService;

    @GetMapping
    @Operation(
            description = """
                    Retorna a soma, o minimo, a media, a soma e a quantidade de transacoes que ocorreu
                    nos ultimos X segundos (esse X sendo definido pelo usuario). Se nao houver nenhuma
                    transacao que ocorreu neste intervalo de tempo, todos os campos retornarao com o valor 0.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "O calculo das transacoes foi retornado sucesso."),
                    @ApiResponse(responseCode = "422", description = "Nao foi passado um numero inteiro"),
                    @ApiResponse(responseCode = "500", description = "Houve algum problema no servidor.")
            }
    )
    public ResponseEntity<EstatisticaResponseDTO> calcularEstatistica(@RequestParam( value = "periodo_transacao", defaultValue = "60") Long periodoTransacao){
        EstatisticaResponseDTO estatisticaResponseDTO = this.transacaoService.calcularEstatisticas(periodoTransacao);
        return ResponseEntity.ok().body(estatisticaResponseDTO);
    }
}
