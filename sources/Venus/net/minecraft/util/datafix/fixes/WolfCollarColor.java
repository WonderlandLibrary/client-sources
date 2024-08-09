/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class WolfCollarColor
extends NamedEntityFix {
    public WolfCollarColor(Schema schema, boolean bl) {
        super(schema, bl, "EntityWolfColorFix", TypeReferences.ENTITY, "minecraft:wolf");
    }

    public Dynamic<?> fixTag(Dynamic<?> dynamic) {
        return dynamic.update("CollarColor", WolfCollarColor::lambda$fixTag$0);
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }

    private static Dynamic lambda$fixTag$0(Dynamic dynamic) {
        return dynamic.createByte((byte)(15 - dynamic.asInt(0)));
    }
}

