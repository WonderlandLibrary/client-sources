/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Map;

public interface ThreadContextMap {
    public void clear();

    public boolean containsKey(String var1);

    public String get(String var1);

    public Map<String, String> getCopy();

    public Map<String, String> getImmutableMapOrNull();

    public boolean isEmpty();

    public void put(String var1, String var2);

    public void remove(String var1);
}

