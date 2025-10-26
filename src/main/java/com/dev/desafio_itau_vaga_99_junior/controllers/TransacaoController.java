package com.dev.desafio_itau_vaga_99_junior.controllers;

import com.dev.desafio_itau_vaga_99_junior.entities.Transacao;
import com.dev.desafio_itau_vaga_99_junior.exceptions.TempoFuturoException;
import com.dev.desafio_itau_vaga_99_junior.exceptions.ValorNegativoException;
import com.dev.desafio_itau_vaga_99_junior.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping("/")
    public ResponseEntity<Void> adicionarTransacao(@RequestBody Transacao transacao){
        try {
            this.transacaoService.adicionarTransacao(transacao);
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
        }
        catch (TempoFuturoException e){
            System.out.println(e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
        catch (ValorNegativoException e){
            System.out.println(e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}