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
import net.minecraft.util.datafix.TypeReferences;

public class PaintingDirection
extends DataFix {
    private static final int[][] DIRECTIONS = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

    public PaintingDirection(Schema schema, boolean bl) {
        super(schema, bl);
    }

    private Dynamic<?> doFix(Dynamic<?> dynamic, boolean bl, boolean bl2) {
        if ((bl || bl2) && !dynamic.get("Facing").asNumber().result().isPresent()) {
            int n;
            if (dynamic.get("Direction").asNumber().result().isPresent()) {
                n = dynamic.get("Direction").asByte((byte)0) % DIRECTIONS.length;
                int[] nArray = DIRECTIONS[n];
                dynamic = dynamic.set("TileX", dynamic.createInt(dynamic.get("TileX").asInt(0) + nArray[0]));
                dynamic = dynamic.set("TileY", dynamic.createInt(dynamic.get("TileY").asInt(0) + nArray[1]));
                dynamic = dynamic.set("TileZ", dynamic.createInt(dynamic.get("TileZ").asInt(0) + nArray[2]));
                dynamic = dynamic.remove("Direction");
                if (bl2 && dynamic.get("ItemRotation").asNumber().result().isPresent()) {
                    dynamic = dynamic.set("ItemRotation", dynamic.createByte((byte)(dynamic.get("ItemRotation").asByte((byte)0) * 2)));
                }
            } else {
                n = dynamic.get("Dir").asByte((byte)0) % DIRECTIONS.length;
                dynamic = dynamic.remove("Dir");
            }
            dynamic = dynamic.set("Facing", dynamic.createByte((byte)n));
        }
        return dynamic;
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "Painting");
        OpticFinder<?> opticFinder = DSL.namedChoice("Painting", type);
        Type<?> type2 = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "ItemFrame");
        OpticFinder<?> opticFinder2 = DSL.namedChoice("ItemFrame", type2);
        Type<?> type3 = this.getInputSchema().getType(TypeReferences.ENTITY);
        TypeRewriteRule typeRewriteRule = this.fixTypeEverywhereTyped("EntityPaintingFix", type3, arg_0 -> this.lambda$makeRule$2(opticFinder, type, arg_0));
        TypeRewriteRule typeRewriteRule2 = this.fixTypeEverywhereTyped("EntityItemFrameFix", type3, arg_0 -> this.lambda$makeRule$5(opticFinder2, type2, arg_0));
        return TypeRewriteRule.seq(typeRewriteRule, typeRewriteRule2);
    }

    private Typed lambda$makeRule$5(OpticFinder opticFinder, Type type, Typed typed) {
        return typed.updateTyped(opticFinder, type, this::lambda$makeRule$4);
    }

    private Typed lambda$makeRule$4(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$3);
    }

    private Dynamic lambda$makeRule$3(Dynamic dynamic) {
        return this.doFix(dynamic, false, false);
    }

    private Typed lambda$makeRule$2(OpticFinder opticFinder, Type type, Typed typed) {
        return typed.updateTyped(opticFinder, type, this::lambda$makeRule$1);
    }

    private Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$makeRule$0);
    }

    private Dynamic lambda$makeRule$0(Dynamic dynamic) {
        return this.doFix(dynamic, true, true);
    }
}

