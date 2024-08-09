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
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class OminousBannerRenameFix
extends DataFix {
    public OminousBannerRenameFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private Dynamic<?> func_219818_a(Dynamic<?> dynamic) {
        Optional<Dynamic<?>> optional = dynamic.get("display").result();
        if (optional.isPresent()) {
            Dynamic dynamic2 = optional.get();
            Optional<String> optional2 = dynamic2.get("Name").asString().result();
            if (optional2.isPresent()) {
                String string = optional2.get();
                string = string.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
                dynamic2 = dynamic2.set("Name", dynamic2.createString(string));
            }
            return dynamic.set("display", dynamic2);
        }
        return dynamic;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        OpticFinder<?> opticFinder2 = type.findField("tag");
        return this.fixTypeEverywhereTyped("OminousBannerRenameFix", type, arg_0 -> this.lambda$makeRule$0(opticFinder, opticFinder2, arg_0));
    }

    private Typed lambda$makeRule$0(OpticFinder opticFinder, OpticFinder opticFinder2, Typed typed) {
        Optional optional;
        Optional optional2 = typed.getOptional(opticFinder);
        if (optional2.isPresent() && Objects.equals(((Pair)optional2.get()).getSecond(), "minecraft:white_banner") && (optional = typed.getOptionalTyped(opticFinder2)).isPresent()) {
            Typed<Dynamic<?>> typed2 = optional.get();
            Dynamic<?> dynamic = typed2.get(DSL.remainderFinder());
            return typed.set(opticFinder2, typed2.set(DSL.remainderFinder(), this.func_219818_a(dynamic)));
        }
        return typed;
    }
}

