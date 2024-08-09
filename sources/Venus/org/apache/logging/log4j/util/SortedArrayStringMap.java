/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.MarshalledObject;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.IndexedStringMap;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;
import org.apache.logging.log4j.util.TriConsumer;

public class SortedArrayStringMap
implements IndexedStringMap {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;
    private static final long serialVersionUID = -5748905872274478116L;
    private static final int HASHVAL = 31;
    private static final TriConsumer<String, Object, StringMap> PUT_ALL = new TriConsumer<String, Object, StringMap>(){

        @Override
        public void accept(String string, Object object, StringMap stringMap) {
            stringMap.putValue(string, object);
        }

        @Override
        public void accept(Object object, Object object2, Object object3) {
            this.accept((String)object, object2, (StringMap)object3);
        }
    };
    private static final String[] EMPTY = new String[0];
    private static final String FROZEN = "Frozen collection cannot be modified";
    private transient String[] keys = EMPTY;
    private transient Object[] values = EMPTY;
    private transient int size;
    private int threshold;
    private boolean immutable;
    private transient boolean iterating;

    public SortedArrayStringMap() {
        this(4);
    }

    public SortedArrayStringMap(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Initial capacity must be at least one but was " + n);
        }
        this.threshold = SortedArrayStringMap.ceilingNextPowerOfTwo(n);
    }

    public SortedArrayStringMap(ReadOnlyStringMap readOnlyStringMap) {
        if (readOnlyStringMap instanceof SortedArrayStringMap) {
            this.initFrom0((SortedArrayStringMap)readOnlyStringMap);
        } else if (readOnlyStringMap != null) {
            this.resize(SortedArrayStringMap.ceilingNextPowerOfTwo(readOnlyStringMap.size()));
            readOnlyStringMap.forEach(PUT_ALL, this);
        }
    }

    public SortedArrayStringMap(Map<String, ?> map) {
        this.resize(SortedArrayStringMap.ceilingNextPowerOfTwo(map.size()));
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            this.putValue(entry.getKey(), entry.getValue());
        }
    }

    private void assertNotFrozen() {
        if (this.immutable) {
            throw new UnsupportedOperationException(FROZEN);
        }
    }

    private void assertNoConcurrentModification() {
        if (this.iterating) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public void clear() {
        if (this.keys == EMPTY) {
            return;
        }
        this.assertNotFrozen();
        this.assertNoConcurrentModification();
        Arrays.fill(this.keys, 0, this.size, null);
        Arrays.fill(this.values, 0, this.size, null);
        this.size = 0;
    }

    @Override
    public boolean containsKey(String string) {
        return this.indexOfKey(string) >= 0;
    }

    @Override
    public Map<String, String> toMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>(this.size());
        for (int i = 0; i < this.size(); ++i) {
            Object v = this.getValueAt(i);
            hashMap.put(this.getKeyAt(i), v == null ? null : String.valueOf(v));
        }
        return hashMap;
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
    public <V> V getValue(String string) {
        int n = this.indexOfKey(string);
        if (n < 0) {
            return null;
        }
        return (V)this.values[n];
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int indexOfKey(String string) {
        if (this.keys == EMPTY) {
            return 1;
        }
        if (string == null) {
            return this.nullKeyIndex();
        }
        int n = this.size > 0 && this.keys[0] == null ? 1 : 0;
        return Arrays.binarySearch(this.keys, n, this.size, string);
    }

    private int nullKeyIndex() {
        return this.size > 0 && this.keys[0] == null ? 0 : -1;
    }

    @Override
    public void putValue(String string, Object object) {
        int n;
        this.assertNotFrozen();
        this.assertNoConcurrentModification();
        if (this.keys == EMPTY) {
            this.inflateTable(this.threshold);
        }
        if ((n = this.indexOfKey(string)) >= 0) {
            this.keys[n] = string;
            this.values[n] = object;
        } else {
            this.insertAt(~n, string, object);
        }
    }

    private void insertAt(int n, String string, Object object) {
        this.ensureCapacity();
        System.arraycopy(this.keys, n, this.keys, n + 1, this.size - n);
        System.arraycopy(this.values, n, this.values, n + 1, this.size - n);
        this.keys[n] = string;
        this.values[n] = object;
        ++this.size;
    }

    @Override
    public void putAll(ReadOnlyStringMap readOnlyStringMap) {
        if (readOnlyStringMap == this || readOnlyStringMap == null || readOnlyStringMap.isEmpty()) {
            return;
        }
        this.assertNotFrozen();
        this.assertNoConcurrentModification();
        if (readOnlyStringMap instanceof SortedArrayStringMap) {
            if (this.size == 0) {
                this.initFrom0((SortedArrayStringMap)readOnlyStringMap);
            } else {
                this.merge((SortedArrayStringMap)readOnlyStringMap);
            }
        } else if (readOnlyStringMap != null) {
            readOnlyStringMap.forEach(PUT_ALL, this);
        }
    }

    private void initFrom0(SortedArrayStringMap sortedArrayStringMap) {
        if (this.keys.length < sortedArrayStringMap.size) {
            this.keys = new String[sortedArrayStringMap.threshold];
            this.values = new Object[sortedArrayStringMap.threshold];
        }
        System.arraycopy(sortedArrayStringMap.keys, 0, this.keys, 0, sortedArrayStringMap.size);
        System.arraycopy(sortedArrayStringMap.values, 0, this.values, 0, sortedArrayStringMap.size);
        this.size = sortedArrayStringMap.size;
        this.threshold = sortedArrayStringMap.threshold;
    }

    private void merge(SortedArrayStringMap sortedArrayStringMap) {
        String[] stringArray = this.keys;
        Object[] objectArray = this.values;
        int n = sortedArrayStringMap.size + this.size;
        this.threshold = SortedArrayStringMap.ceilingNextPowerOfTwo(n);
        if (this.keys.length < this.threshold) {
            this.keys = new String[this.threshold];
            this.values = new Object[this.threshold];
        }
        boolean bl = true;
        if (sortedArrayStringMap.size() > this.size()) {
            System.arraycopy(stringArray, 0, this.keys, sortedArrayStringMap.size, this.size);
            System.arraycopy(objectArray, 0, this.values, sortedArrayStringMap.size, this.size);
            System.arraycopy(sortedArrayStringMap.keys, 0, this.keys, 0, sortedArrayStringMap.size);
            System.arraycopy(sortedArrayStringMap.values, 0, this.values, 0, sortedArrayStringMap.size);
            this.size = sortedArrayStringMap.size;
            bl = false;
        } else {
            System.arraycopy(stringArray, 0, this.keys, 0, this.size);
            System.arraycopy(objectArray, 0, this.values, 0, this.size);
            System.arraycopy(sortedArrayStringMap.keys, 0, this.keys, this.size, sortedArrayStringMap.size);
            System.arraycopy(sortedArrayStringMap.values, 0, this.values, this.size, sortedArrayStringMap.size);
        }
        for (int i = this.size; i < n; ++i) {
            int n2 = this.indexOfKey(this.keys[i]);
            if (n2 < 0) {
                this.insertAt(~n2, this.keys[i], this.values[i]);
                continue;
            }
            if (!bl) continue;
            this.keys[n2] = this.keys[i];
            this.values[n2] = this.values[i];
        }
        Arrays.fill(this.keys, this.size, n, null);
        Arrays.fill(this.values, this.size, n, null);
    }

    private void ensureCapacity() {
        if (this.size >= this.threshold) {
            this.resize(this.threshold * 2);
        }
    }

    private void resize(int n) {
        String[] stringArray = this.keys;
        Object[] objectArray = this.values;
        this.keys = new String[n];
        this.values = new Object[n];
        System.arraycopy(stringArray, 0, this.keys, 0, this.size);
        System.arraycopy(objectArray, 0, this.values, 0, this.size);
        this.threshold = n;
    }

    private void inflateTable(int n) {
        this.threshold = n;
        this.keys = new String[n];
        this.values = new Object[n];
    }

    @Override
    public void remove(String string) {
        if (this.keys == EMPTY) {
            return;
        }
        int n = this.indexOfKey(string);
        if (n >= 0) {
            this.assertNotFrozen();
            this.assertNoConcurrentModification();
            System.arraycopy(this.keys, n + 1, this.keys, n, this.size - 1 - n);
            System.arraycopy(this.values, n + 1, this.values, n, this.size - 1 - n);
            this.keys[this.size - 1] = null;
            this.values[this.size - 1] = null;
            --this.size;
        }
    }

    @Override
    public String getKeyAt(int n) {
        if (n < 0 || n >= this.size) {
            return null;
        }
        return this.keys[n];
    }

    @Override
    public <V> V getValueAt(int n) {
        if (n < 0 || n >= this.size) {
            return null;
        }
        return (V)this.values[n];
    }

    @Override
    public int size() {
        return this.size;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <V> void forEach(BiConsumer<String, ? super V> biConsumer) {
        this.iterating = true;
        try {
            for (int i = 0; i < this.size; ++i) {
                biConsumer.accept(this.keys[i], this.values[i]);
            }
        } finally {
            this.iterating = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <V, T> void forEach(TriConsumer<String, ? super V, T> triConsumer, T t) {
        this.iterating = true;
        try {
            for (int i = 0; i < this.size; ++i) {
                triConsumer.accept(this.keys[i], this.values[i], t);
            }
        } finally {
            this.iterating = false;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof SortedArrayStringMap)) {
            return true;
        }
        SortedArrayStringMap sortedArrayStringMap = (SortedArrayStringMap)object;
        if (this.size() != sortedArrayStringMap.size()) {
            return true;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (!Objects.equals(this.keys[i], sortedArrayStringMap.keys[i])) {
                return true;
            }
            if (Objects.equals(this.values[i], sortedArrayStringMap.values[i])) continue;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int n = 37;
        n = 31 * n + this.size;
        n = 31 * n + SortedArrayStringMap.hashCode(this.keys, this.size);
        n = 31 * n + SortedArrayStringMap.hashCode(this.values, this.size);
        return n;
    }

    private static int hashCode(Object[] objectArray, int n) {
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            n2 = 31 * n2 + (objectArray[i] == null ? 0 : objectArray[i].hashCode());
        }
        return n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(256);
        stringBuilder.append('{');
        for (int i = 0; i < this.size; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.keys[i]).append('=');
            stringBuilder.append(this.values[i] == this ? "(this map)" : this.values[i]);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (this.keys == EMPTY) {
            objectOutputStream.writeInt(SortedArrayStringMap.ceilingNextPowerOfTwo(this.threshold));
        } else {
            objectOutputStream.writeInt(this.keys.length);
        }
        objectOutputStream.writeInt(this.size);
        if (this.size > 0) {
            for (int i = 0; i < this.size; ++i) {
                objectOutputStream.writeObject(this.keys[i]);
                try {
                    objectOutputStream.writeObject(new MarshalledObject<Object>(this.values[i]));
                    continue;
                } catch (Exception exception) {
                    this.handleSerializationException(exception, i, this.keys[i]);
                    objectOutputStream.writeObject(null);
                }
            }
        }
    }

    private static int ceilingNextPowerOfTwo(int n) {
        int n2 = 32;
        return 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keys = EMPTY;
        this.values = EMPTY;
        int n = objectInputStream.readInt();
        if (n < 0) {
            throw new InvalidObjectException("Illegal capacity: " + n);
        }
        int n2 = objectInputStream.readInt();
        if (n2 < 0) {
            throw new InvalidObjectException("Illegal mappings count: " + n2);
        }
        if (n2 > 0) {
            this.inflateTable(n);
        } else {
            this.threshold = n;
        }
        for (int i = 0; i < n2; ++i) {
            this.keys[i] = (String)objectInputStream.readObject();
            try {
                MarshalledObject marshalledObject = (MarshalledObject)objectInputStream.readObject();
                this.values[i] = marshalledObject == null ? null : marshalledObject.get();
                continue;
            } catch (Exception | LinkageError throwable) {
                this.handleSerializationException(throwable, i, this.keys[i]);
                this.values[i] = null;
            }
        }
        this.size = n2;
    }

    private void handleSerializationException(Throwable throwable, int n, String string) {
        StatusLogger.getLogger().warn("Ignoring {} for key[{}] ('{}')", (Object)String.valueOf(throwable), (Object)n, (Object)this.keys[n]);
    }
}

