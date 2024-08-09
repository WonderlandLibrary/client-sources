/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.exceptions;

import com.mojang.authlib.exceptions.AuthenticationException;

public class InvalidCredentialsException
extends AuthenticationException {
    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String string) {
        super(string);
    }

    public InvalidCredentialsException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidCredentialsException(Throwable throwable) {
        super(throwable);
    }
}

