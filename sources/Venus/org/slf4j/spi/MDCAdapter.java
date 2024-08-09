/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.spi;

import java.util.Deque;
import java.util.Map;

public interface MDCAdapter {
    public void put(String var1, String var2);

    public String get(String var1);

    public void remove(String var1);

    public void clear();

    public Map<String, String> getCopyOfContextMap();

    public void setContextMap(Map<String, String> var1);

    public void pushByKey(String var1, String var2);

    public String popByKey(String var1);

    public Deque<String> getCopyOfDequeByKey(String var1);

    public void clearDequeByKey(String var1);
}

