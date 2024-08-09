/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.exceptions;

public class AuthenticationException
extends Exception {
    public AuthenticationException() {
    }

    public AuthenticationException(String string) {
        super(string);
    }

    public AuthenticationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AuthenticationException(Throwable throwable) {
        super(throwable);
    }
}

