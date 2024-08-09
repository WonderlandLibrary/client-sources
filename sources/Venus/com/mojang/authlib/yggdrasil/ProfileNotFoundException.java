/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

public class ProfileNotFoundException
extends RuntimeException {
    public ProfileNotFoundException() {
    }

    public ProfileNotFoundException(String string) {
        super(string);
    }

    public ProfileNotFoundException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ProfileNotFoundException(Throwable throwable) {
        super(throwable);
    }
}

