package br.com.alura.Forum_Hub.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionDealer {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> dealEntityNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> dealMethodArgumentNotValid(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(ValidationError::new).toList());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> dealRuntime(RuntimeException e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    private record ValidationError(String field, String message){
        public ValidationError (FieldError e){
            this(e.getField(), e.getDefaultMessage());
        }
    }
}
