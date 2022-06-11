package com.gsnotes.exceptions;

public class InvalidFileFormatException extends Exception{
    public InvalidFileFormatException() {
    }

    public InvalidFileFormatException(String message) {
        super(message);
    }
}
