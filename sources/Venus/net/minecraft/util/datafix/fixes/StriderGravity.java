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

public class StriderGravity
extends NamedEntityFix {
    public StriderGravity(Schema schema, boolean bl) {
        super(schema, bl, "StriderGravityFix", TypeReferences.ENTITY, "minecraft:strider");
    }

    public Dynamic<?> func_233403_a_(Dynamic<?> dynamic) {
        return dynamic.get("NoGravity").asBoolean(true) ? dynamic.set("NoGravity", dynamic.createBoolean(true)) : dynamic;
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::func_233403_a_);
    }
}

