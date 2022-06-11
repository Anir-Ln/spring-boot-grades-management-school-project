package com.gsnotes.exceptions;

public class IncoherentFileContentException extends Exception{
    public IncoherentFileContentException() {
    }

    public IncoherentFileContentException(String message) {
        super(message);
    }
}
