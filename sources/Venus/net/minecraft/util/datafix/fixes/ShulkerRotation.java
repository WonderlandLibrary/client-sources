/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class ShulkerRotation
extends NamedEntityFix {
    public ShulkerRotation(Schema schema) {
        super(schema, false, "EntityShulkerRotationFix", TypeReferences.ENTITY, "minecraft:shulker");
    }

    public Dynamic<?> func_233201_a_(Dynamic<?> dynamic) {
        List<Double> list = dynamic.get("Rotation").asList(ShulkerRotation::lambda$func_233201_a_$0);
        if (!list.isEmpty()) {
            list.set(0, list.get(0) - 180.0);
            return dynamic.set("Rotation", dynamic.createList(list.stream().map(dynamic::createDouble)));
        }
        return dynamic;
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::func_233201_a_);
    }

    private static Double lambda$func_233201_a_$0(Dynamic dynamic) {
        return dynamic.asDouble(180.0);
    }
}

