/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Clock;
import java.util.Date;

public class DefaultClock
implements Clock {
    public static final Clock INSTANCE = new DefaultClock();

    @Override
    public Date now() {
        return new Date();
    }
}

