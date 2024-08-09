/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.constant;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;

public final class EmptyPart
extends Type<Unit> {
    public String toString() {
        return "EmptyPart";
    }

    @Override
    public Optional<Unit> point(DynamicOps<?> dynamicOps) {
        return Optional.of(Unit.INSTANCE);
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
    protected Codec<Unit> buildCodec() {
        return Codec.EMPTY.codec();
    }
}

