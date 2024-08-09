/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import org.apache.http.impl.client.Clock;

class SystemClock
implements Clock {
    SystemClock() {
    }

    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}

