/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.exception.RealmsServiceException;

public class RetryCallException
extends RealmsServiceException {
    public static final int DEFAULT_DELAY = 5;
    public final int delaySeconds;

    public RetryCallException(int delaySeconds) {
        super(503, "Retry operation", -1, "");
        this.delaySeconds = delaySeconds < 0 || delaySeconds > 120 ? 5 : delaySeconds;
    }
}

