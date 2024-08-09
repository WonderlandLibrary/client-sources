/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class OminousBannerTileEntityRenameFix
extends NamedEntityFix {
    public OminousBannerTileEntityRenameFix(Schema schema, boolean bl) {
        super(schema, bl, "OminousBannerBlockEntityRenameFix", TypeReferences.BLOCK_ENTITY, "minecraft:banner");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::func_222992_a);
    }

    private Dynamic<?> func_222992_a(Dynamic<?> dynamic) {
        Optional<String> optional = dynamic.get("CustomName").asString().result();
        if (optional.isPresent()) {
            String string = optional.get();
            string = string.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
            return dynamic.set("CustomName", dynamic.createString(string));
        }
        return dynamic;
    }
}

