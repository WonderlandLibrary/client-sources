package xyz.mathax.mathaxclient.utils.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

public enum MyPotion {
    Swiftness("Swiftness", Potions.SWIFTNESS, Items.NETHER_WART, Items.SUGAR),
    Swiftness_Long("Swiftness Long", Potions.LONG_SWIFTNESS, Items.NETHER_WART, Items.SUGAR, Items.REDSTONE),
    Swiftness_Strong("Swiftness Strong", Potions.STRONG_SWIFTNESS, Items.NETHER_WART, Items.SUGAR, Items.GLOWSTONE_DUST),

    Slowness("Slowness", Potions.SLOWNESS, Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE),
    Slowness_Long("Slowness Long", Potions.LONG_SLOWNESS, Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE),
    Slowness_Strong("Slowness Strong", Potions.STRONG_SLOWNESS, Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE, Items.GLOWSTONE_DUST),

    Jump_Boost("Jump Boost", Potions.LEAPING, Items.NETHER_WART, Items.RABBIT_FOOT),
    Jump_Boost_Long("Jump Boost Long", Potions.LONG_LEAPING, Items.NETHER_WART, Items.RABBIT_FOOT, Items.REDSTONE),
    Jump_Boost_Strong("Jump Boost Strong", Potions.STRONG_LEAPING, Items.NETHER_WART, Items.RABBIT_FOOT, Items.GLOWSTONE_DUST),

    Strength("Strength", Potions.STRENGTH, Items.NETHER_WART, Items.BLAZE_POWDER),
    Strength_Long("Strength Long", Potions.LONG_STRENGTH, Items.NETHER_WART, Items.BLAZE_POWDER, Items.REDSTONE),
    Strength_Strong("Strength Strong", Potions.STRONG_STRENGTH, Items.NETHER_WART, Items.BLAZE_POWDER, Items.GLOWSTONE_DUST),

    Healing("Healing", Potions.HEALING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE),
    Healing_Strong("Healing Strong", Potions.STRONG_HEALING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE, Items.GLOWSTONE_DUST),

    Harming("Harming", Potions.HARMING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE, Items.FERMENTED_SPIDER_EYE),
    Harming_Strong("Harming Strong", Potions.STRONG_HARMING, Items.NETHER_WART, Items.GLISTERING_MELON_SLICE, Items.FERMENTED_SPIDER_EYE, Items.GLOWSTONE_DUST),

    Poison("Poison", Potions.POISON, Items.NETHER_WART, Items.SPIDER_EYE),
    Poison_Long("Poison Long", Potions.LONG_POISON, Items.NETHER_WART, Items.SPIDER_EYE, Items.REDSTONE),
    Poison_Strong("Poison Strong", Potions.STRONG_POISON, Items.NETHER_WART, Items.SPIDER_EYE, Items.GLOWSTONE_DUST),

    Regeneration("Regeneration", Potions.REGENERATION, Items.NETHER_WART, Items.GHAST_TEAR),
    Regeneration_Long("Regeneration Long", Potions.LONG_REGENERATION, Items.NETHER_WART, Items.GHAST_TEAR, Items.REDSTONE),
    Regeneration_Strong("Regeneration Strong", Potions.STRONG_REGENERATION, Items.NETHER_WART, Items.GHAST_TEAR, Items.GLOWSTONE_DUST),

    Fire_Resistance("Fire Resistance", Potions.FIRE_RESISTANCE, Items.NETHER_WART, Items.MAGMA_CREAM),
    Fire_Resistance_Long("Fire Resistance Long", Potions.LONG_FIRE_RESISTANCE, Items.NETHER_WART, Items.MAGMA_CREAM, Items.REDSTONE),

    Water_Breathing("Water Breathing", Potions.WATER_BREATHING, Items.NETHER_WART, Items.PUFFERFISH),
    Water_Breathing_Long("Water Breathing Long", Potions.LONG_WATER_BREATHING, Items.NETHER_WART, Items.PUFFERFISH, Items.REDSTONE),

    Night_Vision("", Potions.NIGHT_VISION, Items.NETHER_WART, Items.GOLDEN_CARROT),
    Night_Vision_Long("", Potions.LONG_NIGHT_VISION, Items.NETHER_WART, Items.GOLDEN_CARROT, Items.REDSTONE),

    Invisibility("Invisibility", Potions.INVISIBILITY, Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE),
    Invisibility_Long("Invisibility Long", Potions.LONG_INVISIBILITY, Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE),

    Turtle_Master("Turtle Master", Potions.TURTLE_MASTER, Items.NETHER_WART, Items.TURTLE_HELMET),
    Turtle_Master_Long("Turtle Master Long", Potions.LONG_TURTLE_MASTER, Items.NETHER_WART, Items.TURTLE_HELMET, Items.REDSTONE),
    Turtle_Master_Strong("Turtle Master Strong", Potions.STRONG_TURTLE_MASTER, Items.NETHER_WART, Items.TURTLE_HELMET, Items.GLOWSTONE_DUST),

    Slow_Falling("Slow Falling", Potions.SLOW_FALLING, Items.NETHER_WART, Items.PHANTOM_MEMBRANE),
    Slow_Falling_Long("Slow Falling Long", Potions.LONG_SLOW_FALLING, Items.NETHER_WART, Items.PHANTOM_MEMBRANE, Items.REDSTONE),

    Weakness("Weakness", Potions.WEAKNESS, Items.FERMENTED_SPIDER_EYE),
    Weakness_Long("Weakness Long", Potions.LONG_WEAKNESS, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE);

    private final String name;

    public final ItemStack potion;

    public final Item[] ingredients;

    MyPotion(String name, Potion potion, Item... ingredients) {
        this.name = name;
        this.potion = PotionUtil.setPotion(new ItemStack(Items.POTION), potion);
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return name;
    }
}
