package com.mojang.realmsclient.exception;

public class RealmsHttpException extends RuntimeException
{
    public RealmsHttpException(final String s, final Exception e) {
        super(s, e);
    }
}
