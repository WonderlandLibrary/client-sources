/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class PointOfInterestRebuild
extends DataFix {
    public PointOfInterestRebuild(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<Pair<String, Dynamic<?>>> type = DSL.named(TypeReferences.POI_CHUNK.typeName(), DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(TypeReferences.POI_CHUNK))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return this.fixTypeEverywhere("POI rebuild", type, PointOfInterestRebuild::lambda$makeRule$1);
    }

    private static <T> Dynamic<T> func_226195_a_(Dynamic<T> dynamic) {
        return dynamic.update("Sections", PointOfInterestRebuild::lambda$func_226195_a_$4);
    }

    private static Dynamic lambda$func_226195_a_$4(Dynamic dynamic) {
        return dynamic.updateMapValues(PointOfInterestRebuild::lambda$func_226195_a_$3);
    }

    private static Pair lambda$func_226195_a_$3(Pair pair) {
        return pair.mapSecond(PointOfInterestRebuild::lambda$func_226195_a_$2);
    }

    private static Dynamic lambda$func_226195_a_$2(Dynamic dynamic) {
        return dynamic.remove("Valid");
    }

    private static Function lambda$makeRule$1(DynamicOps dynamicOps) {
        return PointOfInterestRebuild::lambda$makeRule$0;
    }

    private static Pair lambda$makeRule$0(Pair pair) {
        return pair.mapSecond(PointOfInterestRebuild::func_226195_a_);
    }
}

