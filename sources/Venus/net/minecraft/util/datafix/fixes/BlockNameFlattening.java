/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;

public class BlockNameFlattening
extends DataFix {
    public BlockNameFlattening(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.BLOCK_NAME);
        Type<?> type2 = this.getOutputSchema().getType(TypeReferences.BLOCK_NAME);
        Type<Pair<String, Either<Integer, String>>> type3 = DSL.named(TypeReferences.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.func_233457_a_()));
        Type<Pair<String, String>> type4 = DSL.named(TypeReferences.BLOCK_NAME.typeName(), NamespacedSchema.func_233457_a_());
        if (Objects.equals(type, type3) && Objects.equals(type2, type4)) {
            return this.fixTypeEverywhere("BlockNameFlatteningFix", type3, type4, BlockNameFlattening::lambda$makeRule$3);
        }
        throw new IllegalStateException("Expected and actual types don't match.");
    }

    private static Function lambda$makeRule$3(DynamicOps dynamicOps) {
        return BlockNameFlattening::lambda$makeRule$2;
    }

    private static Pair lambda$makeRule$2(Pair pair) {
        return pair.mapSecond(BlockNameFlattening::lambda$makeRule$1);
    }

    private static String lambda$makeRule$1(Either either) {
        return either.map(BlockStateFlatteningMap::updateId, BlockNameFlattening::lambda$makeRule$0);
    }

    private static String lambda$makeRule$0(String string) {
        return BlockStateFlatteningMap.updateName(NamespacedSchema.ensureNamespaced(string));
    }
}

