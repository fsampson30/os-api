package com.sampson.osapi.exceptionhandler;

import com.sampson.osapi.domain.exception.EntidadeNaoEncontradaException;
import com.sampson.osapi.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request){
        var status = HttpStatus.NOT_FOUND;
        var problema = new Problema();
        problema.setStatus(status.value());
        problema.setTitulo(ex.getMessage());
        problema.setDataHora(OffsetDateTime.now());
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;
        var problema = new Problema();
        problema.setStatus(status.value());
        problema.setTitulo(ex.getMessage());
        problema.setDataHora(OffsetDateTime.now());
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var problema = new Problema();
        var campos = new ArrayList<Problema.Campo>();
        for (ObjectError error: ex.getBindingResult().getAllErrors()){
            String nome = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            campos.add(new Problema.Campo(nome,mensagem));
        }
        problema.setStatus(status.value());
        problema.setTitulo("Um ou mais campos estão inválidos");
        problema.setDataHora(OffsetDateTime.now());
        problema.setCampos(campos);
        return super.handleExceptionInternal(ex,problema,headers,status,request);
    }
}
