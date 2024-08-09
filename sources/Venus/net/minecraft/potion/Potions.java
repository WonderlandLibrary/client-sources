/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.registry.Registry;

public class Potions {
    public static final Potion EMPTY = Potions.register("empty", new Potion(new EffectInstance[0]));
    public static final Potion WATER = Potions.register("water", new Potion(new EffectInstance[0]));
    public static final Potion MUNDANE = Potions.register("mundane", new Potion(new EffectInstance[0]));
    public static final Potion THICK = Potions.register("thick", new Potion(new EffectInstance[0]));
    public static final Potion AWKWARD = Potions.register("awkward", new Potion(new EffectInstance[0]));
    public static final Potion NIGHT_VISION = Potions.register("night_vision", new Potion(new EffectInstance(Effects.NIGHT_VISION, 3600)));
    public static final Potion LONG_NIGHT_VISION = Potions.register("long_night_vision", new Potion("night_vision", new EffectInstance(Effects.NIGHT_VISION, 9600)));
    public static final Potion INVISIBILITY = Potions.register("invisibility", new Potion(new EffectInstance(Effects.INVISIBILITY, 3600)));
    public static final Potion LONG_INVISIBILITY = Potions.register("long_invisibility", new Potion("invisibility", new EffectInstance(Effects.INVISIBILITY, 9600)));
    public static final Potion LEAPING = Potions.register("leaping", new Potion(new EffectInstance(Effects.JUMP_BOOST, 3600)));
    public static final Potion LONG_LEAPING = Potions.register("long_leaping", new Potion("leaping", new EffectInstance(Effects.JUMP_BOOST, 9600)));
    public static final Potion STRONG_LEAPING = Potions.register("strong_leaping", new Potion("leaping", new EffectInstance(Effects.JUMP_BOOST, 1800, 1)));
    public static final Potion FIRE_RESISTANCE = Potions.register("fire_resistance", new Potion(new EffectInstance(Effects.FIRE_RESISTANCE, 3600)));
    public static final Potion LONG_FIRE_RESISTANCE = Potions.register("long_fire_resistance", new Potion("fire_resistance", new EffectInstance(Effects.FIRE_RESISTANCE, 9600)));
    public static final Potion SWIFTNESS = Potions.register("swiftness", new Potion(new EffectInstance(Effects.SPEED, 3600)));
    public static final Potion LONG_SWIFTNESS = Potions.register("long_swiftness", new Potion("swiftness", new EffectInstance(Effects.SPEED, 9600)));
    public static final Potion STRONG_SWIFTNESS = Potions.register("strong_swiftness", new Potion("swiftness", new EffectInstance(Effects.SPEED, 1800, 1)));
    public static final Potion SLOWNESS = Potions.register("slowness", new Potion(new EffectInstance(Effects.SLOWNESS, 1800)));
    public static final Potion LONG_SLOWNESS = Potions.register("long_slowness", new Potion("slowness", new EffectInstance(Effects.SLOWNESS, 4800)));
    public static final Potion STRONG_SLOWNESS = Potions.register("strong_slowness", new Potion("slowness", new EffectInstance(Effects.SLOWNESS, 400, 3)));
    public static final Potion TURTLE_MASTER = Potions.register("turtle_master", new Potion("turtle_master", new EffectInstance(Effects.SLOWNESS, 400, 3), new EffectInstance(Effects.RESISTANCE, 400, 2)));
    public static final Potion LONG_TURTLE_MASTER = Potions.register("long_turtle_master", new Potion("turtle_master", new EffectInstance(Effects.SLOWNESS, 800, 3), new EffectInstance(Effects.RESISTANCE, 800, 2)));
    public static final Potion STRONG_TURTLE_MASTER = Potions.register("strong_turtle_master", new Potion("turtle_master", new EffectInstance(Effects.SLOWNESS, 400, 5), new EffectInstance(Effects.RESISTANCE, 400, 3)));
    public static final Potion WATER_BREATHING = Potions.register("water_breathing", new Potion(new EffectInstance(Effects.WATER_BREATHING, 3600)));
    public static final Potion LONG_WATER_BREATHING = Potions.register("long_water_breathing", new Potion("water_breathing", new EffectInstance(Effects.WATER_BREATHING, 9600)));
    public static final Potion HEALING = Potions.register("healing", new Potion(new EffectInstance(Effects.INSTANT_HEALTH, 1)));
    public static final Potion STRONG_HEALING = Potions.register("strong_healing", new Potion("healing", new EffectInstance(Effects.INSTANT_HEALTH, 1, 1)));
    public static final Potion HARMING = Potions.register("harming", new Potion(new EffectInstance(Effects.INSTANT_DAMAGE, 1)));
    public static final Potion STRONG_HARMING = Potions.register("strong_harming", new Potion("harming", new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1)));
    public static final Potion POISON = Potions.register("poison", new Potion(new EffectInstance(Effects.POISON, 900)));
    public static final Potion LONG_POISON = Potions.register("long_poison", new Potion("poison", new EffectInstance(Effects.POISON, 1800)));
    public static final Potion STRONG_POISON = Potions.register("strong_poison", new Potion("poison", new EffectInstance(Effects.POISON, 432, 1)));
    public static final Potion REGENERATION = Potions.register("regeneration", new Potion(new EffectInstance(Effects.REGENERATION, 900)));
    public static final Potion LONG_REGENERATION = Potions.register("long_regeneration", new Potion("regeneration", new EffectInstance(Effects.REGENERATION, 1800)));
    public static final Potion STRONG_REGENERATION = Potions.register("strong_regeneration", new Potion("regeneration", new EffectInstance(Effects.REGENERATION, 450, 1)));
    public static final Potion STRENGTH = Potions.register("strength", new Potion(new EffectInstance(Effects.STRENGTH, 3600)));
    public static final Potion LONG_STRENGTH = Potions.register("long_strength", new Potion("strength", new EffectInstance(Effects.STRENGTH, 9600)));
    public static final Potion STRONG_STRENGTH = Potions.register("strong_strength", new Potion("strength", new EffectInstance(Effects.STRENGTH, 1800, 1)));
    public static final Potion WEAKNESS = Potions.register("weakness", new Potion(new EffectInstance(Effects.WEAKNESS, 1800)));
    public static final Potion LONG_WEAKNESS = Potions.register("long_weakness", new Potion("weakness", new EffectInstance(Effects.WEAKNESS, 4800)));
    public static final Potion LUCK = Potions.register("luck", new Potion("luck", new EffectInstance(Effects.LUCK, 6000)));
    public static final Potion SLOW_FALLING = Potions.register("slow_falling", new Potion(new EffectInstance(Effects.SLOW_FALLING, 1800)));
    public static final Potion LONG_SLOW_FALLING = Potions.register("long_slow_falling", new Potion("slow_falling", new EffectInstance(Effects.SLOW_FALLING, 4800)));

    private static Potion register(String string, Potion potion) {
        return Registry.register(Registry.POTION, string, potion);
    }
}

