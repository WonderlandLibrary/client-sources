/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.util.Deque;
import java.util.Map;
import org.slf4j.spi.MDCAdapter;

public class NOPMDCAdapter
implements MDCAdapter {
    @Override
    public void clear() {
    }

    @Override
    public String get(String string) {
        return null;
    }

    @Override
    public void put(String string, String string2) {
    }

    @Override
    public void remove(String string) {
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        return null;
    }

    @Override
    public void setContextMap(Map<String, String> map) {
    }

    @Override
    public void pushByKey(String string, String string2) {
    }

    @Override
    public String popByKey(String string) {
        return null;
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String string) {
        return null;
    }

    @Override
    public void clearDequeByKey(String string) {
    }
}

