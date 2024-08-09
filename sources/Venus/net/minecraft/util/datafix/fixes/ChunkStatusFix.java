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
import java.util.Objects;
import net.minecraft.util.datafix.TypeReferences;

public class ChunkStatusFix
extends DataFix {
    public ChunkStatusFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = type.findFieldType("Level");
        OpticFinder<?> opticFinder = DSL.fieldFinder("Level", type2);
        return this.fixTypeEverywhereTyped("ChunkStatusFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), arg_0 -> ChunkStatusFix.lambda$makeRule$1(opticFinder, arg_0));
    }

    private static Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, ChunkStatusFix::lambda$makeRule$0);
    }

    private static Typed lambda$makeRule$0(Typed typed) {
        Dynamic dynamic = typed.get(DSL.remainderFinder());
        String string = dynamic.get("Status").asString("empty");
        if (Objects.equals(string, "postprocessed")) {
            dynamic = dynamic.set("Status", dynamic.createString("fullchunk"));
        }
        return typed.set(DSL.remainderFinder(), dynamic);
    }
}

