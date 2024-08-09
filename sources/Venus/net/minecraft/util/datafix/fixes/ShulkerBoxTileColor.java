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

public class ShulkerBoxTileColor
extends NamedEntityFix {
    public ShulkerBoxTileColor(Schema schema, boolean bl) {
        super(schema, bl, "BlockEntityShulkerBoxColorFix", TypeReferences.BLOCK_ENTITY, "minecraft:shulker_box");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), ShulkerBoxTileColor::lambda$fix$0);
    }

    private static Dynamic lambda$fix$0(Dynamic dynamic) {
        return dynamic.remove("Color");
    }
}

