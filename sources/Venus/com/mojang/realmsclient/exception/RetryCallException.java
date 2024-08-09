/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.exception.RealmsServiceException;

public class RetryCallException
extends RealmsServiceException {
    public final int field_224985_e;

    public RetryCallException(int n, int n2) {
        super(n2, "Retry operation", -1, "");
        this.field_224985_e = n >= 0 && n <= 120 ? n : 5;
    }
}

