/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;

public class BlockEntityUUID
extends AbstractUUIDFix {
    public BlockEntityUUID(Schema schema) {
        super(schema, TypeReferences.BLOCK_ENTITY);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.reference), this::lambda$makeRule$0);
    }

    private Dynamic<?> func_233115_b_(Dynamic<?> dynamic) {
        return dynamic.get("Owner").get().map(BlockEntityUUID::lambda$func_233115_b_$1).map(arg_0 -> BlockEntityUUID.lambda$func_233115_b_$2(dynamic, arg_0)).result().orElse(dynamic);
    }

    private Dynamic<?> func_233116_c_(Dynamic<?> dynamic) {
        return BlockEntityUUID.func_233062_b_(dynamic, "target_uuid", "Target").orElse(dynamic);
    }

    private static Dynamic lambda$func_233115_b_$2(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.remove("Owner").set("SkullOwner", dynamic2);
    }

    private static Dynamic lambda$func_233115_b_$1(Dynamic dynamic) {
        return BlockEntityUUID.func_233058_a_(dynamic, "Id", "Id").orElse(dynamic);
    }

    private Typed lambda$makeRule$0(Typed typed) {
        typed = this.func_233053_a_(typed, "minecraft:conduit", this::func_233116_c_);
        return this.func_233053_a_(typed, "minecraft:skull", this::func_233115_b_);
    }
}

