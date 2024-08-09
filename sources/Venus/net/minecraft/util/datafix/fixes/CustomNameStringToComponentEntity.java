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
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class CustomNameStringToComponentEntity
extends DataFix {
    public CustomNameStringToComponentEntity(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        OpticFinder<String> opticFinder = DSL.fieldFinder("id", NamespacedSchema.func_233457_a_());
        return this.fixTypeEverywhereTyped("EntityCustomNameToComponentFix", this.getInputSchema().getType(TypeReferences.ENTITY), arg_0 -> CustomNameStringToComponentEntity.lambda$makeRule$1(opticFinder, arg_0));
    }

    public static Dynamic<?> fixTagCustomName(Dynamic<?> dynamic) {
        String string = dynamic.get("CustomName").asString("");
        return string.isEmpty() ? dynamic.remove("CustomName") : dynamic.set("CustomName", dynamic.createString(ITextComponent.Serializer.toJson(new StringTextComponent(string))));
    }

    private static Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.update(DSL.remainderFinder(), arg_0 -> CustomNameStringToComponentEntity.lambda$makeRule$0(typed, opticFinder, arg_0));
    }

    private static Dynamic lambda$makeRule$0(Typed typed, OpticFinder opticFinder, Dynamic dynamic) {
        Optional optional = typed.getOptional(opticFinder);
        return optional.isPresent() && Objects.equals(optional.get(), "minecraft:commandblock_minecart") ? dynamic : CustomNameStringToComponentEntity.fixTagCustomName(dynamic);
    }
}

