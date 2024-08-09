/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Clock;
import java.util.Date;

public class FixedClock
implements Clock {
    private final Date now;

    public FixedClock() {
        this(new Date());
    }

    public FixedClock(Date date) {
        this.now = date;
    }

    public FixedClock(long l) {
        this(new Date(l));
    }

    @Override
    public Date now() {
        return this.now;
    }
}

