/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import net.minecraft.util.datafix.TypeReferences;

public class RedundantChanceTags
extends DataFix {
    private static final Codec<List<Float>> field_241303_a_ = Codec.FLOAT.listOf();

    public RedundantChanceTags(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityRedundantChanceTagsFix", this.getInputSchema().getType(TypeReferences.ENTITY), RedundantChanceTags::lambda$makeRule$1);
    }

    private static boolean func_241306_a_(OptionalDynamic<?> optionalDynamic, int n) {
        return optionalDynamic.flatMap(field_241303_a_::parse).map(arg_0 -> RedundantChanceTags.lambda$func_241306_a_$3(n, arg_0)).result().orElse(false);
    }

    private static Boolean lambda$func_241306_a_$3(int n, List list) {
        return list.size() == n && list.stream().allMatch(RedundantChanceTags::lambda$func_241306_a_$2);
    }

    private static boolean lambda$func_241306_a_$2(Float f) {
        return f.floatValue() == 0.0f;
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), RedundantChanceTags::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        if (RedundantChanceTags.func_241306_a_(dynamic.get("HandDropChances"), 2)) {
            dynamic = dynamic.remove("HandDropChances");
        }
        if (RedundantChanceTags.func_241306_a_(dynamic.get("ArmorDropChances"), 4)) {
            dynamic = dynamic.remove("ArmorDropChances");
        }
        return dynamic;
    }
}

