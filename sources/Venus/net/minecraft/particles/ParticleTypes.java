/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.registry.Registry;

public class ParticleTypes {
    public static final BasicParticleType AMBIENT_ENTITY_EFFECT = ParticleTypes.register("ambient_entity_effect", false);
    public static final BasicParticleType ANGRY_VILLAGER = ParticleTypes.register("angry_villager", false);
    public static final BasicParticleType BARRIER = ParticleTypes.register("barrier", false);
    public static final ParticleType<BlockParticleData> BLOCK = ParticleTypes.register("block", BlockParticleData.DESERIALIZER, BlockParticleData::func_239800_a_);
    public static final BasicParticleType BUBBLE = ParticleTypes.register("bubble", false);
    public static final BasicParticleType CLOUD = ParticleTypes.register("cloud", false);
    public static final BasicParticleType CRIT = ParticleTypes.register("crit", false);
    public static final BasicParticleType DAMAGE_INDICATOR = ParticleTypes.register("damage_indicator", true);
    public static final BasicParticleType DRAGON_BREATH = ParticleTypes.register("dragon_breath", false);
    public static final BasicParticleType DRIPPING_LAVA = ParticleTypes.register("dripping_lava", false);
    public static final BasicParticleType FALLING_LAVA = ParticleTypes.register("falling_lava", false);
    public static final BasicParticleType LANDING_LAVA = ParticleTypes.register("landing_lava", false);
    public static final BasicParticleType DRIPPING_WATER = ParticleTypes.register("dripping_water", false);
    public static final BasicParticleType FALLING_WATER = ParticleTypes.register("falling_water", false);
    public static final ParticleType<RedstoneParticleData> DUST = ParticleTypes.register("dust", RedstoneParticleData.DESERIALIZER, ParticleTypes::lambda$static$0);
    public static final BasicParticleType EFFECT = ParticleTypes.register("effect", false);
    public static final BasicParticleType ELDER_GUARDIAN = ParticleTypes.register("elder_guardian", true);
    public static final BasicParticleType ENCHANTED_HIT = ParticleTypes.register("enchanted_hit", false);
    public static final BasicParticleType ENCHANT = ParticleTypes.register("enchant", false);
    public static final BasicParticleType END_ROD = ParticleTypes.register("end_rod", false);
    public static final BasicParticleType ENTITY_EFFECT = ParticleTypes.register("entity_effect", false);
    public static final BasicParticleType EXPLOSION_EMITTER = ParticleTypes.register("explosion_emitter", true);
    public static final BasicParticleType EXPLOSION = ParticleTypes.register("explosion", true);
    public static final ParticleType<BlockParticleData> FALLING_DUST = ParticleTypes.register("falling_dust", BlockParticleData.DESERIALIZER, BlockParticleData::func_239800_a_);
    public static final BasicParticleType FIREWORK = ParticleTypes.register("firework", false);
    public static final BasicParticleType FISHING = ParticleTypes.register("fishing", false);
    public static final BasicParticleType FLAME = ParticleTypes.register("flame", false);
    public static final BasicParticleType SOUL_FIRE_FLAME = ParticleTypes.register("soul_fire_flame", false);
    public static final BasicParticleType SOUL = ParticleTypes.register("soul", false);
    public static final BasicParticleType FLASH = ParticleTypes.register("flash", false);
    public static final BasicParticleType HAPPY_VILLAGER = ParticleTypes.register("happy_villager", false);
    public static final BasicParticleType COMPOSTER = ParticleTypes.register("composter", false);
    public static final BasicParticleType HEART = ParticleTypes.register("heart", false);
    public static final BasicParticleType INSTANT_EFFECT = ParticleTypes.register("instant_effect", false);
    public static final ParticleType<ItemParticleData> ITEM = ParticleTypes.register("item", ItemParticleData.DESERIALIZER, ItemParticleData::func_239809_a_);
    public static final BasicParticleType ITEM_SLIME = ParticleTypes.register("item_slime", false);
    public static final BasicParticleType ITEM_SNOWBALL = ParticleTypes.register("item_snowball", false);
    public static final BasicParticleType LARGE_SMOKE = ParticleTypes.register("large_smoke", false);
    public static final BasicParticleType LAVA = ParticleTypes.register("lava", false);
    public static final BasicParticleType MYCELIUM = ParticleTypes.register("mycelium", false);
    public static final BasicParticleType NOTE = ParticleTypes.register("note", false);
    public static final BasicParticleType POOF = ParticleTypes.register("poof", true);
    public static final BasicParticleType PORTAL = ParticleTypes.register("portal", false);
    public static final BasicParticleType RAIN = ParticleTypes.register("rain", false);
    public static final BasicParticleType SMOKE = ParticleTypes.register("smoke", false);
    public static final BasicParticleType SNEEZE = ParticleTypes.register("sneeze", false);
    public static final BasicParticleType SPIT = ParticleTypes.register("spit", true);
    public static final BasicParticleType SQUID_INK = ParticleTypes.register("squid_ink", true);
    public static final BasicParticleType SWEEP_ATTACK = ParticleTypes.register("sweep_attack", true);
    public static final BasicParticleType TOTEM_OF_UNDYING = ParticleTypes.register("totem_of_undying", false);
    public static final BasicParticleType UNDERWATER = ParticleTypes.register("underwater", false);
    public static final BasicParticleType SPLASH = ParticleTypes.register("splash", false);
    public static final BasicParticleType WITCH = ParticleTypes.register("witch", false);
    public static final BasicParticleType BUBBLE_POP = ParticleTypes.register("bubble_pop", false);
    public static final BasicParticleType CURRENT_DOWN = ParticleTypes.register("current_down", false);
    public static final BasicParticleType BUBBLE_COLUMN_UP = ParticleTypes.register("bubble_column_up", false);
    public static final BasicParticleType NAUTILUS = ParticleTypes.register("nautilus", false);
    public static final BasicParticleType DOLPHIN = ParticleTypes.register("dolphin", false);
    public static final BasicParticleType CAMPFIRE_COSY_SMOKE = ParticleTypes.register("campfire_cosy_smoke", true);
    public static final BasicParticleType CAMPFIRE_SIGNAL_SMOKE = ParticleTypes.register("campfire_signal_smoke", true);
    public static final BasicParticleType DRIPPING_HONEY = ParticleTypes.register("dripping_honey", false);
    public static final BasicParticleType FALLING_HONEY = ParticleTypes.register("falling_honey", false);
    public static final BasicParticleType LANDING_HONEY = ParticleTypes.register("landing_honey", false);
    public static final BasicParticleType FALLING_NECTAR = ParticleTypes.register("falling_nectar", false);
    public static final BasicParticleType ASH = ParticleTypes.register("ash", false);
    public static final BasicParticleType CRIMSON_SPORE = ParticleTypes.register("crimson_spore", false);
    public static final BasicParticleType WARPED_SPORE = ParticleTypes.register("warped_spore", false);
    public static final BasicParticleType DRIPPING_OBSIDIAN_TEAR = ParticleTypes.register("dripping_obsidian_tear", false);
    public static final BasicParticleType FALLING_OBSIDIAN_TEAR = ParticleTypes.register("falling_obsidian_tear", false);
    public static final BasicParticleType LANDING_OBSIDIAN_TEAR = ParticleTypes.register("landing_obsidian_tear", false);
    public static final BasicParticleType REVERSE_PORTAL = ParticleTypes.register("reverse_portal", false);
    public static final BasicParticleType WHITE_ASH = ParticleTypes.register("white_ash", false);
    public static final Codec<IParticleData> CODEC = Registry.PARTICLE_TYPE.dispatch("type", IParticleData::getType, ParticleType::func_230522_e_);

    private static BasicParticleType register(String string, boolean bl) {
        return Registry.register(Registry.PARTICLE_TYPE, string, new BasicParticleType(bl));
    }

    private static <T extends IParticleData> ParticleType<T> register(String string, IParticleData.IDeserializer<T> iDeserializer, Function<ParticleType<T>, Codec<T>> function) {
        return Registry.register(Registry.PARTICLE_TYPE, string, new ParticleType<T>(false, iDeserializer, function){
            final Function val$p_218416_2_;
            {
                this.val$p_218416_2_ = function;
                super(bl, iDeserializer);
            }

            @Override
            public Codec<T> func_230522_e_() {
                return (Codec)this.val$p_218416_2_.apply(this);
            }
        });
    }

    private static Codec lambda$static$0(ParticleType particleType) {
        return RedstoneParticleData.field_239802_b_;
    }
}

