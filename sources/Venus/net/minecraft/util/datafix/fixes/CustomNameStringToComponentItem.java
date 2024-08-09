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
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CustomNameStringToComponentItem
extends DataFix {
    public CustomNameStringToComponentItem(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private Dynamic<?> fixTag(Dynamic<?> dynamic) {
        Optional<Dynamic<?>> optional = dynamic.get("display").result();
        if (optional.isPresent()) {
            Dynamic dynamic2 = optional.get();
            Optional<String> optional2 = dynamic2.get("Name").asString().result();
            if (optional2.isPresent()) {
                dynamic2 = dynamic2.set("Name", dynamic2.createString(ITextComponent.Serializer.toJson(new StringTextComponent(optional2.get()))));
            } else {
                Optional<String> optional3 = dynamic2.get("LocName").asString().result();
                if (optional3.isPresent()) {
                    dynamic2 = dynamic2.set("Name", dynamic2.createString(ITextComponent.Serializer.toJson(new TranslationTextComponent(optional3.get()))));
                    dynamic2 = dynamic2.remove("LocName");
                }
            }
            return dynamic.set("display", dynamic2);
        }
        return dynamic;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemCustomNameToComponentFix", type, arg_0 -> this.lambda$makeRule$1(opticFinder, arg_0));
    }

    private Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, this::lambda$makeRule$0);
    }

    private Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }
}

