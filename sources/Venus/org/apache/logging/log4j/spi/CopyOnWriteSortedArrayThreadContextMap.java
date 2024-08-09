/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.spi.CopyOnWrite;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;

class CopyOnWriteSortedArrayThreadContextMap
implements ReadOnlyThreadContextMap,
ObjectThreadContextMap,
CopyOnWrite {
    public static final String INHERITABLE_MAP = "isThreadContextMapInheritable";
    protected static final int DEFAULT_INITIAL_CAPACITY = 16;
    protected static final String PROPERTY_NAME_INITIAL_CAPACITY = "log4j2.ThreadContext.initial.capacity";
    private static final StringMap EMPTY_CONTEXT_DATA = new SortedArrayStringMap(1);
    private final ThreadLocal<StringMap> localMap = this.createThreadLocalMap();

    private ThreadLocal<StringMap> createThreadLocalMap() {
        PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
        boolean bl = propertiesUtil.getBooleanProperty(INHERITABLE_MAP);
        if (bl) {
            return new InheritableThreadLocal<StringMap>(this){
                final CopyOnWriteSortedArrayThreadContextMap this$0;
                {
                    this.this$0 = copyOnWriteSortedArrayThreadContextMap;
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

    @Override
    public void put(String string, String string2) {
        this.putValue(string, string2);
    }

    public void putValue(String string, Object object) {
        StringMap stringMap = this.localMap.get();
        stringMap = stringMap == null ? this.createStringMap() : this.createStringMap(stringMap);
        stringMap.putValue(string, object);
        stringMap.freeze();
        this.localMap.set(stringMap);
    }

    @Override
    public void putAll(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        StringMap stringMap = this.localMap.get();
        stringMap = stringMap == null ? this.createStringMap() : this.createStringMap(stringMap);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringMap.putValue(entry.getKey(), entry.getValue());
        }
        stringMap.freeze();
        this.localMap.set(stringMap);
    }

    @Override
    public <V> void putAllValues(Map<String, V> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        StringMap stringMap = this.localMap.get();
        stringMap = stringMap == null ? this.createStringMap() : this.createStringMap(stringMap);
        for (Map.Entry<String, V> entry : map.entrySet()) {
            stringMap.putValue(entry.getKey(), entry.getValue());
        }
        stringMap.freeze();
        this.localMap.set(stringMap);
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
            StringMap stringMap2 = this.createStringMap(stringMap);
            stringMap2.remove(string);
            stringMap2.freeze();
            this.localMap.set(stringMap2);
        }
    }

    @Override
    public void removeAll(Iterable<String> iterable) {
        StringMap stringMap = this.localMap.get();
        if (stringMap != null) {
            StringMap stringMap2 = this.createStringMap(stringMap);
            for (String string : iterable) {
                stringMap2.remove(string);
            }
            stringMap2.freeze();
            this.localMap.set(stringMap2);
        }
    }

    @Override
    public void clear() {
        this.localMap.remove();
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
        return stringMap == null ? EMPTY_CONTEXT_DATA : stringMap;
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

    static {
        EMPTY_CONTEXT_DATA.freeze();
    }
}

