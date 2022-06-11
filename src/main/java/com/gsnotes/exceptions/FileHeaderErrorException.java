package com.gsnotes.exceptions;

public class FileHeaderErrorException extends Exception{
    public FileHeaderErrorException() {
    }

    public FileHeaderErrorException(String message) {
        super(message);
    }
}
