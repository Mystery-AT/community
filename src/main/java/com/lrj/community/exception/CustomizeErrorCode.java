package com.lrj.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("不要放弃，继续");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    private CustomizeErrorCode(String message){
        this.message = message;
    }
}
