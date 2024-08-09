/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LifeCycle;

public interface LifeCycle2
extends LifeCycle {
    public boolean stop(long var1, TimeUnit var3);
}

