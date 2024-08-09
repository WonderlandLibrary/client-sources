/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class VillagerTrades
extends NamedEntityFix {
    public VillagerTrades(Schema schema, boolean bl) {
        super(schema, bl, "Villager trade fix", TypeReferences.ENTITY, "minecraft:villager");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        OpticFinder<?> opticFinder = typed.getType().findField("Offers");
        OpticFinder<?> opticFinder2 = opticFinder.type().findField("Recipes");
        Type<?> type = opticFinder2.type();
        if (!(type instanceof List.ListType)) {
            throw new IllegalStateException("Recipes are expected to be a list.");
        }
        List.ListType listType = (List.ListType)type;
        Type type2 = listType.getElement();
        OpticFinder opticFinder3 = DSL.typeFinder(type2);
        OpticFinder<?> opticFinder4 = type2.findField("buy");
        OpticFinder<?> opticFinder5 = type2.findField("buyB");
        OpticFinder<?> opticFinder6 = type2.findField("sell");
        OpticFinder<Pair<String, String>> opticFinder7 = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        Function<Typed, Typed> function = arg_0 -> this.lambda$fix$0(opticFinder7, arg_0);
        return typed.updateTyped(opticFinder, arg_0 -> VillagerTrades.lambda$fix$3(opticFinder2, opticFinder3, opticFinder4, function, opticFinder5, opticFinder6, arg_0));
    }

    private Typed<?> updateItemStack(OpticFinder<Pair<String, String>> opticFinder, Typed<?> typed) {
        return typed.update(opticFinder, VillagerTrades::lambda$updateItemStack$5);
    }

    private static Pair lambda$updateItemStack$5(Pair pair) {
        return pair.mapSecond(VillagerTrades::lambda$updateItemStack$4);
    }

    private static String lambda$updateItemStack$4(String string) {
        return Objects.equals(string, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : string;
    }

    private static Typed lambda$fix$3(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Function function, OpticFinder opticFinder4, OpticFinder opticFinder5, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> VillagerTrades.lambda$fix$2(opticFinder2, opticFinder3, function, opticFinder4, opticFinder5, arg_0));
    }

    private static Typed lambda$fix$2(OpticFinder opticFinder, OpticFinder opticFinder2, Function function, OpticFinder opticFinder3, OpticFinder opticFinder4, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> VillagerTrades.lambda$fix$1(opticFinder2, function, opticFinder3, opticFinder4, arg_0));
    }

    private static Typed lambda$fix$1(OpticFinder opticFinder, Function function, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        return typed.updateTyped(opticFinder, function).updateTyped(opticFinder2, function).updateTyped(opticFinder3, function);
    }

    private Typed lambda$fix$0(OpticFinder opticFinder, Typed typed) {
        return this.updateItemStack(opticFinder, typed);
    }
}

