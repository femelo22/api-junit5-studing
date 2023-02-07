package br.com.lfmelo.apijunit5.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErros> handleNotFoundException(NotFoundException ex) {

        ApiErros apiErros = new ApiErros(ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErros);
    }
}
