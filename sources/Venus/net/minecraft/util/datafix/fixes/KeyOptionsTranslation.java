/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.TypeReferences;

public class KeyOptionsTranslation
extends DataFix {
    public KeyOptionsTranslation(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsKeyTranslationFix", this.getInputSchema().getType(TypeReferences.OPTIONS), KeyOptionsTranslation::lambda$makeRule$3);
    }

    private static Typed lambda$makeRule$3(Typed typed) {
        return typed.update(DSL.remainderFinder(), KeyOptionsTranslation::lambda$makeRule$2);
    }

    private static Dynamic lambda$makeRule$2(Dynamic dynamic) {
        return dynamic.getMapValues().map(arg_0 -> KeyOptionsTranslation.lambda$makeRule$1(dynamic, arg_0)).result().orElse(dynamic);
    }

    private static Dynamic lambda$makeRule$1(Dynamic dynamic, Map map) {
        return dynamic.createMap(map.entrySet().stream().map(arg_0 -> KeyOptionsTranslation.lambda$makeRule$0(dynamic, arg_0)).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
    }

    private static Pair lambda$makeRule$0(Dynamic dynamic, Map.Entry entry) {
        String string;
        if (((Dynamic)entry.getKey()).asString("").startsWith("key_") && !(string = ((Dynamic)entry.getValue()).asString("")).startsWith("key.mouse") && !string.startsWith("scancode.")) {
            return Pair.of((Dynamic)entry.getKey(), dynamic.createString("key.keyboard." + string.substring(4)));
        }
        return Pair.of((Dynamic)entry.getKey(), (Dynamic)entry.getValue());
    }
}

