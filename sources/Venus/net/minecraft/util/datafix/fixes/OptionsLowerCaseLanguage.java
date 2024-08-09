/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class OptionsLowerCaseLanguage
extends DataFix {
    public OptionsLowerCaseLanguage(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsLowerCaseLanguageFix", this.getInputSchema().getType(TypeReferences.OPTIONS), OptionsLowerCaseLanguage::lambda$makeRule$1);
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), OptionsLowerCaseLanguage::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        Optional<String> optional = dynamic.get("lang").asString().result();
        return optional.isPresent() ? dynamic.set("lang", dynamic.createString(optional.get().toLowerCase(Locale.ROOT))) : dynamic;
    }
}

