package com.dev.desafio_itau_vaga_99_junior.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TempoFuturoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public void tratarTempoFuturoException(TempoFuturoException e){
        System.out.println(e.getMessage());
    }

    @ExceptionHandler(ValorNegativoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public void tratarValorNegativoException(ValorNegativoException e){
        System.out.println(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void tratarSolicitacaoInvalida(RuntimeException e){
        System.out.printf("A API não compreendeu a requisição do cliente: %s", e.getMessage());
    }
}
