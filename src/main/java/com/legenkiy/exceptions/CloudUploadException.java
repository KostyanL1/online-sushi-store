package com.legenkiy.exceptions;


//Thrown when an error occurred while uploading photos to the cloud


public class CloudUploadException extends RuntimeException {
    public CloudUploadException(String message) {
        super(message);
    }
}
