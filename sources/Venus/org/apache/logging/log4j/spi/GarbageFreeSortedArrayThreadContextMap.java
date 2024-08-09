/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;

class GarbageFreeSortedArrayThreadContextMap
implements ReadOnlyThreadContextMap,
ObjectThreadContextMap {
    public static final String INHERITABLE_MAP = "isThreadContextMapInheritable";
    protected static final int DEFAULT_INITIAL_CAPACITY = 16;
    protected static final String PROPERTY_NAME_INITIAL_CAPACITY = "log4j2.ThreadContext.initial.capacity";
    protected final ThreadLocal<StringMap> localMap = this.createThreadLocalMap();

    private ThreadLocal<StringMap> createThreadLocalMap() {
        PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
        boolean bl = propertiesUtil.getBooleanProperty(INHERITABLE_MAP);
        if (bl) {
            return new InheritableThreadLocal<StringMap>(this){
                final GarbageFreeSortedArrayThreadContextMap this$0;
                {
                    this.this$0 = garbageFreeSortedArrayThreadContextMap;
                }

                @Override
                protected StringMap childValue(StringMap stringMap) {
                    return stringMap != null ? this.this$0.createStringMap(stringMap) : null;
                }

                @Override
                protected Object childValue(Object object) {
                    return this.childValue((StringMap)object);
                }
            };
        }
        return new ThreadLocal<StringMap>();
    }

    protected StringMap createStringMap() {
        return new SortedArrayStringMap(PropertiesUtil.getProperties().getIntegerProperty(PROPERTY_NAME_INITIAL_CAPACITY, 16));
    }

    protected StringMap createStringMap(ReadOnlyStringMap readOnlyStringMap) {
        return new SortedArrayStringMap(readOnlyStringMap);
    }

    private StringMap getThreadLocalMap() {
        StringMap stringMap = this.localMap.get();
        if (stringMap == null) {
            stringMap = this.createStringMap();
            this.localMap.set(stringMap);
        }
        return stringMap;
    }

    @Override
    public void put(String string, String string2) {
        this.getThreadLocalMap().putValue(string, string2);
    }

    public void putValue(String string, Object object) {
        this.getThreadLocalMap().putValue(string, object);
    }

    @Override
    public void putAll(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        StringMap stringMap = this.getThreadLocalMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringMap.putValue(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public <V> void putAllValues(Map<String, V> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        StringMap stringMap = this.getThreadLocalMap();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            stringMap.putValue(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String get(String string) {
        return (String)this.getValue(string);
    }

    public Object getValue(String string) {
        StringMap stringMap = this.localMap.get();
        return stringMap == null ? null : stringMap.getValue(string);
    }

    @Override
    public void remove(String string) {
        StringMap stringMap = this.localMap.get();
        if (stringMap != null) {
            stringMap.remove(string);
        }
    }

    @Override
    public void removeAll(Iterable<String> iterable) {
        StringMap stringMap = this.localMap.get();
        if (stringMap != null) {
            for (String string : iterable) {
                stringMap.remove(string);
            }
        }
    }

    @Override
    public void clear() {
        StringMap stringMap = this.localMap.get();
        if (stringMap != null) {
            stringMap.clear();
        }
    }

    @Override
    public boolean containsKey(String string) {
        StringMap stringMap = this.localMap.get();
        return stringMap != null && stringMap.containsKey(string);
    }

    @Override
    public Map<String, String> getCopy() {
        StringMap stringMap = this.localMap.get();
        return stringMap == null ? new HashMap() : stringMap.toMap();
    }

    @Override
    public StringMap getReadOnlyContextData() {
        StringMap stringMap = this.localMap.get();
        if (stringMap == null) {
            stringMap = this.createStringMap();
            this.localMap.set(stringMap);
        }
        return stringMap;
    }

    @Override
    public Map<String, String> getImmutableMapOrNull() {
        StringMap stringMap = this.localMap.get();
        return stringMap == null ? null : Collections.unmodifiableMap(stringMap.toMap());
    }

    @Override
    public boolean isEmpty() {
        StringMap stringMap = this.localMap.get();
        return stringMap == null || stringMap.size() == 0;
    }

    public String toString() {
        StringMap stringMap = this.localMap.get();
        return stringMap == null ? "{}" : stringMap.toString();
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        StringMap stringMap = this.localMap.get();
        n2 = 31 * n2 + (stringMap == null ? 0 : stringMap.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof ThreadContextMap)) {
            return true;
        }
        ThreadContextMap threadContextMap = (ThreadContextMap)object;
        Map<String, String> map = this.getImmutableMapOrNull();
        Map<String, String> map2 = threadContextMap.getImmutableMapOrNull();
        return map == null ? map2 != null : !map.equals(map2);
    }
}

