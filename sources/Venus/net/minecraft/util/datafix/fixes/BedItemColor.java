/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class BedItemColor
extends DataFix {
    public BedItemColor(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(TypeReferences.ITEM_STACK), arg_0 -> BedItemColor.lambda$makeRule$0(opticFinder, arg_0));
    }

    private static Typed lambda$makeRule$0(OpticFinder opticFinder, Typed typed) {
        Dynamic dynamic;
        Optional optional = typed.getOptional(opticFinder);
        if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:bed") && (dynamic = typed.get(DSL.remainderFinder())).get("Damage").asInt(0) == 0) {
            return typed.set(DSL.remainderFinder(), dynamic.set("Damage", dynamic.createShort((short)14)));
        }
        return typed;
    }
}

