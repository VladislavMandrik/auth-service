package com.example.demo.handlers;

public class ErrorMessage {

    private String message;
    private int code;
    private String moreInfo;

    public ErrorMessage(String message, int code, String moreInfo) {
        super();
        this.message = message;
        this.code = code;
        this.moreInfo = moreInfo;
    }

    public String getMessage() {

        return message;
    }

    public int getCode() {

        return code;
    }

    public String getMoreInfo() {

        return moreInfo;
    }
}

