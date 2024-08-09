/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.exceptions;

import com.mojang.authlib.exceptions.InvalidCredentialsException;

public class UserMigratedException
extends InvalidCredentialsException {
    public UserMigratedException() {
    }

    public UserMigratedException(String string) {
        super(string);
    }

    public UserMigratedException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public UserMigratedException(Throwable throwable) {
        super(throwable);
    }
}

