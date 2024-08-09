/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class AttributesFix
extends DataFix {
    private static final Map<String, String> field_233070_a_ = ImmutableMap.builder().put("generic.maxHealth", "generic.max_health").put("Max Health", "generic.max_health").put("zombie.spawnReinforcements", "zombie.spawn_reinforcements").put("Spawn Reinforcements Chance", "zombie.spawn_reinforcements").put("horse.jumpStrength", "horse.jump_strength").put("Jump Strength", "horse.jump_strength").put("generic.followRange", "generic.follow_range").put("Follow Range", "generic.follow_range").put("generic.knockbackResistance", "generic.knockback_resistance").put("Knockback Resistance", "generic.knockback_resistance").put("generic.movementSpeed", "generic.movement_speed").put("Movement Speed", "generic.movement_speed").put("generic.flyingSpeed", "generic.flying_speed").put("Flying Speed", "generic.flying_speed").put("generic.attackDamage", "generic.attack_damage").put("generic.attackKnockback", "generic.attack_knockback").put("generic.attackSpeed", "generic.attack_speed").put("generic.armorToughness", "generic.armor_toughness").build();

    public AttributesFix(Schema schema) {
        super(schema, false);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("tag");
        return TypeRewriteRule.seq(this.fixTypeEverywhereTyped("Rename ItemStack Attributes", type, arg_0 -> AttributesFix.lambda$makeRule$0(opticFinder, arg_0)), this.fixTypeEverywhereTyped("Rename Entity Attributes", this.getInputSchema().getType(TypeReferences.ENTITY), AttributesFix::func_233076_b_), this.fixTypeEverywhereTyped("Rename Player Attributes", this.getInputSchema().getType(TypeReferences.PLAYER), AttributesFix::func_233076_b_));
    }

    private static Dynamic<?> func_233073_a_(Dynamic<?> dynamic) {
        return DataFixUtils.orElse(dynamic.asString().result().map(AttributesFix::lambda$func_233073_a_$1).map(dynamic::createString), dynamic);
    }

    private static Typed<?> func_233072_a_(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), AttributesFix::lambda$func_233072_a_$5);
    }

    private static Typed<?> func_233076_b_(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), AttributesFix::lambda$func_233076_b_$9);
    }

    private static Dynamic lambda$func_233076_b_$9(Dynamic dynamic) {
        return dynamic.update("Attributes", AttributesFix::lambda$func_233076_b_$8);
    }

    private static Dynamic lambda$func_233076_b_$8(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asStreamOpt().result().map(AttributesFix::lambda$func_233076_b_$7).map(dynamic::createList), dynamic);
    }

    private static Stream lambda$func_233076_b_$7(Stream stream) {
        return stream.map(AttributesFix::lambda$func_233076_b_$6);
    }

    private static Dynamic lambda$func_233076_b_$6(Dynamic dynamic) {
        return dynamic.update("Name", AttributesFix::func_233073_a_);
    }

    private static Dynamic lambda$func_233072_a_$5(Dynamic dynamic) {
        return dynamic.update("AttributeModifiers", AttributesFix::lambda$func_233072_a_$4);
    }

    private static Dynamic lambda$func_233072_a_$4(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asStreamOpt().result().map(AttributesFix::lambda$func_233072_a_$3).map(dynamic::createList), dynamic);
    }

    private static Stream lambda$func_233072_a_$3(Stream stream) {
        return stream.map(AttributesFix::lambda$func_233072_a_$2);
    }

    private static Dynamic lambda$func_233072_a_$2(Dynamic dynamic) {
        return dynamic.update("AttributeName", AttributesFix::func_233073_a_);
    }

    private static String lambda$func_233073_a_$1(String string) {
        return field_233070_a_.getOrDefault(string, string);
    }

    private static Typed lambda$makeRule$0(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, AttributesFix::func_233072_a_);
    }
}

