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
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;

public class ChunkLightRemoveFix
extends DataFix {
    public ChunkLightRemoveFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = type.findFieldType("Level");
        OpticFinder<?> opticFinder = DSL.fieldFinder("Level", type2);
        return this.fixTypeEverywhereTyped("ChunkLightRemoveFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), arg_0 -> ChunkLightRemoveFix.lambda$makeRule$2(opticFinder, arg_0));
    }

    private static Typed lambda$makeRule$2(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, ChunkLightRemoveFix::lambda$makeRule$1);
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), ChunkLightRemoveFix::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        return dynamic.remove("isLightOn");
    }
}

