/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.EntityUUID;

public class PlayerUUID
extends AbstractUUIDFix {
    public PlayerUUID(Schema schema) {
        super(schema, TypeReferences.PLAYER);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("PlayerUUIDFix", this.getInputSchema().getType(this.reference), PlayerUUID::lambda$makeRule$3);
    }

    private static Typed lambda$makeRule$3(Typed typed) {
        OpticFinder<?> opticFinder = typed.getType().findField("RootVehicle");
        return typed.updateTyped(opticFinder, opticFinder.type(), PlayerUUID::lambda$makeRule$1).update(DSL.remainderFinder(), PlayerUUID::lambda$makeRule$2);
    }

    private static Dynamic lambda$makeRule$2(Dynamic dynamic) {
        return EntityUUID.func_233214_c_(EntityUUID.func_233212_b_(dynamic));
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), PlayerUUID::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        return PlayerUUID.func_233064_c_(dynamic, "Attach", "Attach").orElse(dynamic);
    }
}

