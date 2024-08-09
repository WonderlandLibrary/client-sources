/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;

public class LevelUUID
extends AbstractUUIDFix {
    public LevelUUID(Schema schema) {
        super(schema, TypeReferences.LEVEL);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("LevelUUIDFix", this.getInputSchema().getType(this.reference), this::lambda$makeRule$2);
    }

    private Dynamic<?> func_233313_b_(Dynamic<?> dynamic) {
        return LevelUUID.func_233058_a_(dynamic, "WanderingTraderId", "WanderingTraderId").orElse(dynamic);
    }

    private Dynamic<?> func_233314_c_(Dynamic<?> dynamic) {
        return dynamic.update("DimensionData", LevelUUID::lambda$func_233314_c_$6);
    }

    private Dynamic<?> func_233315_d_(Dynamic<?> dynamic) {
        return dynamic.update("CustomBossEvents", LevelUUID::lambda$func_233315_d_$12);
    }

    private static Dynamic lambda$func_233315_d_$12(Dynamic dynamic) {
        return dynamic.updateMapValues(LevelUUID::lambda$func_233315_d_$11);
    }

    private static Pair lambda$func_233315_d_$11(Pair pair) {
        return pair.mapSecond(LevelUUID::lambda$func_233315_d_$10);
    }

    private static Dynamic lambda$func_233315_d_$10(Dynamic dynamic) {
        return dynamic.update("Players", arg_0 -> LevelUUID.lambda$func_233315_d_$9(dynamic, arg_0));
    }

    private static Dynamic lambda$func_233315_d_$9(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().map(LevelUUID::lambda$func_233315_d_$8));
    }

    private static Dynamic lambda$func_233315_d_$8(Dynamic dynamic) {
        return LevelUUID.func_233054_a_(dynamic).orElseGet(() -> LevelUUID.lambda$func_233315_d_$7(dynamic));
    }

    private static Dynamic lambda$func_233315_d_$7(Dynamic dynamic) {
        LOGGER.warn("CustomBossEvents contains invalid UUIDs.");
        return dynamic;
    }

    private static Dynamic lambda$func_233314_c_$6(Dynamic dynamic) {
        return dynamic.updateMapValues(LevelUUID::lambda$func_233314_c_$5);
    }

    private static Pair lambda$func_233314_c_$5(Pair pair) {
        return pair.mapSecond(LevelUUID::lambda$func_233314_c_$4);
    }

    private static Dynamic lambda$func_233314_c_$4(Dynamic dynamic) {
        return dynamic.update("DragonFight", LevelUUID::lambda$func_233314_c_$3);
    }

    private static Dynamic lambda$func_233314_c_$3(Dynamic dynamic) {
        return LevelUUID.func_233064_c_(dynamic, "DragonUUID", "Dragon").orElse(dynamic);
    }

    private Typed lambda$makeRule$2(Typed typed) {
        return typed.updateTyped(DSL.remainderFinder(), this::lambda$makeRule$1);
    }

    private Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$0);
    }

    private Dynamic lambda$makeRule$0(Dynamic dynamic) {
        dynamic = this.func_233315_d_(dynamic);
        dynamic = this.func_233314_c_(dynamic);
        return this.func_233313_b_(dynamic);
    }
}

