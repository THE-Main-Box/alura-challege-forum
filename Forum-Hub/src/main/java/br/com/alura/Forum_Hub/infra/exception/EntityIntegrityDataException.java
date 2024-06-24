package br.com.alura.Forum_Hub.infra.exception;

public class EntityIntegrityDataException extends RuntimeException {
    public EntityIntegrityDataException(String message){
        super(message);
    }
}
