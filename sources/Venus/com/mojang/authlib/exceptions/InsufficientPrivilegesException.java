/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.exceptions;

import com.mojang.authlib.exceptions.AuthenticationException;

public class InsufficientPrivilegesException
extends AuthenticationException {
    public InsufficientPrivilegesException() {
    }

    public InsufficientPrivilegesException(String string) {
        super(string);
    }

    public InsufficientPrivilegesException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InsufficientPrivilegesException(Throwable throwable) {
        super(throwable);
    }
}

