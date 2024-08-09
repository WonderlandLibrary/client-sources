/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class FurnaceRecipes
extends DataFix {
    public FurnaceRecipes(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.func_233248_a_(this.getOutputSchema().getTypeRaw(TypeReferences.RECIPE));
    }

    private <R> TypeRewriteRule func_233248_a_(Type<R> type) {
        Type type2 = DSL.and(DSL.optional(DSL.field("RecipesUsed", DSL.and(DSL.compoundList(type, DSL.intType()), DSL.remainderType()))), DSL.remainderType());
        OpticFinder<?> opticFinder = DSL.namedChoice("minecraft:furnace", this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:furnace"));
        OpticFinder<?> opticFinder2 = DSL.namedChoice("minecraft:blast_furnace", this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:blast_furnace"));
        OpticFinder<?> opticFinder3 = DSL.namedChoice("minecraft:smoker", this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:smoker"));
        Type<?> type3 = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:furnace");
        Type<?> type4 = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:blast_furnace");
        Type<?> type5 = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:smoker");
        Type<?> type6 = this.getInputSchema().getType(TypeReferences.BLOCK_ENTITY);
        Type<?> type7 = this.getOutputSchema().getType(TypeReferences.BLOCK_ENTITY);
        return this.fixTypeEverywhereTyped("FurnaceRecipesFix", type6, type7, arg_0 -> this.lambda$func_233248_a_$3(opticFinder, type3, type, type2, opticFinder2, type4, opticFinder3, type5, arg_0));
    }

    private <R> Typed<?> func_233249_a_(Type<R> type, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> type2, Typed<?> typed) {
        Dynamic<?> dynamic = typed.getOrCreate(DSL.remainderFinder());
        int n = dynamic.get("RecipesUsedSize").asInt(0);
        dynamic = dynamic.remove("RecipesUsedSize");
        ArrayList arrayList = Lists.newArrayList();
        for (int i = 0; i < n; ++i) {
            String string = "RecipeLocation" + i;
            String string2 = "RecipeAmount" + i;
            Optional<Dynamic<?>> optional = dynamic.get(string).result();
            int n2 = dynamic.get(string2).asInt(0);
            if (n2 > 0) {
                optional.ifPresent(arg_0 -> FurnaceRecipes.lambda$func_233249_a_$5(type, arrayList, n2, arg_0));
            }
            dynamic = dynamic.remove(string).remove(string2);
        }
        return typed.set(DSL.remainderFinder(), type2, Pair.of(Either.left(Pair.of(arrayList, dynamic.emptyMap())), dynamic));
    }

    private static void lambda$func_233249_a_$5(Type type, List list, int n, Dynamic dynamic) {
        Optional optional = type.read(dynamic).result();
        optional.ifPresent(arg_0 -> FurnaceRecipes.lambda$func_233249_a_$4(list, n, arg_0));
    }

    private static void lambda$func_233249_a_$4(List list, int n, Pair pair) {
        list.add(Pair.of(pair.getFirst(), n));
    }

    private Typed lambda$func_233248_a_$3(OpticFinder opticFinder, Type type, Type type2, Type type3, OpticFinder opticFinder2, Type type4, OpticFinder opticFinder3, Type type5, Typed typed) {
        return typed.updateTyped(opticFinder, type, arg_0 -> this.lambda$func_233248_a_$0(type2, type3, arg_0)).updateTyped(opticFinder2, type4, arg_0 -> this.lambda$func_233248_a_$1(type2, type3, arg_0)).updateTyped(opticFinder3, type5, arg_0 -> this.lambda$func_233248_a_$2(type2, type3, arg_0));
    }

    private Typed lambda$func_233248_a_$2(Type type, Type type2, Typed typed) {
        return this.func_233249_a_(type, type2, typed);
    }

    private Typed lambda$func_233248_a_$1(Type type, Type type2, Typed typed) {
        return this.func_233249_a_(type, type2, typed);
    }

    private Typed lambda$func_233248_a_$0(Type type, Type type2, Typed typed) {
        return this.func_233249_a_(type, type2, typed);
    }
}

