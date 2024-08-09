/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.exceptions;

import com.mojang.authlib.exceptions.AuthenticationException;

public class AuthenticationUnavailableException
extends AuthenticationException {
    public AuthenticationUnavailableException() {
    }

    public AuthenticationUnavailableException(String string) {
        super(string);
    }

    public AuthenticationUnavailableException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AuthenticationUnavailableException(Throwable throwable) {
        super(throwable);
    }
}

