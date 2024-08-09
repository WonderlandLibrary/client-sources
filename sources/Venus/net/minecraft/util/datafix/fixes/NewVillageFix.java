/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class NewVillageFix
extends DataFix {
    public NewVillageFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        CompoundList.CompoundListType<String, ?> compoundListType = DSL.compoundList(DSL.string(), this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE));
        OpticFinder opticFinder = compoundListType.finder();
        return this.func_219848_a(compoundListType);
    }

    private <SF> TypeRewriteRule func_219848_a(CompoundList.CompoundListType<String, SF> compoundListType) {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
        OpticFinder<?> opticFinder = type.findField("Level");
        OpticFinder<?> opticFinder2 = opticFinder.type().findField("Structures");
        OpticFinder<?> opticFinder3 = opticFinder2.type().findField("Starts");
        OpticFinder opticFinder4 = compoundListType.finder();
        return TypeRewriteRule.seq(this.fixTypeEverywhereTyped("NewVillageFix", type, arg_0 -> NewVillageFix.lambda$func_219848_a$10(opticFinder, opticFinder2, opticFinder3, opticFinder4, arg_0)), this.fixTypeEverywhereTyped("NewVillageStartFix", type2, NewVillageFix::lambda$func_219848_a$13));
    }

    private static Typed lambda$func_219848_a$13(Typed typed) {
        return typed.update(DSL.remainderFinder(), NewVillageFix::lambda$func_219848_a$12);
    }

    private static Dynamic lambda$func_219848_a$12(Dynamic dynamic) {
        return dynamic.update("id", NewVillageFix::lambda$func_219848_a$11);
    }

    private static Dynamic lambda$func_219848_a$11(Dynamic dynamic) {
        return Objects.equals(NamespacedSchema.ensureNamespaced(dynamic.asString("")), "minecraft:new_village") ? dynamic.createString("minecraft:village") : dynamic;
    }

    private static Typed lambda$func_219848_a$10(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, OpticFinder opticFinder4, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> NewVillageFix.lambda$func_219848_a$9(opticFinder2, opticFinder3, opticFinder4, arg_0));
    }

    private static Typed lambda$func_219848_a$9(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> NewVillageFix.lambda$func_219848_a$8(opticFinder2, opticFinder3, arg_0));
    }

    private static Typed lambda$func_219848_a$8(OpticFinder opticFinder, OpticFinder opticFinder2, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> NewVillageFix.lambda$func_219848_a$4(opticFinder2, arg_0)).update(DSL.remainderFinder(), NewVillageFix::lambda$func_219848_a$7);
    }

    private static Dynamic lambda$func_219848_a$7(Dynamic dynamic) {
        return dynamic.update("References", NewVillageFix::lambda$func_219848_a$6);
    }

    private static Dynamic lambda$func_219848_a$6(Dynamic dynamic) {
        Optional<Dynamic<Dynamic>> optional = dynamic.get("New_Village").result();
        return DataFixUtils.orElse(optional.map(arg_0 -> NewVillageFix.lambda$func_219848_a$5(dynamic, arg_0)), dynamic).remove("Village");
    }

    private static Dynamic lambda$func_219848_a$5(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.remove("New_Village").set("Village", dynamic2);
    }

    private static Typed lambda$func_219848_a$4(OpticFinder opticFinder, Typed typed) {
        return typed.update(opticFinder, NewVillageFix::lambda$func_219848_a$3);
    }

    private static List lambda$func_219848_a$3(List list) {
        return list.stream().filter(NewVillageFix::lambda$func_219848_a$0).map(NewVillageFix::lambda$func_219848_a$2).collect(Collectors.toList());
    }

    private static Pair lambda$func_219848_a$2(Pair pair) {
        return pair.mapFirst(NewVillageFix::lambda$func_219848_a$1);
    }

    private static String lambda$func_219848_a$1(String string) {
        return string.equals("New_Village") ? "Village" : string;
    }

    private static boolean lambda$func_219848_a$0(Pair pair) {
        return !Objects.equals(pair.getFirst(), "Village");
    }
}

