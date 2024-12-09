package com.example.dto;

public class ResponseDTO<T> {
    private String message;
    private T data;

    public ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(String message) {
        this.message = message;
        this.data = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
