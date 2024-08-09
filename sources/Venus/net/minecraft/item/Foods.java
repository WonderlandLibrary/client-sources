/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Foods {
    public static final Food APPLE = new Food.Builder().hunger(4).saturation(0.3f).build();
    public static final Food BAKED_POTATO = new Food.Builder().hunger(5).saturation(0.6f).build();
    public static final Food BEEF = new Food.Builder().hunger(3).saturation(0.3f).meat().build();
    public static final Food BEETROOT = new Food.Builder().hunger(1).saturation(0.6f).build();
    public static final Food BEETROOT_SOUP = Foods.buildStew(6);
    public static final Food BREAD = new Food.Builder().hunger(5).saturation(0.6f).build();
    public static final Food CARROT = new Food.Builder().hunger(3).saturation(0.6f).build();
    public static final Food CHICKEN = new Food.Builder().hunger(2).saturation(0.3f).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3f).meat().build();
    public static final Food CHORUS_FRUIT = new Food.Builder().hunger(4).saturation(0.3f).setAlwaysEdible().build();
    public static final Food COD = new Food.Builder().hunger(2).saturation(0.1f).build();
    public static final Food COOKED_BEEF = new Food.Builder().hunger(8).saturation(0.8f).meat().build();
    public static final Food COOKED_CHICKEN = new Food.Builder().hunger(6).saturation(0.6f).meat().build();
    public static final Food COOKED_COD = new Food.Builder().hunger(5).saturation(0.6f).build();
    public static final Food COOKED_MUTTON = new Food.Builder().hunger(6).saturation(0.8f).meat().build();
    public static final Food COOKED_PORKCHOP = new Food.Builder().hunger(8).saturation(0.8f).meat().build();
    public static final Food COOKED_RABBIT = new Food.Builder().hunger(5).saturation(0.6f).meat().build();
    public static final Food COOKED_SALMON = new Food.Builder().hunger(6).saturation(0.8f).build();
    public static final Food COOKIE = new Food.Builder().hunger(2).saturation(0.1f).build();
    public static final Food DRIED_KELP = new Food.Builder().hunger(1).saturation(0.3f).fastToEat().build();
    public static final Food ENCHANTED_GOLDEN_APPLE = new Food.Builder().hunger(4).saturation(1.2f).effect(new EffectInstance(Effects.REGENERATION, 400, 1), 1.0f).effect(new EffectInstance(Effects.RESISTANCE, 6000, 0), 1.0f).effect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0), 1.0f).effect(new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0f).setAlwaysEdible().build();
    public static final Food GOLDEN_APPLE = new Food.Builder().hunger(4).saturation(1.2f).effect(new EffectInstance(Effects.REGENERATION, 100, 1), 1.0f).effect(new EffectInstance(Effects.ABSORPTION, 2400, 0), 1.0f).setAlwaysEdible().build();
    public static final Food GOLDEN_CARROT = new Food.Builder().hunger(6).saturation(1.2f).build();
    public static final Food HONEY = new Food.Builder().hunger(6).saturation(0.1f).build();
    public static final Food MELON_SLICE = new Food.Builder().hunger(2).saturation(0.3f).build();
    public static final Food MUSHROOM_STEW = Foods.buildStew(6);
    public static final Food MUTTON = new Food.Builder().hunger(2).saturation(0.3f).meat().build();
    public static final Food POISONOUS_POTATO = new Food.Builder().hunger(2).saturation(0.3f).effect(new EffectInstance(Effects.POISON, 100, 0), 0.6f).build();
    public static final Food PORKCHOP = new Food.Builder().hunger(3).saturation(0.3f).meat().build();
    public static final Food POTATO = new Food.Builder().hunger(1).saturation(0.3f).build();
    public static final Food PUFFERFISH = new Food.Builder().hunger(1).saturation(0.1f).effect(new EffectInstance(Effects.POISON, 1200, 3), 1.0f).effect(new EffectInstance(Effects.HUNGER, 300, 2), 1.0f).effect(new EffectInstance(Effects.NAUSEA, 300, 0), 1.0f).build();
    public static final Food PUMPKIN_PIE = new Food.Builder().hunger(8).saturation(0.3f).build();
    public static final Food RABBIT = new Food.Builder().hunger(3).saturation(0.3f).meat().build();
    public static final Food RABBIT_STEW = Foods.buildStew(10);
    public static final Food ROTTEN_FLESH = new Food.Builder().hunger(4).saturation(0.1f).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8f).meat().build();
    public static final Food SALMON = new Food.Builder().hunger(2).saturation(0.1f).build();
    public static final Food SPIDER_EYE = new Food.Builder().hunger(2).saturation(0.8f).effect(new EffectInstance(Effects.POISON, 100, 0), 1.0f).build();
    public static final Food SUSPICIOUS_STEW = Foods.buildStew(6);
    public static final Food SWEET_BERRIES = new Food.Builder().hunger(2).saturation(0.1f).build();
    public static final Food TROPICAL_FISH = new Food.Builder().hunger(1).saturation(0.1f).build();

    private static Food buildStew(int n) {
        return new Food.Builder().hunger(n).saturation(0.6f).build();
    }
}

