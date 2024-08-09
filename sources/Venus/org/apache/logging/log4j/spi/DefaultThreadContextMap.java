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

    public DefaultThreadContextMap(boolean bl) {
        this.useMap = bl;
        this.localMap = DefaultThreadContextMap.createThreadLocalMap(bl);
    }

    static ThreadLocal<Map<String, String>> createThreadLocalMap(boolean bl) {
        PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
        boolean bl2 = propertiesUtil.getBooleanProperty(INHERITABLE_MAP);
        if (bl2) {
            return new InheritableThreadLocal<Map<String, String>>(bl){
                final boolean val$isMapEnabled;
                {
                    this.val$isMapEnabled = bl;
                }

                @Override
                protected Map<String, String> childValue(Map<String, String> map) {
                    return map != null && this.val$isMapEnabled ? Collections.unmodifiableMap(new HashMap<String, String>(map)) : null;
                }

                @Override
                protected Object childValue(Object object) {
                    return this.childValue((Map)object);
                }
            };
        }
        return new ThreadLocal<Map<String, String>>();
    }

    @Override
    public void put(String string, String string2) {
        if (!this.useMap) {
            return;
        }
        Map<String, String> map = this.localMap.get();
        map = map == null ? new HashMap<String, String>(1) : new HashMap<String, String>(map);
        map.put(string, string2);
        this.localMap.set(Collections.unmodifiableMap(map));
    }

    public void putAll(Map<String, String> map) {
        if (!this.useMap) {
            return;
        }
        Map<String, String> map2 = this.localMap.get();
        map2 = map2 == null ? new HashMap<String, String>(map.size()) : new HashMap<String, String>(map2);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getKey(), entry.getValue());
        }
        this.localMap.set(Collections.unmodifiableMap(map2));
    }

    @Override
    public String get(String string) {
        Map<String, String> map = this.localMap.get();
        return map == null ? null : map.get(string);
    }

    @Override
    public void remove(String string) {
        Map<String, String> map = this.localMap.get();
        if (map != null) {
            HashMap<String, String> hashMap = new HashMap<String, String>(map);
            hashMap.remove(string);
            this.localMap.set(Collections.unmodifiableMap(hashMap));
        }
    }

    public void removeAll(Iterable<String> iterable) {
        Map<String, String> map = this.localMap.get();
        if (map != null) {
            HashMap<String, String> hashMap = new HashMap<String, String>(map);
            for (String string : iterable) {
                hashMap.remove(string);
            }
            this.localMap.set(Collections.unmodifiableMap(hashMap));
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
    public boolean containsKey(String string) {
        Map<String, String> map = this.localMap.get();
        return map != null && map.containsKey(string);
    }

    @Override
    public <V> void forEach(BiConsumer<String, ? super V> biConsumer) {
        Map<String, String> map = this.localMap.get();
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public <V, S> void forEach(TriConsumer<String, ? super V, S> triConsumer, S s) {
        Map<String, String> map = this.localMap.get();
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            triConsumer.accept(entry.getKey(), entry.getValue(), s);
        }
    }

    @Override
    public <V> V getValue(String string) {
        Map<String, String> map = this.localMap.get();
        return (V)(map == null ? null : map.get(string));
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
        int n = 31;
        int n2 = 1;
        Map<String, String> map = this.localMap.get();
        n2 = 31 * n2 + (map == null ? 0 : map.hashCode());
        n2 = 31 * n2 + Boolean.valueOf(this.useMap).hashCode();
        return n2;
    }

    public boolean equals(Object object) {
        ThreadContextMap threadContextMap;
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (object instanceof DefaultThreadContextMap) {
            threadContextMap = (DefaultThreadContextMap)object;
            if (this.useMap != ((DefaultThreadContextMap)threadContextMap).useMap) {
                return true;
            }
        }
        if (!(object instanceof ThreadContextMap)) {
            return true;
        }
        threadContextMap = (ThreadContextMap)object;
        Map<String, String> map = this.localMap.get();
        Map<String, String> map2 = threadContextMap.getImmutableMapOrNull();
        return map == null ? map2 != null : !map.equals(map2);
    }
}

