/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.Timer;
import io.netty.util.TimerTask;

public interface Timeout {
    public Timer timer();

    public TimerTask task();

    public boolean isExpired();

    public boolean isCancelled();

    public boolean cancel();
}

