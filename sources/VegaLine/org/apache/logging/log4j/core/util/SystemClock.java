/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.core.util.Clock;

public final class SystemClock
implements Clock {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}

