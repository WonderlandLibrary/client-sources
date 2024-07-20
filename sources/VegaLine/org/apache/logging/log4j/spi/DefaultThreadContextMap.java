/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.TriConsumer;

public class DefaultThreadContextMap
implements ThreadContextMap,
ReadOnlyStringMap {
    public static final String INHERITABLE_MAP = "isThreadContextMapInheritable";
    private final boolean useMap;
    private final ThreadLocal<Map<String, String>> localMap;

    public DefaultThreadContextMap() {
        this(true);
    }

    public DefaultThreadContextMap(boolean useMap) {
        this.useMap = useMap;
        this.localMap = DefaultThreadContextMap.createThreadLocalMap(useMap);
    }

    static ThreadLocal<Map<String, String>> createThreadLocalMap(final boolean isMapEnabled) {
        PropertiesUtil managerProps = PropertiesUtil.getProperties();
        boolean inheritable = managerProps.getBooleanProperty(INHERITABLE_MAP);
        if (inheritable) {
            return new InheritableThreadLocal<Map<String, String>>(){

                @Override
                protected Map<String, String> childValue(Map<String, String> parentValue) {
                    return parentValue != null && isMapEnabled ? Collections.unmodifiableMap(new HashMap<String, String>(parentValue)) : null;
                }
            };
        }
        return new ThreadLocal<Map<String, String>>();
    }

    @Override
    public void put(String key, String value) {
        if (!this.useMap) {
            return;
        }
        Map<String, String> map = this.localMap.get();
        map = map == null ? new HashMap<String, String>(1) : new HashMap<String, String>(map);
        map.put(key, value);
        this.localMap.set(Collections.unmodifiableMap(map));
    }

    public void putAll(Map<String, String> m) {
        if (!this.useMap) {
            return;
        }
        Map<String, String> map = this.localMap.get();
        map = map == null ? new HashMap<String, String>(m.size()) : new HashMap<String, String>(map);
        for (Map.Entry<String, String> e : m.entrySet()) {
            map.put(e.getKey(), e.getValue());
        }
        this.localMap.set(Collections.unmodifiableMap(map));
    }

    @Override
    public String get(String key) {
        Map<String, String> map = this.localMap.get();
        return map == null ? null : map.get(key);
    }

    @Override
    public void remove(String key) {
        Map<String, String> map = this.localMap.get();
        if (map != null) {
            HashMap<String, String> copy = new HashMap<String, String>(map);
            copy.remove(key);
            this.localMap.set(Collections.unmodifiableMap(copy));
        }
    }

    public void removeAll(Iterable<String> keys) {
        Map<String, String> map = this.localMap.get();
        if (map != null) {
            HashMap<String, String> copy = new HashMap<String, String>(map);
            for (String key : keys) {
                copy.remove(key);
            }
            this.localMap.set(Collections.unmodifiableMap(copy));
        }
    }

    @Override
    public void clear() {
        this.localMap.remove();
    }

    @Override
    public Map<String, String> toMap() {
        return this.getCopy();
    }

    @Override
    public boolean containsKey(String key) {
        Map<String, String> map = this.localMap.get();
        return map != null && map.containsKey(key);
    }

    @Override
    public <V> void forEach(BiConsumer<String, ? super V> action) {
        Map<String, String> map = this.localMap.get();
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public <V, S> void forEach(TriConsumer<String, ? super V, S> action, S state) {
        Map<String, String> map = this.localMap.get();
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            action.accept(entry.getKey(), entry.getValue(), state);
        }
    }

    @Override
    public <V> V getValue(String key) {
        Map<String, String> map = this.localMap.get();
        return (V)(map == null ? null : map.get(key));
    }

    @Override
    public Map<String, String> getCopy() {
        Map<String, String> map = this.localMap.get();
        return map == null ? new HashMap<String, String>() : new HashMap<String, String>(map);
    }

    @Override
    public Map<String, String> getImmutableMapOrNull() {
        return this.localMap.get();
    }

    @Override
    public boolean isEmpty() {
        Map<String, String> map = this.localMap.get();
        return map == null || map.size() == 0;
    }

    @Override
    public int size() {
        Map<String, String> map = this.localMap.get();
        return map == null ? 0 : map.size();
    }

    public String toString() {
        Map<String, String> map = this.localMap.get();
        return map == null ? "{}" : map.toString();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        Map<String, String> map = this.localMap.get();
        result = 31 * result + (map == null ? 0 : map.hashCode());
        result = 31 * result + Boolean.valueOf(this.useMap).hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        ThreadContextMap other;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof DefaultThreadContextMap) {
            other = (DefaultThreadContextMap)obj;
            if (this.useMap != ((DefaultThreadContextMap)other).useMap) {
                return false;
            }
        }
        if (!(obj instanceof ThreadContextMap)) {
            return false;
        }
        other = (ThreadContextMap)obj;
        Map<String, String> map = this.localMap.get();
        Map<String, String> otherMap = other.getImmutableMapOrNull();
        return !(map == null ? otherMap != null : !map.equals(otherMap));
    }
}

