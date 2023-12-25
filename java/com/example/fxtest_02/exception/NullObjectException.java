package com.example.fxtest_02.exception;

public class NullObjectException extends RuntimeException{
    public NullObjectException(String operation , String objectName) {
        super("can not achieve operation "+operation+" because "+objectName+" is null !");
    }
}
