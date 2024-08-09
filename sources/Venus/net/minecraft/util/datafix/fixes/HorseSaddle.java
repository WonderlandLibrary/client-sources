/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class HorseSaddle
extends NamedEntityFix {
    public HorseSaddle(Schema schema, boolean bl) {
        super(schema, bl, "EntityHorseSaddleFix", TypeReferences.ENTITY, "EntityHorse");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        Type<?> type = this.getInputSchema().getTypeRaw(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder2 = DSL.fieldFinder("SaddleItem", type);
        Optional<Typed<?>> optional = typed.getOptionalTyped(opticFinder2);
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        if (!optional.isPresent() && dynamic.get("Saddle").asBoolean(true)) {
            Typed<?> typed2 = type.pointTyped(typed.getOps()).orElseThrow(IllegalStateException::new);
            typed2 = typed2.set(opticFinder, Pair.of(TypeReferences.ITEM_NAME.typeName(), "minecraft:saddle"));
            Dynamic dynamic2 = dynamic.emptyMap();
            dynamic2 = dynamic2.set("Count", dynamic2.createByte((byte)1));
            dynamic2 = dynamic2.set("Damage", dynamic2.createShort((short)0));
            typed2 = typed2.set(DSL.remainderFinder(), dynamic2);
            dynamic.remove("Saddle");
            return typed.set(opticFinder2, typed2).set(DSL.remainderFinder(), dynamic);
        }
        return typed;
    }
}

