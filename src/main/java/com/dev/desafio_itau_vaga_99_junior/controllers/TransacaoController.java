package com.dev.desafio_itau_vaga_99_junior.controllers;

import com.dev.desafio_itau_vaga_99_junior.dtos.TransacaoRequestDTO;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    @Operation(
            description = "Armazena uma transacao (que contem os dados do valor e do tempo da transacao) dentro de uma lista de transacoes (em memoria).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "A transacao foi inserida na lista com sucesso."),
                    @ApiResponse(responseCode = "422", description = "Foi passado um numero negativo para o campo valor ou uma data futura para o campo dataHora."),
                    @ApiResponse(responseCode = "400", description = "Houve algum erro de requisicao no lado do cliente."),
                    @ApiResponse(responseCode = "500", description = "Houve algum problema no lado do servidor.")
            }
    )
    public ResponseEntity<Void> adicionarTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO){
        this.transacaoService.adicionarTransacao(transacaoRequestDTO);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

    @DeleteMapping
    @Operation(
            description = "Utilizado para excluir todas as transacoes armazenadas na lista.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Todas as transacoes foram apagadas da lista com sucesso."),
                    @ApiResponse(responseCode = "500", description = "Houve algum problema no servidor.")
            }
    )
    public ResponseEntity<Void> apagarTransacoes(){
        this.transacaoService.apagarTransacoes();
        return ResponseEntity.ok().build();
    }
}