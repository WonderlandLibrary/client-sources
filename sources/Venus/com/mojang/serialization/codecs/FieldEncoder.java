/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization.codecs;

import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapEncoder;
import com.mojang.serialization.RecordBuilder;
import java.util.Objects;
import java.util.stream.Stream;

public class FieldEncoder<A>
extends MapEncoder.Implementation<A> {
    private final String name;
    private final Encoder<A> elementCodec;

    public FieldEncoder(String string, Encoder<A> encoder) {
        this.name = string;
        this.elementCodec = encoder;
    }

    @Override
    public <T> RecordBuilder<T> encode(A a, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        return recordBuilder.add(this.name, this.elementCodec.encodeStart(dynamicOps, a));
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return Stream.of(dynamicOps.createString(this.name));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        FieldEncoder fieldEncoder = (FieldEncoder)object;
        return Objects.equals(this.name, fieldEncoder.name) && Objects.equals(this.elementCodec, fieldEncoder.elementCodec);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.elementCodec);
    }

    public String toString() {
        return "FieldEncoder[" + this.name + ": " + this.elementCodec + ']';
    }
}

