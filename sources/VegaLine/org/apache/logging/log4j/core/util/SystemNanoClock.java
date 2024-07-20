/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.core.util.NanoClock;

public final class SystemNanoClock
implements NanoClock {
    @Override
    public long nanoTime() {
        return System.nanoTime();
    }
}

