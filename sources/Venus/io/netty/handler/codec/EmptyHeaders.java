/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.handler.codec.Headers;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyHeaders<K, V, T extends Headers<K, V, T>>
implements Headers<K, V, T> {
    @Override
    public V get(K k) {
        return null;
    }

    @Override
    public V get(K k, V v) {
        return v;
    }

    @Override
    public V getAndRemove(K k) {
        return null;
    }

    @Override
    public V getAndRemove(K k, V v) {
        return v;
    }

    @Override
    public List<V> getAll(K k) {
        return Collections.emptyList();
    }

    @Override
    public List<V> getAllAndRemove(K k) {
        return Collections.emptyList();
    }

    @Override
    public Boolean getBoolean(K k) {
        return null;
    }

    @Override
    public boolean getBoolean(K k, boolean bl) {
        return bl;
    }

    @Override
    public Byte getByte(K k) {
        return null;
    }

    @Override
    public byte getByte(K k, byte by) {
        return by;
    }

    @Override
    public Character getChar(K k) {
        return null;
    }

    @Override
    public char getChar(K k, char c) {
        return c;
    }

    @Override
    public Short getShort(K k) {
        return null;
    }

    @Override
    public short getShort(K k, short s) {
        return s;
    }

    @Override
    public Integer getInt(K k) {
        return null;
    }

    @Override
    public int getInt(K k, int n) {
        return n;
    }

    @Override
    public Long getLong(K k) {
        return null;
    }

    @Override
    public long getLong(K k, long l) {
        return l;
    }

    @Override
    public Float getFloat(K k) {
        return null;
    }

    @Override
    public float getFloat(K k, float f) {
        return f;
    }

    @Override
    public Double getDouble(K k) {
        return null;
    }

    @Override
    public double getDouble(K k, double d) {
        return d;
    }

    @Override
    public Long getTimeMillis(K k) {
        return null;
    }

    @Override
    public long getTimeMillis(K k, long l) {
        return l;
    }

    @Override
    public Boolean getBooleanAndRemove(K k) {
        return null;
    }

    @Override
    public boolean getBooleanAndRemove(K k, boolean bl) {
        return bl;
    }

    @Override
    public Byte getByteAndRemove(K k) {
        return null;
    }

    @Override
    public byte getByteAndRemove(K k, byte by) {
        return by;
    }

    @Override
    public Character getCharAndRemove(K k) {
        return null;
    }

    @Override
    public char getCharAndRemove(K k, char c) {
        return c;
    }

    @Override
    public Short getShortAndRemove(K k) {
        return null;
    }

    @Override
    public short getShortAndRemove(K k, short s) {
        return s;
    }

    @Override
    public Integer getIntAndRemove(K k) {
        return null;
    }

    @Override
    public int getIntAndRemove(K k, int n) {
        return n;
    }

    @Override
    public Long getLongAndRemove(K k) {
        return null;
    }

    @Override
    public long getLongAndRemove(K k, long l) {
        return l;
    }

    @Override
    public Float getFloatAndRemove(K k) {
        return null;
    }

    @Override
    public float getFloatAndRemove(K k, float f) {
        return f;
    }

    @Override
    public Double getDoubleAndRemove(K k) {
        return null;
    }

    @Override
    public double getDoubleAndRemove(K k, double d) {
        return d;
    }

    @Override
    public Long getTimeMillisAndRemove(K k) {
        return null;
    }

    @Override
    public long getTimeMillisAndRemove(K k, long l) {
        return l;
    }

    @Override
    public boolean contains(K k) {
        return true;
    }

    @Override
    public boolean contains(K k, V v) {
        return true;
    }

    @Override
    public boolean containsObject(K k, Object object) {
        return true;
    }

    @Override
    public boolean containsBoolean(K k, boolean bl) {
        return true;
    }

    @Override
    public boolean containsByte(K k, byte by) {
        return true;
    }

    @Override
    public boolean containsChar(K k, char c) {
        return true;
    }

    @Override
    public boolean containsShort(K k, short s) {
        return true;
    }

    @Override
    public boolean containsInt(K k, int n) {
        return true;
    }

    @Override
    public boolean containsLong(K k, long l) {
        return true;
    }

    @Override
    public boolean containsFloat(K k, float f) {
        return true;
    }

    @Override
    public boolean containsDouble(K k, double d) {
        return true;
    }

    @Override
    public boolean containsTimeMillis(K k, long l) {
        return true;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Set<K> names() {
        return Collections.emptySet();
    }

    @Override
    public T add(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T add(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T add(K k, V ... VArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addObject(K k, Object object) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addObject(K k, Object ... objectArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addBoolean(K k, boolean bl) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addByte(K k, byte by) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addChar(K k, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addShort(K k, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addInt(K k, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addLong(K k, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addFloat(K k, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addDouble(K k, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T addTimeMillis(K k, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T add(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T set(K k, V v) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T set(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T set(K k, V ... VArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setObject(K k, Object object) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setObject(K k, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setObject(K k, Object ... objectArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setBoolean(K k, boolean bl) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setByte(K k, byte by) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setChar(K k, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setShort(K k, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setInt(K k, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setLong(K k, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setFloat(K k, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setDouble(K k, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setTimeMillis(K k, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T set(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public boolean remove(K k) {
        return true;
    }

    @Override
    public T clear() {
        return this.thisT();
    }

    public Iterator<V> valueIterator(K k) {
        List list = Collections.emptyList();
        return list.iterator();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        List list = Collections.emptyList();
        return list.iterator();
    }

    public boolean equals(Object object) {
        if (!(object instanceof Headers)) {
            return true;
        }
        Headers headers = (Headers)object;
        return this.isEmpty() && headers.isEmpty();
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return this.getClass().getSimpleName() + '[' + ']';
    }

    private T thisT() {
        return (T)this;
    }
}

