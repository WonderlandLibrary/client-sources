/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class MemoryExpiry
extends NamedEntityFix {
    public MemoryExpiry(Schema schema, String string) {
        super(schema, false, "Memory expiry data fix (" + string + ")", TypeReferences.ENTITY, string);
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::func_233326_a_);
    }

    public Dynamic<?> func_233326_a_(Dynamic<?> dynamic) {
        return dynamic.update("Brain", this::func_233327_b_);
    }

    private Dynamic<?> func_233327_b_(Dynamic<?> dynamic) {
        return dynamic.update("memories", this::func_233328_c_);
    }

    private Dynamic<?> func_233328_c_(Dynamic<?> dynamic) {
        return dynamic.updateMapValues(this::func_233325_a_);
    }

    private Pair<Dynamic<?>, Dynamic<?>> func_233325_a_(Pair<Dynamic<?>, Dynamic<?>> pair) {
        return pair.mapSecond(this::func_233329_d_);
    }

    private Dynamic<?> func_233329_d_(Dynamic<?> dynamic) {
        return dynamic.createMap(ImmutableMap.of(dynamic.createString("value"), dynamic));
    }
}

