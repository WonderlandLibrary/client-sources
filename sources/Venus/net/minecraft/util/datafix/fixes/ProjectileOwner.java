/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class ProjectileOwner
extends DataFix {
    public ProjectileOwner(Schema schema) {
        super(schema, false);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        return this.fixTypeEverywhereTyped("EntityProjectileOwner", schema.getType(TypeReferences.ENTITY), this::func_233183_a_);
    }

    private Typed<?> func_233183_a_(Typed<?> typed) {
        typed = this.func_233184_a_(typed, "minecraft:egg", this::func_233190_d_);
        typed = this.func_233184_a_(typed, "minecraft:ender_pearl", this::func_233190_d_);
        typed = this.func_233184_a_(typed, "minecraft:experience_bottle", this::func_233190_d_);
        typed = this.func_233184_a_(typed, "minecraft:snowball", this::func_233190_d_);
        typed = this.func_233184_a_(typed, "minecraft:potion", this::func_233190_d_);
        typed = this.func_233184_a_(typed, "minecraft:potion", this::func_233189_c_);
        typed = this.func_233184_a_(typed, "minecraft:llama_spit", this::func_233188_b_);
        typed = this.func_233184_a_(typed, "minecraft:arrow", this::func_233185_a_);
        typed = this.func_233184_a_(typed, "minecraft:spectral_arrow", this::func_233185_a_);
        return this.func_233184_a_(typed, "minecraft:trident", this::func_233185_a_);
    }

    private Dynamic<?> func_233185_a_(Dynamic<?> dynamic) {
        long l = dynamic.get("OwnerUUIDMost").asLong(0L);
        long l2 = dynamic.get("OwnerUUIDLeast").asLong(0L);
        return this.func_233186_a_(dynamic, l, l2).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
    }

    private Dynamic<?> func_233188_b_(Dynamic<?> dynamic) {
        OptionalDynamic<?> optionalDynamic = dynamic.get("Owner");
        long l = optionalDynamic.get("OwnerUUIDMost").asLong(0L);
        long l2 = optionalDynamic.get("OwnerUUIDLeast").asLong(0L);
        return this.func_233186_a_(dynamic, l, l2).remove("Owner");
    }

    private Dynamic<?> func_233189_c_(Dynamic<?> dynamic) {
        OptionalDynamic<?> optionalDynamic = dynamic.get("Potion");
        return dynamic.set("Item", optionalDynamic.orElseEmptyMap()).remove("Potion");
    }

    private Dynamic<?> func_233190_d_(Dynamic<?> dynamic) {
        String string = "owner";
        OptionalDynamic<?> optionalDynamic = dynamic.get("owner");
        long l = optionalDynamic.get("M").asLong(0L);
        long l2 = optionalDynamic.get("L").asLong(0L);
        return this.func_233186_a_(dynamic, l, l2).remove("owner");
    }

    private Dynamic<?> func_233186_a_(Dynamic<?> dynamic, long l, long l2) {
        String string = "OwnerUUID";
        return l != 0L && l2 != 0L ? dynamic.set("OwnerUUID", dynamic.createIntList(Arrays.stream(ProjectileOwner.func_233182_a_(l, l2)))) : dynamic;
    }

    private static int[] func_233182_a_(long l, long l2) {
        return new int[]{(int)(l >> 32), (int)l, (int)(l2 >> 32), (int)l2};
    }

    private Typed<?> func_233184_a_(Typed<?> typed, String string, Function<Dynamic<?>, Dynamic<?>> function) {
        Type<?> type = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, string);
        Type<?> type2 = this.getOutputSchema().getChoiceType(TypeReferences.ENTITY, string);
        return typed.updateTyped(DSL.namedChoice(string, type), type2, arg_0 -> ProjectileOwner.lambda$func_233184_a_$0(function, arg_0));
    }

    private static Typed lambda$func_233184_a_$0(Function function, Typed typed) {
        return typed.update(DSL.remainderFinder(), function);
    }
}

