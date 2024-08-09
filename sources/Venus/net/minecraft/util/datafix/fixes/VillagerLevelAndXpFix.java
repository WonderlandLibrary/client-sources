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
import com.mojang.datafixers.types.templates.List;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.math.MathHelper;

public class VillagerLevelAndXpFix
extends DataFix {
    private static final int[] field_223004_a = new int[]{0, 10, 50, 100, 150};

    public static int func_223001_a(int n) {
        return field_223004_a[MathHelper.clamp(n - 1, 0, field_223004_a.length - 1)];
    }

    public VillagerLevelAndXpFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "minecraft:villager");
        OpticFinder<?> opticFinder = DSL.namedChoice("minecraft:villager", type);
        OpticFinder<?> opticFinder2 = type.findField("Offers");
        Type<?> type2 = opticFinder2.type();
        OpticFinder<?> opticFinder3 = type2.findField("Recipes");
        List.ListType listType = (List.ListType)opticFinder3.type();
        OpticFinder opticFinder4 = listType.getElement().finder();
        return this.fixTypeEverywhereTyped("Villager level and xp rebuild", this.getInputSchema().getType(TypeReferences.ENTITY), arg_0 -> VillagerLevelAndXpFix.lambda$makeRule$3(opticFinder, type, opticFinder2, opticFinder3, opticFinder4, arg_0));
    }

    private static Typed<?> func_223003_a(Typed<?> typed, int n) {
        return typed.update(DSL.remainderFinder(), arg_0 -> VillagerLevelAndXpFix.lambda$func_223003_a$5(n, arg_0));
    }

    private static Typed<?> func_222994_b(Typed<?> typed, int n) {
        int n2 = VillagerLevelAndXpFix.func_223001_a(n);
        return typed.update(DSL.remainderFinder(), arg_0 -> VillagerLevelAndXpFix.lambda$func_222994_b$6(n2, arg_0));
    }

    private static Dynamic lambda$func_222994_b$6(int n, Dynamic dynamic) {
        return dynamic.set("Xp", dynamic.createInt(n));
    }

    private static Dynamic lambda$func_223003_a$5(int n, Dynamic dynamic) {
        return dynamic.update("VillagerData", arg_0 -> VillagerLevelAndXpFix.lambda$func_223003_a$4(n, arg_0));
    }

    private static Dynamic lambda$func_223003_a$4(int n, Dynamic dynamic) {
        return dynamic.set("level", dynamic.createInt(n));
    }

    private static Typed lambda$makeRule$3(OpticFinder opticFinder, Type type, OpticFinder opticFinder2, OpticFinder opticFinder3, OpticFinder opticFinder4, Typed typed) {
        return typed.updateTyped(opticFinder, type, arg_0 -> VillagerLevelAndXpFix.lambda$makeRule$2(opticFinder2, opticFinder3, opticFinder4, arg_0));
    }

    private static Typed lambda$makeRule$2(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        Optional<Number> optional;
        int n;
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        int n2 = dynamic.get("VillagerData").get("level").asInt(0);
        Typed<?> typed2 = typed;
        if ((n2 == 0 || n2 == 1) && (n2 = MathHelper.clamp((n = typed.getOptionalTyped(opticFinder).flatMap(arg_0 -> VillagerLevelAndXpFix.lambda$makeRule$0(opticFinder2, arg_0)).map(arg_0 -> VillagerLevelAndXpFix.lambda$makeRule$1(opticFinder3, arg_0)).orElse(0).intValue()) / 2, 1, 5)) > 1) {
            typed2 = VillagerLevelAndXpFix.func_223003_a(typed, n2);
        }
        if (!(optional = dynamic.get("Xp").asNumber().result()).isPresent()) {
            typed2 = VillagerLevelAndXpFix.func_222994_b(typed2, n2);
        }
        return typed2;
    }

    private static Integer lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.getAllTyped(opticFinder).size();
    }

    private static Optional lambda$makeRule$0(OpticFinder opticFinder, Typed typed) {
        return typed.getOptionalTyped(opticFinder);
    }
}

