/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;

public class SavedDataUUID
extends AbstractUUIDFix {
    public SavedDataUUID(Schema schema) {
        super(schema, TypeReferences.SAVED_DATA);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("SavedDataUUIDFix", this.getInputSchema().getType(this.reference), SavedDataUUID::lambda$makeRule$7);
    }

    private static Typed lambda$makeRule$7(Typed typed) {
        return typed.updateTyped(typed.getType().findField("data"), SavedDataUUID::lambda$makeRule$6);
    }

    private static Typed lambda$makeRule$6(Typed typed) {
        return typed.update(DSL.remainderFinder(), SavedDataUUID::lambda$makeRule$5);
    }

    private static Dynamic lambda$makeRule$5(Dynamic dynamic) {
        return dynamic.update("Raids", SavedDataUUID::lambda$makeRule$4);
    }

    private static Dynamic lambda$makeRule$4(Dynamic dynamic) {
        return dynamic.createList(dynamic.asStream().map(SavedDataUUID::lambda$makeRule$3));
    }

    private static Dynamic lambda$makeRule$3(Dynamic dynamic) {
        return dynamic.update("HeroesOfTheVillage", SavedDataUUID::lambda$makeRule$2);
    }

    private static Dynamic lambda$makeRule$2(Dynamic dynamic) {
        return dynamic.createList(dynamic.asStream().map(SavedDataUUID::lambda$makeRule$1));
    }

    private static Dynamic lambda$makeRule$1(Dynamic dynamic) {
        return SavedDataUUID.func_233065_d_(dynamic, "UUIDMost", "UUIDLeast").orElseGet(() -> SavedDataUUID.lambda$makeRule$0(dynamic));
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        LOGGER.warn("HeroesOfTheVillage contained invalid UUIDs.");
        return dynamic;
    }
}

