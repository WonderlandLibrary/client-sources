/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;
import org.apache.logging.log4j.util.TriConsumer;

class JdkMapAdapterStringMap
implements StringMap {
    private static final long serialVersionUID = -7348247784983193612L;
    private static final String FROZEN = "Frozen collection cannot be modified";
    private static final Comparator<? super String> NULL_FIRST_COMPARATOR = new Comparator<String>(){

        @Override
        public int compare(String string, String string2) {
            if (string == null) {
                return 1;
            }
            if (string2 == null) {
                return 0;
            }
            return string.compareTo(string2);
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((String)object, (String)object2);
        }
    };
    private final Map<String, String> map;
    private boolean immutable = false;
    private transient String[] sortedKeys;
    private static TriConsumer<String, String, Map<String, String>> PUT_ALL = new TriConsumer<String, String, Map<String, String>>(){

        @Override
        public void accept(String string, String string2, Map<String, String> map) {
            map.put(string, string2);
        }

        @Override
        public void accept(Object object, Object object2, Object object3) {
            this.accept((String)object, (String)object2, (Map)object3);
        }
    };

    public JdkMapAdapterStringMap() {
        this(new HashMap<String, String>());
    }

    public JdkMapAdapterStringMap(Map<String, String> map) {
        this.map = Objects.requireNonNull(map, "map");
    }

    @Override
    public Map<String, String> toMap() {
        return this.map;
    }

    private void assertNotFrozen() {
        if (this.immutable) {
            throw new UnsupportedOperationException(FROZEN);
        }
    }

    @Override
    public boolean containsKey(String string) {
        return this.map.containsKey(string);
    }

    @Override
    public <V> void forEach(BiConsumer<String, ? super V> biConsumer) {
        String[] stringArray = this.getSortedKeys();
        for (int i = 0; i < stringArray.length; ++i) {
            biConsumer.accept(stringArray[i], this.map.get(stringArray[i]));
        }
    }

    @Override
    public <V, S> void forEach(TriConsumer<String, ? super V, S> triConsumer, S s) {
        String[] stringArray = this.getSortedKeys();
        for (int i = 0; i < stringArray.length; ++i) {
            triConsumer.accept(stringArray[i], this.map.get(stringArray[i]), s);
        }
    }

    private String[] getSortedKeys() {
        if (this.sortedKeys == null) {
            this.sortedKeys = this.map.keySet().toArray(new String[this.map.size()]);
            Arrays.sort(this.sortedKeys, NULL_FIRST_COMPARATOR);
        }
        return this.sortedKeys;
    }

    @Override
    public <V> V getValue(String string) {
        return (V)this.map.get(string);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public void clear() {
        if (this.map.isEmpty()) {
            return;
        }
        this.assertNotFrozen();
        this.map.clear();
        this.sortedKeys = null;
    }

    @Override
    public void freeze() {
        this.immutable = true;
    }

    @Override
    public boolean isFrozen() {
        return this.immutable;
    }

    @Override
    public void putAll(ReadOnlyStringMap readOnlyStringMap) {
        this.assertNotFrozen();
        readOnlyStringMap.forEach(PUT_ALL, this.map);
        this.sortedKeys = null;
    }

    @Override
    public void putValue(String string, Object object) {
        this.assertNotFrozen();
        this.map.put(string, object == null ? null : String.valueOf(object));
        this.sortedKeys = null;
    }

    @Override
    public void remove(String string) {
        if (!this.map.containsKey(string)) {
            return;
        }
        this.assertNotFrozen();
        this.map.remove(string);
        this.sortedKeys = null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.map.size() * 13);
        stringBuilder.append('{');
        String[] stringArray = this.getSortedKeys();
        for (int i = 0; i < stringArray.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(stringArray[i]).append('=').append(this.map.get(stringArray[i]));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof JdkMapAdapterStringMap)) {
            return true;
        }
        JdkMapAdapterStringMap jdkMapAdapterStringMap = (JdkMapAdapterStringMap)object;
        return this.map.equals(jdkMapAdapterStringMap.map) && this.immutable == jdkMapAdapterStringMap.immutable;
    }

    @Override
    public int hashCode() {
        return this.map.hashCode() + (this.immutable ? 31 : 0);
    }
}

