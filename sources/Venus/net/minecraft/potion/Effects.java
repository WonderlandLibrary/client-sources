/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.AbsorptionEffect;
import net.minecraft.potion.AttackDamageEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.HealthBoostEffect;
import net.minecraft.potion.InstantEffect;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.server.ServerWorld;

public class Effects {
    public static final Effect SPEED = Effects.register(1, "speed", new Effect(EffectType.BENEFICIAL, 8171462).addAttributesModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final Effect SLOWNESS = Effects.register(2, "slowness", new Effect(EffectType.HARMFUL, 5926017).addAttributesModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final Effect HASTE = Effects.register(3, "haste", new Effect(EffectType.BENEFICIAL, 14270531).addAttributesModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final Effect MINING_FATIGUE = Effects.register(4, "mining_fatigue", new Effect(EffectType.HARMFUL, 4866583).addAttributesModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final Effect STRENGTH = Effects.register(5, "strength", new AttackDamageEffect(EffectType.BENEFICIAL, 9643043, 3.0).addAttributesModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0, AttributeModifier.Operation.ADDITION));
    public static final Effect INSTANT_HEALTH = Effects.register(6, "instant_health", new InstantEffect(EffectType.BENEFICIAL, 16262179));
    public static final Effect INSTANT_DAMAGE = Effects.register(7, "instant_damage", new InstantEffect(EffectType.HARMFUL, 4393481));
    public static final Effect JUMP_BOOST = Effects.register(8, "jump_boost", new Effect(EffectType.BENEFICIAL, 2293580));
    public static final Effect NAUSEA = Effects.register(9, "nausea", new Effect(EffectType.HARMFUL, 5578058));
    public static final Effect REGENERATION = Effects.register(10, "regeneration", new Effect(EffectType.BENEFICIAL, 13458603));
    public static final Effect RESISTANCE = Effects.register(11, "resistance", new Effect(EffectType.BENEFICIAL, 10044730));
    public static final Effect FIRE_RESISTANCE = Effects.register(12, "fire_resistance", new Effect(EffectType.BENEFICIAL, 14981690));
    public static final Effect WATER_BREATHING = Effects.register(13, "water_breathing", new Effect(EffectType.BENEFICIAL, 3035801));
    public static final Effect INVISIBILITY = Effects.register(14, "invisibility", new Effect(EffectType.BENEFICIAL, 8356754));
    public static final Effect BLINDNESS = Effects.register(15, "blindness", new Effect(EffectType.HARMFUL, 2039587));
    public static final Effect NIGHT_VISION = Effects.register(16, "night_vision", new Effect(EffectType.BENEFICIAL, 0x1F1FA1));
    public static final Effect HUNGER = Effects.register(17, "hunger", new Effect(EffectType.HARMFUL, 5797459));
    public static final Effect WEAKNESS = Effects.register(18, "weakness", new AttackDamageEffect(EffectType.HARMFUL, 0x484D48, -4.0).addAttributesModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0, AttributeModifier.Operation.ADDITION));
    public static final Effect POISON = Effects.register(19, "poison", new Effect(EffectType.HARMFUL, 5149489));
    public static final Effect WITHER = Effects.register(20, "wither", new Effect(EffectType.HARMFUL, 3484199));
    public static final Effect HEALTH_BOOST = Effects.register(21, "health_boost", new HealthBoostEffect(EffectType.BENEFICIAL, 16284963).addAttributesModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, AttributeModifier.Operation.ADDITION));
    public static final Effect ABSORPTION = Effects.register(22, "absorption", new AbsorptionEffect(EffectType.BENEFICIAL, 0x2552A5));
    public static final Effect SATURATION = Effects.register(23, "saturation", new InstantEffect(EffectType.BENEFICIAL, 16262179));
    public static final Effect GLOWING = Effects.register(24, "glowing", new Effect(EffectType.NEUTRAL, 9740385));
    public static final Effect LEVITATION = Effects.register(25, "levitation", new Effect(EffectType.HARMFUL, 0xCEFFFF));
    public static final Effect LUCK = Effects.register(26, "luck", new Effect(EffectType.BENEFICIAL, 0x339900).addAttributesModifier(Attributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0, AttributeModifier.Operation.ADDITION));
    public static final Effect UNLUCK = Effects.register(27, "unluck", new Effect(EffectType.HARMFUL, 12624973).addAttributesModifier(Attributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0, AttributeModifier.Operation.ADDITION));
    public static final Effect SLOW_FALLING = Effects.register(28, "slow_falling", new Effect(EffectType.BENEFICIAL, 16773073));
    public static final Effect CONDUIT_POWER = Effects.register(29, "conduit_power", new Effect(EffectType.BENEFICIAL, 1950417));
    public static final Effect DOLPHINS_GRACE = Effects.register(30, "dolphins_grace", new Effect(EffectType.BENEFICIAL, 8954814));
    public static final Effect BAD_OMEN = Effects.register(31, "bad_omen", new Effect(EffectType.NEUTRAL, 745784){

        @Override
        public boolean isReady(int n, int n2) {
            return false;
        }

        @Override
        public void performEffect(LivingEntity livingEntity, int n) {
            if (livingEntity instanceof ServerPlayerEntity && !livingEntity.isSpectator()) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)livingEntity;
                ServerWorld serverWorld = serverPlayerEntity.getServerWorld();
                if (serverWorld.getDifficulty() == Difficulty.PEACEFUL) {
                    return;
                }
                if (serverWorld.isVillage(livingEntity.getPosition())) {
                    serverWorld.getRaids().badOmenTick(serverPlayerEntity);
                }
            }
        }
    });
    public static final Effect HERO_OF_THE_VILLAGE = Effects.register(32, "hero_of_the_village", new Effect(EffectType.BENEFICIAL, 0x44FF44));

    private static Effect register(int n, String string, Effect effect) {
        return Registry.register(Registry.EFFECTS, n, string, effect);
    }
}

