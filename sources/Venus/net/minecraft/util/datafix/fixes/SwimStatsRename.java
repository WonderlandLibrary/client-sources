/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class SwimStatsRename
extends DataFix {
    public SwimStatsRename(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getOutputSchema().getType(TypeReferences.STATS);
        Type<?> type2 = this.getInputSchema().getType(TypeReferences.STATS);
        OpticFinder<?> opticFinder = type2.findField("stats");
        OpticFinder<?> opticFinder2 = opticFinder.type().findField("minecraft:custom");
        OpticFinder<String> opticFinder3 = NamespacedSchema.func_233457_a_().finder();
        return this.fixTypeEverywhereTyped("SwimStatsRenameFix", type2, type, arg_0 -> SwimStatsRename.lambda$makeRule$3(opticFinder, opticFinder2, opticFinder3, arg_0));
    }

    private static Typed lambda$makeRule$3(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> SwimStatsRename.lambda$makeRule$2(opticFinder2, opticFinder3, arg_0));
    }

    private static Typed lambda$makeRule$2(OpticFinder opticFinder, OpticFinder opticFinder2, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> SwimStatsRename.lambda$makeRule$1(opticFinder2, arg_0));
    }

    private static Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.update(opticFinder, SwimStatsRename::lambda$makeRule$0);
    }

    private static String lambda$makeRule$0(String string) {
        if (string.equals("minecraft:swim_one_cm")) {
            return "minecraft:walk_on_water_one_cm";
        }
        return string.equals("minecraft:dive_one_cm") ? "minecraft:walk_under_water_one_cm" : string;
    }
}

