/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

public class ProfileIncompleteException
extends RuntimeException {
    public ProfileIncompleteException() {
    }

    public ProfileIncompleteException(String string) {
        super(string);
    }

    public ProfileIncompleteException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ProfileIncompleteException(Throwable throwable) {
        super(throwable);
    }
}

