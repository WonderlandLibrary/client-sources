/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.helpers.ThreadLocalMapOfStacks;
import org.slf4j.spi.MDCAdapter;

public class BasicMDCAdapter
implements MDCAdapter {
    private final ThreadLocalMapOfStacks threadLocalMapOfDeques = new ThreadLocalMapOfStacks();
    private final InheritableThreadLocal<Map<String, String>> inheritableThreadLocalMap = new InheritableThreadLocal<Map<String, String>>(this){
        final BasicMDCAdapter this$0;
        {
            this.this$0 = basicMDCAdapter;
        }

        @Override
        protected Map<String, String> childValue(Map<String, String> map) {
            if (map == null) {
                return null;
            }
            return new HashMap<String, String>(map);
        }

        @Override
        protected Object childValue(Object object) {
            return this.childValue((Map)object);
        }
    };

    @Override
    public void put(String string, String string2) {
        if (string == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        HashMap<String, String> hashMap = (HashMap<String, String>)this.inheritableThreadLocalMap.get();
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
            this.inheritableThreadLocalMap.set(hashMap);
        }
        hashMap.put(string, string2);
    }

    @Override
    public String get(String string) {
        Map map = (Map)this.inheritableThreadLocalMap.get();
        if (map != null && string != null) {
            return (String)map.get(string);
        }
        return null;
    }

    @Override
    public void remove(String string) {
        Map map = (Map)this.inheritableThreadLocalMap.get();
        if (map != null) {
            map.remove(string);
        }
    }

    @Override
    public void clear() {
        Map map = (Map)this.inheritableThreadLocalMap.get();
        if (map != null) {
            map.clear();
            this.inheritableThreadLocalMap.remove();
        }
    }

    public Set<String> getKeys() {
        Map map = (Map)this.inheritableThreadLocalMap.get();
        if (map != null) {
            return map.keySet();
        }
        return null;
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        Map map = (Map)this.inheritableThreadLocalMap.get();
        if (map != null) {
            return new HashMap<String, String>(map);
        }
        return null;
    }

    @Override
    public void setContextMap(Map<String, String> map) {
        HashMap<String, String> hashMap = null;
        if (map != null) {
            hashMap = new HashMap<String, String>(map);
        }
        this.inheritableThreadLocalMap.set(hashMap);
    }

    @Override
    public void pushByKey(String string, String string2) {
        this.threadLocalMapOfDeques.pushByKey(string, string2);
    }

    @Override
    public String popByKey(String string) {
        return this.threadLocalMapOfDeques.popByKey(string);
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String string) {
        return this.threadLocalMapOfDeques.getCopyOfDequeByKey(string);
    }

    @Override
    public void clearDequeByKey(String string) {
        this.threadLocalMapOfDeques.clearDequeByKey(string);
    }
}

