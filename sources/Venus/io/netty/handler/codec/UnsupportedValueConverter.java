/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.handler.codec.ValueConverter;

public final class UnsupportedValueConverter<V>
implements ValueConverter<V> {
    private static final UnsupportedValueConverter INSTANCE = new UnsupportedValueConverter();

    private UnsupportedValueConverter() {
    }

    public static <V> UnsupportedValueConverter<V> instance() {
        return INSTANCE;
    }

    @Override
    public V convertObject(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertBoolean(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean convertToBoolean(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertByte(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte convertToByte(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertChar(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char convertToChar(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertShort(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short convertToShort(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertInt(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int convertToInt(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertLong(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long convertToLong(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertTimeMillis(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long convertToTimeMillis(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertFloat(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float convertToFloat(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V convertDouble(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double convertToDouble(V v) {
        throw new UnsupportedOperationException();
    }
}

