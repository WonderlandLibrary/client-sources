/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

public interface PrimitiveCodec<A>
extends Codec<A> {
    public <T> DataResult<A> read(DynamicOps<T> var1, T var2);

    public <T> T write(DynamicOps<T> var1, A var2);

    @Override
    default public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T t) {
        return this.read(dynamicOps, t).map(arg_0 -> PrimitiveCodec.lambda$decode$0(dynamicOps, arg_0));
    }

    @Override
    default public <T> DataResult<T> encode(A a, DynamicOps<T> dynamicOps, T t) {
        return dynamicOps.mergeToPrimitive(t, this.write(dynamicOps, a));
    }

    private static Pair lambda$decode$0(DynamicOps dynamicOps, Object object) {
        return Pair.of(object, dynamicOps.empty());
    }
}

