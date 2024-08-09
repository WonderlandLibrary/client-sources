/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;

public class ItemStackUUID
extends AbstractUUIDFix {
    public ItemStackUUID(Schema schema) {
        super(schema, TypeReferences.ITEM_STACK);
    }

    @Override
    public TypeRewriteRule makeRule() {
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.reference), arg_0 -> this.lambda$makeRule$3(opticFinder, arg_0));
    }

    private Dynamic<?> func_233282_b_(Dynamic<?> dynamic) {
        return dynamic.update("AttributeModifiers", arg_0 -> ItemStackUUID.lambda$func_233282_b_$5(dynamic, arg_0));
    }

    private Dynamic<?> func_233283_c_(Dynamic<?> dynamic) {
        return dynamic.update("SkullOwner", ItemStackUUID::lambda$func_233283_c_$6);
    }

    private static Dynamic lambda$func_233283_c_$6(Dynamic dynamic) {
        return ItemStackUUID.func_233058_a_(dynamic, "Id", "Id").orElse(dynamic);
    }

    private static Dynamic lambda$func_233282_b_$5(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().map(ItemStackUUID::lambda$func_233282_b_$4));
    }

    private static Dynamic lambda$func_233282_b_$4(Dynamic dynamic) {
        return ItemStackUUID.func_233064_c_(dynamic, "UUID", "UUID").orElse(dynamic);
    }

    private Typed lambda$makeRule$3(OpticFinder opticFinder, Typed typed) {
        OpticFinder<?> opticFinder2 = typed.getType().findField("tag");
        return typed.updateTyped(opticFinder2, arg_0 -> this.lambda$makeRule$2(typed, opticFinder, arg_0));
    }

    private Typed lambda$makeRule$2(Typed typed, OpticFinder opticFinder, Typed typed2) {
        return typed2.update(DSL.remainderFinder(), arg_0 -> this.lambda$makeRule$1(typed, opticFinder, arg_0));
    }

    private Dynamic lambda$makeRule$1(Typed typed, OpticFinder opticFinder, Dynamic dynamic) {
        dynamic = this.func_233282_b_(dynamic);
        if (typed.getOptional(opticFinder).map(ItemStackUUID::lambda$makeRule$0).orElse(false).booleanValue()) {
            dynamic = this.func_233283_c_(dynamic);
        }
        return dynamic;
    }

    private static Boolean lambda$makeRule$0(Pair pair) {
        return "minecraft:player_head".equals(pair.getSecond());
    }
}

