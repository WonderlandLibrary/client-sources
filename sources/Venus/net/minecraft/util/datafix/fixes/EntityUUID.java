/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;

public class EntityUUID
extends AbstractUUIDFix {
    private static final Set<String> field_233204_c_ = Sets.newHashSet();
    private static final Set<String> field_233205_d_ = Sets.newHashSet();
    private static final Set<String> field_233206_e_ = Sets.newHashSet();
    private static final Set<String> field_233207_f_ = Sets.newHashSet();
    private static final Set<String> field_233208_g_ = Sets.newHashSet();
    private static final Set<String> field_233209_h_ = Sets.newHashSet();

    public EntityUUID(Schema schema) {
        super(schema, TypeReferences.ENTITY);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.reference), this::lambda$makeRule$0);
    }

    private static Dynamic<?> func_233216_d_(Dynamic<?> dynamic) {
        return dynamic.update("Brain", EntityUUID::lambda$func_233216_d_$4);
    }

    private static Dynamic<?> func_233218_e_(Dynamic<?> dynamic) {
        return EntityUUID.func_233064_c_(dynamic, "OwnerUUID", "Owner").orElse(dynamic);
    }

    private static Dynamic<?> func_233220_f_(Dynamic<?> dynamic) {
        return EntityUUID.func_233064_c_(dynamic, "ConversionPlayer", "ConversionPlayer").orElse(dynamic);
    }

    private static Dynamic<?> func_233221_g_(Dynamic<?> dynamic) {
        return EntityUUID.func_233064_c_(dynamic, "OwnerUUID", "Owner").orElse(dynamic);
    }

    private static Dynamic<?> func_233222_h_(Dynamic<?> dynamic) {
        dynamic = EntityUUID.func_233062_b_(dynamic, "Owner", "Owner").orElse(dynamic);
        return EntityUUID.func_233062_b_(dynamic, "Target", "Target").orElse(dynamic);
    }

    private static Dynamic<?> func_233223_i_(Dynamic<?> dynamic) {
        dynamic = EntityUUID.func_233062_b_(dynamic, "Owner", "Owner").orElse(dynamic);
        return EntityUUID.func_233062_b_(dynamic, "Thrower", "Thrower").orElse(dynamic);
    }

    private static Dynamic<?> func_233224_j_(Dynamic<?> dynamic) {
        Optional<Dynamic> optional = dynamic.get("TrustedUUIDs").result().map(arg_0 -> EntityUUID.lambda$func_233224_j_$7(dynamic, arg_0));
        return DataFixUtils.orElse(optional.map(arg_0 -> EntityUUID.lambda$func_233224_j_$8(dynamic, arg_0)), dynamic);
    }

    private static Dynamic<?> func_233225_k_(Dynamic<?> dynamic) {
        return EntityUUID.func_233058_a_(dynamic, "HurtBy", "HurtBy").orElse(dynamic);
    }

    private static Dynamic<?> func_233226_l_(Dynamic<?> dynamic) {
        Dynamic<?> dynamic2 = EntityUUID.func_233227_m_(dynamic);
        return EntityUUID.func_233058_a_(dynamic2, "OwnerUUID", "Owner").orElse(dynamic2);
    }

    private static Dynamic<?> func_233227_m_(Dynamic<?> dynamic) {
        Dynamic<?> dynamic2 = EntityUUID.func_233228_n_(dynamic);
        return EntityUUID.func_233064_c_(dynamic2, "LoveCause", "LoveCause").orElse(dynamic2);
    }

    private static Dynamic<?> func_233228_n_(Dynamic<?> dynamic) {
        return EntityUUID.func_233212_b_(dynamic).update("Leash", EntityUUID::lambda$func_233228_n_$9);
    }

    public static Dynamic<?> func_233212_b_(Dynamic<?> dynamic) {
        return dynamic.update("Attributes", arg_0 -> EntityUUID.lambda$func_233212_b_$13(dynamic, arg_0));
    }

    private static Dynamic<?> func_233229_o_(Dynamic<?> dynamic) {
        return DataFixUtils.orElse(dynamic.get("OwnerUUID").result().map(arg_0 -> EntityUUID.lambda$func_233229_o_$14(dynamic, arg_0)), dynamic);
    }

    public static Dynamic<?> func_233214_c_(Dynamic<?> dynamic) {
        return EntityUUID.func_233064_c_(dynamic, "UUID", "UUID").orElse(dynamic);
    }

    private static Dynamic lambda$func_233229_o_$14(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.remove("OwnerUUID").set("Owner", dynamic2);
    }

    private static Dynamic lambda$func_233212_b_$13(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().map(EntityUUID::lambda$func_233212_b_$12));
    }

    private static Dynamic lambda$func_233212_b_$12(Dynamic dynamic) {
        return dynamic.update("Modifiers", arg_0 -> EntityUUID.lambda$func_233212_b_$11(dynamic, arg_0));
    }

    private static Dynamic lambda$func_233212_b_$11(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().map(EntityUUID::lambda$func_233212_b_$10));
    }

    private static Dynamic lambda$func_233212_b_$10(Dynamic dynamic) {
        return EntityUUID.func_233064_c_(dynamic, "UUID", "UUID").orElse(dynamic);
    }

    private static Dynamic lambda$func_233228_n_$9(Dynamic dynamic) {
        return EntityUUID.func_233064_c_(dynamic, "UUID", "UUID").orElse(dynamic);
    }

    private static Dynamic lambda$func_233224_j_$8(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.remove("TrustedUUIDs").set("Trusted", dynamic2);
    }

    private static Dynamic lambda$func_233224_j_$7(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().map(EntityUUID::lambda$func_233224_j_$6));
    }

    private static Dynamic lambda$func_233224_j_$6(Dynamic dynamic) {
        return EntityUUID.func_233054_a_(dynamic).orElseGet(() -> EntityUUID.lambda$func_233224_j_$5(dynamic));
    }

    private static Dynamic lambda$func_233224_j_$5(Dynamic dynamic) {
        LOGGER.warn("Trusted contained invalid data.");
        return dynamic;
    }

    private static Dynamic lambda$func_233216_d_$4(Dynamic dynamic) {
        return dynamic.update("memories", EntityUUID::lambda$func_233216_d_$3);
    }

    private static Dynamic lambda$func_233216_d_$3(Dynamic dynamic) {
        return dynamic.update("minecraft:angry_at", EntityUUID::lambda$func_233216_d_$2);
    }

    private static Dynamic lambda$func_233216_d_$2(Dynamic dynamic) {
        return EntityUUID.func_233058_a_(dynamic, "value", "value").orElseGet(() -> EntityUUID.lambda$func_233216_d_$1(dynamic));
    }

    private static Dynamic lambda$func_233216_d_$1(Dynamic dynamic) {
        LOGGER.warn("angry_at has no value.");
        return dynamic;
    }

    private Typed lambda$makeRule$0(Typed typed) {
        typed = typed.update(DSL.remainderFinder(), EntityUUID::func_233214_c_);
        for (String string : field_233204_c_) {
            typed = this.func_233053_a_(typed, string, EntityUUID::func_233226_l_);
        }
        for (String string : field_233205_d_) {
            typed = this.func_233053_a_(typed, string, EntityUUID::func_233226_l_);
        }
        for (String string : field_233206_e_) {
            typed = this.func_233053_a_(typed, string, EntityUUID::func_233227_m_);
        }
        for (String string : field_233207_f_) {
            typed = this.func_233053_a_(typed, string, EntityUUID::func_233228_n_);
        }
        for (String string : field_233208_g_) {
            typed = this.func_233053_a_(typed, string, EntityUUID::func_233212_b_);
        }
        for (String string : field_233209_h_) {
            typed = this.func_233053_a_(typed, string, EntityUUID::func_233229_o_);
        }
        typed = this.func_233053_a_(typed, "minecraft:bee", EntityUUID::func_233225_k_);
        typed = this.func_233053_a_(typed, "minecraft:zombified_piglin", EntityUUID::func_233225_k_);
        typed = this.func_233053_a_(typed, "minecraft:fox", EntityUUID::func_233224_j_);
        typed = this.func_233053_a_(typed, "minecraft:item", EntityUUID::func_233223_i_);
        typed = this.func_233053_a_(typed, "minecraft:shulker_bullet", EntityUUID::func_233222_h_);
        typed = this.func_233053_a_(typed, "minecraft:area_effect_cloud", EntityUUID::func_233221_g_);
        typed = this.func_233053_a_(typed, "minecraft:zombie_villager", EntityUUID::func_233220_f_);
        typed = this.func_233053_a_(typed, "minecraft:evoker_fangs", EntityUUID::func_233218_e_);
        return this.func_233053_a_(typed, "minecraft:piglin", EntityUUID::func_233216_d_);
    }

    static {
        field_233204_c_.add("minecraft:donkey");
        field_233204_c_.add("minecraft:horse");
        field_233204_c_.add("minecraft:llama");
        field_233204_c_.add("minecraft:mule");
        field_233204_c_.add("minecraft:skeleton_horse");
        field_233204_c_.add("minecraft:trader_llama");
        field_233204_c_.add("minecraft:zombie_horse");
        field_233205_d_.add("minecraft:cat");
        field_233205_d_.add("minecraft:parrot");
        field_233205_d_.add("minecraft:wolf");
        field_233206_e_.add("minecraft:bee");
        field_233206_e_.add("minecraft:chicken");
        field_233206_e_.add("minecraft:cow");
        field_233206_e_.add("minecraft:fox");
        field_233206_e_.add("minecraft:mooshroom");
        field_233206_e_.add("minecraft:ocelot");
        field_233206_e_.add("minecraft:panda");
        field_233206_e_.add("minecraft:pig");
        field_233206_e_.add("minecraft:polar_bear");
        field_233206_e_.add("minecraft:rabbit");
        field_233206_e_.add("minecraft:sheep");
        field_233206_e_.add("minecraft:turtle");
        field_233206_e_.add("minecraft:hoglin");
        field_233207_f_.add("minecraft:bat");
        field_233207_f_.add("minecraft:blaze");
        field_233207_f_.add("minecraft:cave_spider");
        field_233207_f_.add("minecraft:cod");
        field_233207_f_.add("minecraft:creeper");
        field_233207_f_.add("minecraft:dolphin");
        field_233207_f_.add("minecraft:drowned");
        field_233207_f_.add("minecraft:elder_guardian");
        field_233207_f_.add("minecraft:ender_dragon");
        field_233207_f_.add("minecraft:enderman");
        field_233207_f_.add("minecraft:endermite");
        field_233207_f_.add("minecraft:evoker");
        field_233207_f_.add("minecraft:ghast");
        field_233207_f_.add("minecraft:giant");
        field_233207_f_.add("minecraft:guardian");
        field_233207_f_.add("minecraft:husk");
        field_233207_f_.add("minecraft:illusioner");
        field_233207_f_.add("minecraft:magma_cube");
        field_233207_f_.add("minecraft:pufferfish");
        field_233207_f_.add("minecraft:zombified_piglin");
        field_233207_f_.add("minecraft:salmon");
        field_233207_f_.add("minecraft:shulker");
        field_233207_f_.add("minecraft:silverfish");
        field_233207_f_.add("minecraft:skeleton");
        field_233207_f_.add("minecraft:slime");
        field_233207_f_.add("minecraft:snow_golem");
        field_233207_f_.add("minecraft:spider");
        field_233207_f_.add("minecraft:squid");
        field_233207_f_.add("minecraft:stray");
        field_233207_f_.add("minecraft:tropical_fish");
        field_233207_f_.add("minecraft:vex");
        field_233207_f_.add("minecraft:villager");
        field_233207_f_.add("minecraft:iron_golem");
        field_233207_f_.add("minecraft:vindicator");
        field_233207_f_.add("minecraft:pillager");
        field_233207_f_.add("minecraft:wandering_trader");
        field_233207_f_.add("minecraft:witch");
        field_233207_f_.add("minecraft:wither");
        field_233207_f_.add("minecraft:wither_skeleton");
        field_233207_f_.add("minecraft:zombie");
        field_233207_f_.add("minecraft:zombie_villager");
        field_233207_f_.add("minecraft:phantom");
        field_233207_f_.add("minecraft:ravager");
        field_233207_f_.add("minecraft:piglin");
        field_233208_g_.add("minecraft:armor_stand");
        field_233209_h_.add("minecraft:arrow");
        field_233209_h_.add("minecraft:dragon_fireball");
        field_233209_h_.add("minecraft:firework_rocket");
        field_233209_h_.add("minecraft:fireball");
        field_233209_h_.add("minecraft:llama_spit");
        field_233209_h_.add("minecraft:small_fireball");
        field_233209_h_.add("minecraft:snowball");
        field_233209_h_.add("minecraft:spectral_arrow");
        field_233209_h_.add("minecraft:egg");
        field_233209_h_.add("minecraft:ender_pearl");
        field_233209_h_.add("minecraft:experience_bottle");
        field_233209_h_.add("minecraft:potion");
        field_233209_h_.add("minecraft:trident");
        field_233209_h_.add("minecraft:wither_skull");
    }
}

