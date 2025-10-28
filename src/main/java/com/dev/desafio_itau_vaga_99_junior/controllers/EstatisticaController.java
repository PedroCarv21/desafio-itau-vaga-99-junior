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
                    Retorna a soma, o mínimo, a média, o máximo e a quantidade de transações que ocorreram
                    nos últimos X segundos (esse X sendo definido pelo usuário). Se não houver nenhuma
                    transação que ocorreu neste intervalo de tempo, todos os campos retornarão com o valor 0.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "O cálculo das transações retornou com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Houve algum erro de requisição no lado do cliente."),
                    @ApiResponse(responseCode = "422", description = "Não foi passado um número inteiro"),
                    @ApiResponse(responseCode = "500", description = "Houve algum problema no servidor.")
            }
    )
    public ResponseEntity<EstatisticaResponseDTO> calcularEstatistica(@RequestParam( value = "periodo_transacao", defaultValue = "60") Long periodoTransacao){
        EstatisticaResponseDTO estatisticaResponseDTO = this.transacaoService.calcularEstatisticas(periodoTransacao);
        return ResponseEntity.ok().body(estatisticaResponseDTO);
    }
}
