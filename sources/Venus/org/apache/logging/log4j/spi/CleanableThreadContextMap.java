/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.spi.ThreadContextMap2;

public interface CleanableThreadContextMap
extends ThreadContextMap2 {
    public void removeAll(Iterable<String> var1);
}

