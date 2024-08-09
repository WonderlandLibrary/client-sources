/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.handler.codec.Headers;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class DefaultHeaders<K, V, T extends Headers<K, V, T>>
implements Headers<K, V, T> {
    static final int HASH_CODE_SEED = -1028477387;
    private final HeaderEntry<K, V>[] entries;
    protected final HeaderEntry<K, V> head;
    private final byte hashMask;
    private final ValueConverter<V> valueConverter;
    private final NameValidator<K> nameValidator;
    private final HashingStrategy<K> hashingStrategy;
    int size;

    public DefaultHeaders(ValueConverter<V> valueConverter) {
        this(HashingStrategy.JAVA_HASHER, valueConverter);
    }

    public DefaultHeaders(ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
        this(HashingStrategy.JAVA_HASHER, valueConverter, nameValidator);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter) {
        this(hashingStrategy, valueConverter, NameValidator.NOT_NULL);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
        this(hashingStrategy, valueConverter, nameValidator, 16);
    }

    public DefaultHeaders(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator, int n) {
        this.valueConverter = ObjectUtil.checkNotNull(valueConverter, "valueConverter");
        this.nameValidator = ObjectUtil.checkNotNull(nameValidator, "nameValidator");
        this.hashingStrategy = ObjectUtil.checkNotNull(hashingStrategy, "nameHashingStrategy");
        this.entries = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(n, 128)))];
        this.hashMask = (byte)(this.entries.length - 1);
        this.head = new HeaderEntry();
    }

    @Override
    public V get(K k) {
        ObjectUtil.checkNotNull(k, "name");
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        HeaderEntry<K, V> headerEntry = this.entries[n2];
        V v = null;
        while (headerEntry != null) {
            if (headerEntry.hash == n && this.hashingStrategy.equals(k, headerEntry.key)) {
                v = headerEntry.value;
            }
            headerEntry = headerEntry.next;
        }
        return v;
    }

    @Override
    public V get(K k, V v) {
        V v2 = this.get(k);
        if (v2 == null) {
            return v;
        }
        return v2;
    }

    @Override
    public V getAndRemove(K k) {
        int n = this.hashingStrategy.hashCode(k);
        return this.remove0(n, this.index(n), ObjectUtil.checkNotNull(k, "name"));
    }

    @Override
    public V getAndRemove(K k, V v) {
        V v2 = this.getAndRemove(k);
        if (v2 == null) {
            return v;
        }
        return v2;
    }

    @Override
    public List<V> getAll(K k) {
        ObjectUtil.checkNotNull(k, "name");
        LinkedList<V> linkedList = new LinkedList<V>();
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        HeaderEntry<K, V> headerEntry = this.entries[n2];
        while (headerEntry != null) {
            if (headerEntry.hash == n && this.hashingStrategy.equals(k, headerEntry.key)) {
                linkedList.addFirst(headerEntry.getValue());
            }
            headerEntry = headerEntry.next;
        }
        return linkedList;
    }

    public Iterator<V> valueIterator(K k) {
        return new ValueIterator(this, k);
    }

    @Override
    public List<V> getAllAndRemove(K k) {
        List<V> list = this.getAll(k);
        this.remove(k);
        return list;
    }

    @Override
    public boolean contains(K k) {
        return this.get(k) != null;
    }

    @Override
    public boolean containsObject(K k, Object object) {
        return this.contains(k, this.valueConverter.convertObject(ObjectUtil.checkNotNull(object, "value")));
    }

    @Override
    public boolean containsBoolean(K k, boolean bl) {
        return this.contains(k, this.valueConverter.convertBoolean(bl));
    }

    @Override
    public boolean containsByte(K k, byte by) {
        return this.contains(k, this.valueConverter.convertByte(by));
    }

    @Override
    public boolean containsChar(K k, char c) {
        return this.contains(k, this.valueConverter.convertChar(c));
    }

    @Override
    public boolean containsShort(K k, short s) {
        return this.contains(k, this.valueConverter.convertShort(s));
    }

    @Override
    public boolean containsInt(K k, int n) {
        return this.contains(k, this.valueConverter.convertInt(n));
    }

    @Override
    public boolean containsLong(K k, long l) {
        return this.contains(k, this.valueConverter.convertLong(l));
    }

    @Override
    public boolean containsFloat(K k, float f) {
        return this.contains(k, this.valueConverter.convertFloat(f));
    }

    @Override
    public boolean containsDouble(K k, double d) {
        return this.contains(k, this.valueConverter.convertDouble(d));
    }

    @Override
    public boolean containsTimeMillis(K k, long l) {
        return this.contains(k, this.valueConverter.convertTimeMillis(l));
    }

    @Override
    public boolean contains(K k, V v) {
        return this.contains(k, v, HashingStrategy.JAVA_HASHER);
    }

    public final boolean contains(K k, V v, HashingStrategy<? super V> hashingStrategy) {
        ObjectUtil.checkNotNull(k, "name");
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        HeaderEntry<K, V> headerEntry = this.entries[n2];
        while (headerEntry != null) {
            if (headerEntry.hash == n && this.hashingStrategy.equals(k, headerEntry.key) && hashingStrategy.equals(v, headerEntry.value)) {
                return false;
            }
            headerEntry = headerEntry.next;
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == this.head.after;
    }

    @Override
    public Set<K> names() {
        if (this.isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(this.size());
        HeaderEntry headerEntry = this.head.after;
        while (headerEntry != this.head) {
            linkedHashSet.add(headerEntry.getKey());
            headerEntry = headerEntry.after;
        }
        return linkedHashSet;
    }

    @Override
    public T add(K k, V v) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(v, "value");
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        this.add0(n, n2, k, v);
        return this.thisT();
    }

    @Override
    public T add(K k, Iterable<? extends V> iterable) {
        this.nameValidator.validateName(k);
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        for (V v : iterable) {
            this.add0(n, n2, k, v);
        }
        return this.thisT();
    }

    @Override
    public T add(K k, V ... VArray) {
        this.nameValidator.validateName(k);
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        for (V v : VArray) {
            this.add0(n, n2, k, v);
        }
        return this.thisT();
    }

    @Override
    public T addObject(K k, Object object) {
        return this.add(k, this.valueConverter.convertObject(ObjectUtil.checkNotNull(object, "value")));
    }

    @Override
    public T addObject(K k, Iterable<?> iterable) {
        for (Object obj : iterable) {
            this.addObject(k, obj);
        }
        return this.thisT();
    }

    @Override
    public T addObject(K k, Object ... objectArray) {
        for (Object object : objectArray) {
            this.addObject(k, object);
        }
        return this.thisT();
    }

    @Override
    public T addInt(K k, int n) {
        return this.add(k, this.valueConverter.convertInt(n));
    }

    @Override
    public T addLong(K k, long l) {
        return this.add(k, this.valueConverter.convertLong(l));
    }

    @Override
    public T addDouble(K k, double d) {
        return this.add(k, this.valueConverter.convertDouble(d));
    }

    @Override
    public T addTimeMillis(K k, long l) {
        return this.add(k, this.valueConverter.convertTimeMillis(l));
    }

    @Override
    public T addChar(K k, char c) {
        return this.add(k, this.valueConverter.convertChar(c));
    }

    @Override
    public T addBoolean(K k, boolean bl) {
        return this.add(k, this.valueConverter.convertBoolean(bl));
    }

    @Override
    public T addFloat(K k, float f) {
        return this.add(k, this.valueConverter.convertFloat(f));
    }

    @Override
    public T addByte(K k, byte by) {
        return this.add(k, this.valueConverter.convertByte(by));
    }

    @Override
    public T addShort(K k, short s) {
        return this.add(k, this.valueConverter.convertShort(s));
    }

    @Override
    public T add(Headers<? extends K, ? extends V, ?> headers) {
        if (headers == this) {
            throw new IllegalArgumentException("can't add to itself.");
        }
        this.addImpl(headers);
        return this.thisT();
    }

    protected void addImpl(Headers<? extends K, ? extends V, ?> headers) {
        if (headers instanceof DefaultHeaders) {
            DefaultHeaders defaultHeaders = (DefaultHeaders)headers;
            HeaderEntry headerEntry = defaultHeaders.head.after;
            if (defaultHeaders.hashingStrategy == this.hashingStrategy && defaultHeaders.nameValidator == this.nameValidator) {
                while (headerEntry != defaultHeaders.head) {
                    this.add0(headerEntry.hash, this.index(headerEntry.hash), headerEntry.key, headerEntry.value);
                    headerEntry = headerEntry.after;
                }
            } else {
                while (headerEntry != defaultHeaders.head) {
                    this.add((K)headerEntry.key, (V)headerEntry.value);
                    headerEntry = headerEntry.after;
                }
            }
        } else {
            for (Map.Entry<K, V> entry : headers) {
                this.add(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public T set(K k, V v) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(v, "value");
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        this.remove0(n, n2, k);
        this.add0(n, n2, k, v);
        return this.thisT();
    }

    @Override
    public T set(K k, Iterable<? extends V> iterable) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(iterable, "values");
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        this.remove0(n, n2, k);
        for (V v : iterable) {
            if (v == null) break;
            this.add0(n, n2, k, v);
        }
        return this.thisT();
    }

    @Override
    public T set(K k, V ... VArray) {
        this.nameValidator.validateName(k);
        ObjectUtil.checkNotNull(VArray, "values");
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        this.remove0(n, n2, k);
        for (V v : VArray) {
            if (v == null) break;
            this.add0(n, n2, k, v);
        }
        return this.thisT();
    }

    @Override
    public T setObject(K k, Object object) {
        ObjectUtil.checkNotNull(object, "value");
        V v = ObjectUtil.checkNotNull(this.valueConverter.convertObject(object), "convertedValue");
        return this.set(k, v);
    }

    @Override
    public T setObject(K k, Iterable<?> iterable) {
        this.nameValidator.validateName(k);
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        this.remove0(n, n2, k);
        for (Object obj : iterable) {
            if (obj == null) break;
            this.add0(n, n2, k, this.valueConverter.convertObject(obj));
        }
        return this.thisT();
    }

    @Override
    public T setObject(K k, Object ... objectArray) {
        this.nameValidator.validateName(k);
        int n = this.hashingStrategy.hashCode(k);
        int n2 = this.index(n);
        this.remove0(n, n2, k);
        for (Object object : objectArray) {
            if (object == null) break;
            this.add0(n, n2, k, this.valueConverter.convertObject(object));
        }
        return this.thisT();
    }

    @Override
    public T setInt(K k, int n) {
        return this.set(k, this.valueConverter.convertInt(n));
    }

    @Override
    public T setLong(K k, long l) {
        return this.set(k, this.valueConverter.convertLong(l));
    }

    @Override
    public T setDouble(K k, double d) {
        return this.set(k, this.valueConverter.convertDouble(d));
    }

    @Override
    public T setTimeMillis(K k, long l) {
        return this.set(k, this.valueConverter.convertTimeMillis(l));
    }

    @Override
    public T setFloat(K k, float f) {
        return this.set(k, this.valueConverter.convertFloat(f));
    }

    @Override
    public T setChar(K k, char c) {
        return this.set(k, this.valueConverter.convertChar(c));
    }

    @Override
    public T setBoolean(K k, boolean bl) {
        return this.set(k, this.valueConverter.convertBoolean(bl));
    }

    @Override
    public T setByte(K k, byte by) {
        return this.set(k, this.valueConverter.convertByte(by));
    }

    @Override
    public T setShort(K k, short s) {
        return this.set(k, this.valueConverter.convertShort(s));
    }

    @Override
    public T set(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            this.clear();
            this.addImpl(headers);
        }
        return this.thisT();
    }

    @Override
    public T setAll(Headers<? extends K, ? extends V, ?> headers) {
        if (headers != this) {
            for (K k : headers.names()) {
                this.remove(k);
            }
            this.addImpl(headers);
        }
        return this.thisT();
    }

    @Override
    public boolean remove(K k) {
        return this.getAndRemove(k) != null;
    }

    @Override
    public T clear() {
        Arrays.fill(this.entries, null);
        this.head.after = this.head;
        this.head.before = this.head.after;
        this.size = 0;
        return this.thisT();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HeaderIterator(this, null);
    }

    @Override
    public Boolean getBoolean(K k) {
        V v = this.get(k);
        try {
            return v != null ? Boolean.valueOf(this.valueConverter.convertToBoolean(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public boolean getBoolean(K k, boolean bl) {
        Boolean bl2 = this.getBoolean(k);
        return bl2 != null ? bl2 : bl;
    }

    @Override
    public Byte getByte(K k) {
        V v = this.get(k);
        try {
            return v != null ? Byte.valueOf(this.valueConverter.convertToByte(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public byte getByte(K k, byte by) {
        Byte by2 = this.getByte(k);
        return by2 != null ? by2 : by;
    }

    @Override
    public Character getChar(K k) {
        V v = this.get(k);
        try {
            return v != null ? Character.valueOf(this.valueConverter.convertToChar(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public char getChar(K k, char c) {
        Character c2 = this.getChar(k);
        return c2 != null ? c2.charValue() : c;
    }

    @Override
    public Short getShort(K k) {
        V v = this.get(k);
        try {
            return v != null ? Short.valueOf(this.valueConverter.convertToShort(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public short getShort(K k, short s) {
        Short s2 = this.getShort(k);
        return s2 != null ? s2 : s;
    }

    @Override
    public Integer getInt(K k) {
        V v = this.get(k);
        try {
            return v != null ? Integer.valueOf(this.valueConverter.convertToInt(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public int getInt(K k, int n) {
        Integer n2 = this.getInt(k);
        return n2 != null ? n2 : n;
    }

    @Override
    public Long getLong(K k) {
        V v = this.get(k);
        try {
            return v != null ? Long.valueOf(this.valueConverter.convertToLong(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public long getLong(K k, long l) {
        Long l2 = this.getLong(k);
        return l2 != null ? l2 : l;
    }

    @Override
    public Float getFloat(K k) {
        V v = this.get(k);
        try {
            return v != null ? Float.valueOf(this.valueConverter.convertToFloat(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public float getFloat(K k, float f) {
        Float f2 = this.getFloat(k);
        return f2 != null ? f2.floatValue() : f;
    }

    @Override
    public Double getDouble(K k) {
        V v = this.get(k);
        try {
            return v != null ? Double.valueOf(this.valueConverter.convertToDouble(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public double getDouble(K k, double d) {
        Double d2 = this.getDouble(k);
        return d2 != null ? d2 : d;
    }

    @Override
    public Long getTimeMillis(K k) {
        V v = this.get(k);
        try {
            return v != null ? Long.valueOf(this.valueConverter.convertToTimeMillis(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public long getTimeMillis(K k, long l) {
        Long l2 = this.getTimeMillis(k);
        return l2 != null ? l2 : l;
    }

    @Override
    public Boolean getBooleanAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Boolean.valueOf(this.valueConverter.convertToBoolean(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public boolean getBooleanAndRemove(K k, boolean bl) {
        Boolean bl2 = this.getBooleanAndRemove(k);
        return bl2 != null ? bl2 : bl;
    }

    @Override
    public Byte getByteAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Byte.valueOf(this.valueConverter.convertToByte(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public byte getByteAndRemove(K k, byte by) {
        Byte by2 = this.getByteAndRemove(k);
        return by2 != null ? by2 : by;
    }

    @Override
    public Character getCharAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Character.valueOf(this.valueConverter.convertToChar(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public char getCharAndRemove(K k, char c) {
        Character c2 = this.getCharAndRemove(k);
        return c2 != null ? c2.charValue() : c;
    }

    @Override
    public Short getShortAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Short.valueOf(this.valueConverter.convertToShort(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public short getShortAndRemove(K k, short s) {
        Short s2 = this.getShortAndRemove(k);
        return s2 != null ? s2 : s;
    }

    @Override
    public Integer getIntAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Integer.valueOf(this.valueConverter.convertToInt(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public int getIntAndRemove(K k, int n) {
        Integer n2 = this.getIntAndRemove(k);
        return n2 != null ? n2 : n;
    }

    @Override
    public Long getLongAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Long.valueOf(this.valueConverter.convertToLong(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public long getLongAndRemove(K k, long l) {
        Long l2 = this.getLongAndRemove(k);
        return l2 != null ? l2 : l;
    }

    @Override
    public Float getFloatAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Float.valueOf(this.valueConverter.convertToFloat(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public float getFloatAndRemove(K k, float f) {
        Float f2 = this.getFloatAndRemove(k);
        return f2 != null ? f2.floatValue() : f;
    }

    @Override
    public Double getDoubleAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Double.valueOf(this.valueConverter.convertToDouble(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public double getDoubleAndRemove(K k, double d) {
        Double d2 = this.getDoubleAndRemove(k);
        return d2 != null ? d2 : d;
    }

    @Override
    public Long getTimeMillisAndRemove(K k) {
        V v = this.getAndRemove(k);
        try {
            return v != null ? Long.valueOf(this.valueConverter.convertToTimeMillis(v)) : null;
        } catch (RuntimeException runtimeException) {
            return null;
        }
    }

    @Override
    public long getTimeMillisAndRemove(K k, long l) {
        Long l2 = this.getTimeMillisAndRemove(k);
        return l2 != null ? l2 : l;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Headers)) {
            return true;
        }
        return this.equals((Headers)object, HashingStrategy.JAVA_HASHER);
    }

    public int hashCode() {
        return this.hashCode(HashingStrategy.JAVA_HASHER);
    }

    public final boolean equals(Headers<K, V, ?> headers, HashingStrategy<V> hashingStrategy) {
        if (headers.size() != this.size()) {
            return true;
        }
        if (this == headers) {
            return false;
        }
        for (K k : this.names()) {
            List<V> list = headers.getAll(k);
            List<V> list2 = this.getAll(k);
            if (list.size() != list2.size()) {
                return true;
            }
            for (int i = 0; i < list.size(); ++i) {
                if (hashingStrategy.equals(list.get(i), list2.get(i))) continue;
                return true;
            }
        }
        return false;
    }

    public final int hashCode(HashingStrategy<V> hashingStrategy) {
        int n = -1028477387;
        for (K k : this.names()) {
            n = 31 * n + this.hashingStrategy.hashCode(k);
            List<V> list = this.getAll(k);
            for (int i = 0; i < list.size(); ++i) {
                n = 31 * n + hashingStrategy.hashCode(list.get(i));
            }
        }
        return n;
    }

    public String toString() {
        return HeadersUtils.toString(this.getClass(), this.iterator(), this.size());
    }

    protected HeaderEntry<K, V> newHeaderEntry(int n, K k, V v, HeaderEntry<K, V> headerEntry) {
        return new HeaderEntry<K, V>(n, k, v, headerEntry, this.head);
    }

    protected ValueConverter<V> valueConverter() {
        return this.valueConverter;
    }

    private int index(int n) {
        return n & this.hashMask;
    }

    private void add0(int n, int n2, K k, V v) {
        this.entries[n2] = this.newHeaderEntry(n, k, v, this.entries[n2]);
        ++this.size;
    }

    private V remove0(int n, int n2, K k) {
        HeaderEntry<K, V> headerEntry = this.entries[n2];
        if (headerEntry == null) {
            return null;
        }
        V v = null;
        HeaderEntry headerEntry2 = headerEntry.next;
        while (headerEntry2 != null) {
            if (headerEntry2.hash == n && this.hashingStrategy.equals(k, headerEntry2.key)) {
                v = headerEntry2.value;
                headerEntry.next = headerEntry2.next;
                headerEntry2.remove();
                --this.size;
            } else {
                headerEntry = headerEntry2;
            }
            headerEntry2 = headerEntry.next;
        }
        headerEntry = this.entries[n2];
        if (headerEntry.hash == n && this.hashingStrategy.equals(k, headerEntry.key)) {
            if (v == null) {
                v = headerEntry.value;
            }
            this.entries[n2] = headerEntry.next;
            headerEntry.remove();
            --this.size;
        }
        return v;
    }

    private T thisT() {
        return (T)this;
    }

    public DefaultHeaders<K, V, T> copy() {
        DefaultHeaders<K, V, T> defaultHeaders = new DefaultHeaders<K, V, T>(this.hashingStrategy, this.valueConverter, this.nameValidator, this.entries.length);
        defaultHeaders.addImpl(this);
        return defaultHeaders;
    }

    static HashingStrategy access$100(DefaultHeaders defaultHeaders) {
        return defaultHeaders.hashingStrategy;
    }

    static HeaderEntry[] access$200(DefaultHeaders defaultHeaders) {
        return defaultHeaders.entries;
    }

    static int access$300(DefaultHeaders defaultHeaders, int n) {
        return defaultHeaders.index(n);
    }

    protected static class HeaderEntry<K, V>
    implements Map.Entry<K, V> {
        protected final int hash;
        protected final K key;
        protected V value;
        protected HeaderEntry<K, V> next;
        protected HeaderEntry<K, V> before;
        protected HeaderEntry<K, V> after;

        protected HeaderEntry(int n, K k) {
            this.hash = n;
            this.key = k;
        }

        HeaderEntry(int n, K k, V v, HeaderEntry<K, V> headerEntry, HeaderEntry<K, V> headerEntry2) {
            this.hash = n;
            this.key = k;
            this.value = v;
            this.next = headerEntry;
            this.after = headerEntry2;
            this.before = headerEntry2.before;
            this.pointNeighborsToThis();
        }

        HeaderEntry() {
            this.hash = -1;
            this.key = null;
            this.before = this.after = this;
        }

        protected final void pointNeighborsToThis() {
            this.before.after = this;
            this.after.before = this;
        }

        public final HeaderEntry<K, V> before() {
            return this.before;
        }

        public final HeaderEntry<K, V> after() {
            return this.after;
        }

        protected void remove() {
            this.before.after = this.after;
            this.after.before = this.before;
        }

        @Override
        public final K getKey() {
            return this.key;
        }

        @Override
        public final V getValue() {
            return this.value;
        }

        @Override
        public final V setValue(V v) {
            ObjectUtil.checkNotNull(v, "value");
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public final String toString() {
            return this.key.toString() + '=' + this.value.toString();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return (this.getKey() == null ? entry.getKey() == null : this.getKey().equals(entry.getKey())) && (this.getValue() == null ? entry.getValue() == null : this.getValue().equals(entry.getValue()));
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
        }
    }

    private final class ValueIterator
    implements Iterator<V> {
        private final K name;
        private final int hash;
        private HeaderEntry<K, V> next;
        final DefaultHeaders this$0;

        ValueIterator(DefaultHeaders defaultHeaders, K k) {
            this.this$0 = defaultHeaders;
            this.name = ObjectUtil.checkNotNull(k, "name");
            this.hash = DefaultHeaders.access$100(defaultHeaders).hashCode(k);
            this.calculateNext(DefaultHeaders.access$200(defaultHeaders)[DefaultHeaders.access$300(defaultHeaders, this.hash)]);
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public V next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            HeaderEntry headerEntry = this.next;
            this.calculateNext(this.next.next);
            return headerEntry.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        private void calculateNext(HeaderEntry<K, V> headerEntry) {
            while (headerEntry != null) {
                if (headerEntry.hash == this.hash && DefaultHeaders.access$100(this.this$0).equals(this.name, headerEntry.key)) {
                    this.next = headerEntry;
                    return;
                }
                headerEntry = headerEntry.next;
            }
            this.next = null;
        }
    }

    private final class HeaderIterator
    implements Iterator<Map.Entry<K, V>> {
        private HeaderEntry<K, V> current;
        final DefaultHeaders this$0;

        private HeaderIterator(DefaultHeaders defaultHeaders) {
            this.this$0 = defaultHeaders;
            this.current = this.this$0.head;
        }

        @Override
        public boolean hasNext() {
            return this.current.after != this.this$0.head;
        }

        @Override
        public Map.Entry<K, V> next() {
            this.current = this.current.after;
            if (this.current == this.this$0.head) {
                throw new NoSuchElementException();
            }
            return this.current;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public Object next() {
            return this.next();
        }

        HeaderIterator(DefaultHeaders defaultHeaders, 1 var2_2) {
            this(defaultHeaders);
        }
    }

    public static interface NameValidator<K> {
        public static final NameValidator NOT_NULL = new NameValidator(){

            public void validateName(Object object) {
                ObjectUtil.checkNotNull(object, "name");
            }
        };

        public void validateName(K var1);
    }
}

