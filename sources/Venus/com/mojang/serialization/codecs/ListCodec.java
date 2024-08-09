/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableObject;

public final class ListCodec<A>
implements Codec<List<A>> {
    private final Codec<A> elementCodec;

    public ListCodec(Codec<A> codec) {
        this.elementCodec = codec;
    }

    @Override
    public <T> DataResult<T> encode(List<A> list, DynamicOps<T> dynamicOps, T t) {
        ListBuilder<T> listBuilder = dynamicOps.listBuilder();
        for (A a : list) {
            listBuilder.add(this.elementCodec.encodeStart(dynamicOps, a));
        }
        return listBuilder.build(t);
    }

    @Override
    public <T> DataResult<Pair<List<A>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        return dynamicOps.getList(t).setLifecycle(Lifecycle.stable()).flatMap(arg_0 -> this.lambda$decode$4(dynamicOps, arg_0));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ListCodec listCodec = (ListCodec)object;
        return Objects.equals(this.elementCodec, listCodec.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.elementCodec);
    }

    public String toString() {
        return "ListCodec[" + this.elementCodec + ']';
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((List)object, dynamicOps, object2);
    }

    private DataResult lambda$decode$4(DynamicOps dynamicOps, Consumer consumer) {
        ImmutableList.Builder builder = ImmutableList.builder();
        Stream.Builder builder2 = Stream.builder();
        MutableObject<DataResult<Unit>> mutableObject = new MutableObject<DataResult<Unit>>(DataResult.success(Unit.INSTANCE, Lifecycle.stable()));
        consumer.accept(arg_0 -> this.lambda$null$2(dynamicOps, builder2, mutableObject, builder, arg_0));
        ImmutableCollection immutableCollection = builder.build();
        Object t = dynamicOps.createList(builder2.build());
        Pair pair = Pair.of(immutableCollection, t);
        return mutableObject.getValue().map(arg_0 -> ListCodec.lambda$null$3(pair, arg_0)).setPartial(pair);
    }

    private static Pair lambda$null$3(Pair pair, Unit unit) {
        return pair;
    }

    private void lambda$null$2(DynamicOps dynamicOps, Stream.Builder builder, MutableObject mutableObject, ImmutableList.Builder builder2, Object object) {
        DataResult dataResult = this.elementCodec.decode(dynamicOps, object);
        dataResult.error().ifPresent(arg_0 -> ListCodec.lambda$null$0(builder, object, arg_0));
        mutableObject.setValue(((DataResult)mutableObject.getValue()).apply2stable((arg_0, arg_1) -> ListCodec.lambda$null$1(builder2, arg_0, arg_1), dataResult));
    }

    private static Unit lambda$null$1(ImmutableList.Builder builder, Unit unit, Pair pair) {
        builder.add(pair.getFirst());
        return unit;
    }

    private static void lambda$null$0(Stream.Builder builder, Object object, DataResult.PartialResult partialResult) {
        builder.add(object);
    }
}

