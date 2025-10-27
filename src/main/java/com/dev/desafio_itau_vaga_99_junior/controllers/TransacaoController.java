package com.dev.desafio_itau_vaga_99_junior.controllers;

import com.dev.desafio_itau_vaga_99_junior.entities.Transacao;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
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
    public ResponseEntity<Void> adicionarTransacao(@RequestBody Transacao transacao){
        try {
            this.transacaoService.adicionarTransacao(transacao);
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
        }
        catch (TempoFuturoException | ValorNegativoException e){
            System.out.println(e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> apagarTransacoes(){
        this.transacaoService.apagarTransacoes();
        return ResponseEntity.ok().build();
    }
}