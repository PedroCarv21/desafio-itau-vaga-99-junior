package com.dev.desafio_itau_vaga_99_junior.controllers;

import com.dev.desafio_itau_vaga_99_junior.dtos.TransacaoRequestDTO;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
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
    public ResponseEntity<Void> adicionarTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO){
        this.transacaoService.adicionarTransacao(transacaoRequestDTO);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> apagarTransacoes(){
        this.transacaoService.apagarTransacoes();
        return ResponseEntity.ok().build();
    }
}