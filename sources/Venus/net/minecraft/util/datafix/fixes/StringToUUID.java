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
import java.util.Optional;
import java.util.UUID;
import net.minecraft.util.datafix.TypeReferences;

public class StringToUUID
extends DataFix {
    public StringToUUID(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityStringUuidFix", this.getInputSchema().getType(TypeReferences.ENTITY), StringToUUID::lambda$makeRule$1);
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), StringToUUID::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        Optional<String> optional = dynamic.get("UUID").asString().result();
        if (optional.isPresent()) {
            UUID uUID = UUID.fromString(optional.get());
            return dynamic.remove("UUID").set("UUIDMost", dynamic.createLong(uUID.getMostSignificantBits())).set("UUIDLeast", dynamic.createLong(uUID.getLeastSignificantBits()));
        }
        return dynamic;
    }
}

