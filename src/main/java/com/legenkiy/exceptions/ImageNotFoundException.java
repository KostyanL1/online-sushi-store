package com.legenkiy.exceptions;


//Throws when needed image was skipped

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String message) {
        super(message);
    }
}
