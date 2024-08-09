/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class DelegatingDynamicOps<T>
implements DynamicOps<T> {
    protected final DynamicOps<T> ops;

    protected DelegatingDynamicOps(DynamicOps<T> dynamicOps) {
        this.ops = dynamicOps;
    }

    @Override
    public T empty() {
        return this.ops.empty();
    }

    @Override
    public <U> U convertTo(DynamicOps<U> dynamicOps, T t) {
        return this.ops.convertTo(dynamicOps, t);
    }

    @Override
    public DataResult<Number> getNumberValue(T t) {
        return this.ops.getNumberValue(t);
    }

    @Override
    public T createNumeric(Number number) {
        return this.ops.createNumeric(number);
    }

    @Override
    public T createByte(byte by) {
        return this.ops.createByte(by);
    }

    @Override
    public T createShort(short s) {
        return this.ops.createShort(s);
    }

    @Override
    public T createInt(int n) {
        return this.ops.createInt(n);
    }

    @Override
    public T createLong(long l) {
        return this.ops.createLong(l);
    }

    @Override
    public T createFloat(float f) {
        return this.ops.createFloat(f);
    }

    @Override
    public T createDouble(double d) {
        return this.ops.createDouble(d);
    }

    @Override
    public DataResult<Boolean> getBooleanValue(T t) {
        return this.ops.getBooleanValue(t);
    }

    @Override
    public T createBoolean(boolean bl) {
        return this.ops.createBoolean(bl);
    }

    @Override
    public DataResult<String> getStringValue(T t) {
        return this.ops.getStringValue(t);
    }

    @Override
    public T createString(String string) {
        return this.ops.createString(string);
    }

    @Override
    public DataResult<T> mergeToList(T t, T t2) {
        return this.ops.mergeToList(t, t2);
    }

    @Override
    public DataResult<T> mergeToList(T t, List<T> list) {
        return this.ops.mergeToList(t, list);
    }

    @Override
    public DataResult<T> mergeToMap(T t, T t2, T t3) {
        return this.ops.mergeToMap(t, t2, t3);
    }

    @Override
    public DataResult<T> mergeToMap(T t, MapLike<T> mapLike) {
        return this.ops.mergeToMap(t, mapLike);
    }

    @Override
    public DataResult<Stream<Pair<T, T>>> getMapValues(T t) {
        return this.ops.getMapValues(t);
    }

    @Override
    public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T t) {
        return this.ops.getMapEntries(t);
    }

    @Override
    public T createMap(Stream<Pair<T, T>> stream) {
        return this.ops.createMap(stream);
    }

    @Override
    public DataResult<MapLike<T>> getMap(T t) {
        return this.ops.getMap(t);
    }

    @Override
    public DataResult<Stream<T>> getStream(T t) {
        return this.ops.getStream(t);
    }

    @Override
    public DataResult<Consumer<Consumer<T>>> getList(T t) {
        return this.ops.getList(t);
    }

    @Override
    public T createList(Stream<T> stream) {
        return this.ops.createList(stream);
    }

    @Override
    public DataResult<ByteBuffer> getByteBuffer(T t) {
        return this.ops.getByteBuffer(t);
    }

    @Override
    public T createByteList(ByteBuffer byteBuffer) {
        return this.ops.createByteList(byteBuffer);
    }

    @Override
    public DataResult<IntStream> getIntStream(T t) {
        return this.ops.getIntStream(t);
    }

    @Override
    public T createIntList(IntStream intStream) {
        return this.ops.createIntList(intStream);
    }

    @Override
    public DataResult<LongStream> getLongStream(T t) {
        return this.ops.getLongStream(t);
    }

    @Override
    public T createLongList(LongStream longStream) {
        return this.ops.createLongList(longStream);
    }

    @Override
    public T remove(T t, String string) {
        return this.ops.remove(t, string);
    }

    @Override
    public boolean compressMaps() {
        return this.ops.compressMaps();
    }

    @Override
    public ListBuilder<T> listBuilder() {
        return this.ops.listBuilder();
    }

    @Override
    public RecordBuilder<T> mapBuilder() {
        return this.ops.mapBuilder();
    }
}

