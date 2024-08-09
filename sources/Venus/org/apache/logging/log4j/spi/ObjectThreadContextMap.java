/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Map;
import org.apache.logging.log4j.spi.CleanableThreadContextMap;

public interface ObjectThreadContextMap
extends CleanableThreadContextMap {
    public <V> V getValue(String var1);

    public <V> void putValue(String var1, V var2);

    public <V> void putAllValues(Map<String, V> var1);
}

