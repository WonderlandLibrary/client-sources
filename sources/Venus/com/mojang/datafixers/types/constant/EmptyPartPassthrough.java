/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.constant;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;

public final class EmptyPartPassthrough
extends Type<Dynamic<?>> {
    public String toString() {
        return "EmptyPartPassthrough";
    }

    @Override
    public Optional<Dynamic<?>> point(DynamicOps<?> dynamicOps) {
        return Optional.of(new Dynamic(dynamicOps));
    }

    @Override
    public boolean equals(Object object, boolean bl, boolean bl2) {
        return this == object;
    }

    @Override
    public TypeTemplate buildTemplate() {
        return DSL.constType(this);
    }

    @Override
    public Codec<Dynamic<?>> buildCodec() {
        return Codec.PASSTHROUGH;
    }
}

