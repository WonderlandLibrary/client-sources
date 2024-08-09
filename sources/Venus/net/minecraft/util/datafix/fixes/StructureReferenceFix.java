/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;

public class StructureReferenceFix
extends DataFix {
    public StructureReferenceFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
        return this.fixTypeEverywhereTyped("Structure Reference Fix", type, StructureReferenceFix::lambda$makeRule$0);
    }

    private static <T> Dynamic<T> func_226212_a_(Dynamic<T> dynamic) {
        return dynamic.update("references", StructureReferenceFix::lambda$func_226212_a_$2);
    }

    private static Dynamic lambda$func_226212_a_$2(Dynamic dynamic) {
        return dynamic.createInt(dynamic.asNumber().map(Number::intValue).result().filter(StructureReferenceFix::lambda$func_226212_a_$1).orElse(1));
    }

    private static boolean lambda$func_226212_a_$1(Integer n) {
        return n > 0;
    }

    private static Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), StructureReferenceFix::func_226212_a_);
    }
}

