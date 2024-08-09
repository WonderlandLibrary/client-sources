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

public class ColorlessShulkerEntityFix
extends NamedEntityFix {
    public ColorlessShulkerEntityFix(Schema schema, boolean bl) {
        super(schema, bl, "Colorless shulker entity fix", TypeReferences.ENTITY, "minecraft:shulker");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), ColorlessShulkerEntityFix::lambda$fix$0);
    }

    private static Dynamic lambda$fix$0(Dynamic dynamic) {
        return dynamic.get("Color").asInt(0) == 10 ? dynamic.set("Color", dynamic.createByte((byte)16)) : dynamic;
    }
}

