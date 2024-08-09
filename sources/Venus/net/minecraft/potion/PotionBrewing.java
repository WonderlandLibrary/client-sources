/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;

public class PotionBrewing {
    private static final List<MixPredicate<Potion>> POTION_TYPE_CONVERSIONS = Lists.newArrayList();
    private static final List<MixPredicate<Item>> POTION_ITEM_CONVERSIONS = Lists.newArrayList();
    private static final List<Ingredient> POTION_ITEMS = Lists.newArrayList();
    private static final Predicate<ItemStack> IS_POTION_ITEM = PotionBrewing::lambda$static$0;

    public static boolean isReagent(ItemStack itemStack) {
        return PotionBrewing.isItemConversionReagent(itemStack) || PotionBrewing.isTypeConversionReagent(itemStack);
    }

    protected static boolean isItemConversionReagent(ItemStack itemStack) {
        int n = POTION_ITEM_CONVERSIONS.size();
        for (int i = 0; i < n; ++i) {
            if (!PotionBrewing.POTION_ITEM_CONVERSIONS.get((int)i).reagent.test(itemStack)) continue;
            return false;
        }
        return true;
    }

    protected static boolean isTypeConversionReagent(ItemStack itemStack) {
        int n = POTION_TYPE_CONVERSIONS.size();
        for (int i = 0; i < n; ++i) {
            if (!PotionBrewing.POTION_TYPE_CONVERSIONS.get((int)i).reagent.test(itemStack)) continue;
            return false;
        }
        return true;
    }

    public static boolean isBrewablePotion(Potion potion) {
        int n = POTION_TYPE_CONVERSIONS.size();
        for (int i = 0; i < n; ++i) {
            if (PotionBrewing.POTION_TYPE_CONVERSIONS.get((int)i).output != potion) continue;
            return false;
        }
        return true;
    }

    public static boolean hasConversions(ItemStack itemStack, ItemStack itemStack2) {
        if (!IS_POTION_ITEM.test(itemStack)) {
            return true;
        }
        return PotionBrewing.hasItemConversions(itemStack, itemStack2) || PotionBrewing.hasTypeConversions(itemStack, itemStack2);
    }

    protected static boolean hasItemConversions(ItemStack itemStack, ItemStack itemStack2) {
        Item item = itemStack.getItem();
        int n = POTION_ITEM_CONVERSIONS.size();
        for (int i = 0; i < n; ++i) {
            MixPredicate<Item> mixPredicate = POTION_ITEM_CONVERSIONS.get(i);
            if (mixPredicate.input != item || !mixPredicate.reagent.test(itemStack2)) continue;
            return false;
        }
        return true;
    }

    protected static boolean hasTypeConversions(ItemStack itemStack, ItemStack itemStack2) {
        Potion potion = PotionUtils.getPotionFromItem(itemStack);
        int n = POTION_TYPE_CONVERSIONS.size();
        for (int i = 0; i < n; ++i) {
            MixPredicate<Potion> mixPredicate = POTION_TYPE_CONVERSIONS.get(i);
            if (mixPredicate.input != potion || !mixPredicate.reagent.test(itemStack2)) continue;
            return false;
        }
        return true;
    }

    public static ItemStack doReaction(ItemStack itemStack, ItemStack itemStack2) {
        if (!itemStack2.isEmpty()) {
            MixPredicate<Object> mixPredicate;
            int n;
            Potion potion = PotionUtils.getPotionFromItem(itemStack2);
            Item item = itemStack2.getItem();
            int n2 = POTION_ITEM_CONVERSIONS.size();
            for (n = 0; n < n2; ++n) {
                mixPredicate = POTION_ITEM_CONVERSIONS.get(n);
                if (mixPredicate.input != item || !mixPredicate.reagent.test(itemStack)) continue;
                return PotionUtils.addPotionToItemStack(new ItemStack((IItemProvider)mixPredicate.output), potion);
            }
            n2 = POTION_TYPE_CONVERSIONS.size();
            for (n = 0; n < n2; ++n) {
                mixPredicate = POTION_TYPE_CONVERSIONS.get(n);
                if (mixPredicate.input != potion || !mixPredicate.reagent.test(itemStack)) continue;
                return PotionUtils.addPotionToItemStack(new ItemStack(item), (Potion)mixPredicate.output);
            }
        }
        return itemStack2;
    }

    public static void init() {
        PotionBrewing.addContainer(Items.POTION);
        PotionBrewing.addContainer(Items.SPLASH_POTION);
        PotionBrewing.addContainer(Items.LINGERING_POTION);
        PotionBrewing.addContainerRecipe(Items.POTION, Items.GUNPOWDER, Items.SPLASH_POTION);
        PotionBrewing.addContainerRecipe(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION);
        PotionBrewing.addMix(Potions.WATER, Items.GLISTERING_MELON_SLICE, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.GHAST_TEAR, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.RABBIT_FOOT, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.BLAZE_POWDER, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.SPIDER_EYE, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.SUGAR, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.MAGMA_CREAM, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.GLOWSTONE_DUST, Potions.THICK);
        PotionBrewing.addMix(Potions.WATER, Items.REDSTONE, Potions.MUNDANE);
        PotionBrewing.addMix(Potions.WATER, Items.NETHER_WART, Potions.AWKWARD);
        PotionBrewing.addMix(Potions.AWKWARD, Items.GOLDEN_CARROT, Potions.NIGHT_VISION);
        PotionBrewing.addMix(Potions.NIGHT_VISION, Items.REDSTONE, Potions.LONG_NIGHT_VISION);
        PotionBrewing.addMix(Potions.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY);
        PotionBrewing.addMix(Potions.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY);
        PotionBrewing.addMix(Potions.INVISIBILITY, Items.REDSTONE, Potions.LONG_INVISIBILITY);
        PotionBrewing.addMix(Potions.AWKWARD, Items.MAGMA_CREAM, Potions.FIRE_RESISTANCE);
        PotionBrewing.addMix(Potions.FIRE_RESISTANCE, Items.REDSTONE, Potions.LONG_FIRE_RESISTANCE);
        PotionBrewing.addMix(Potions.AWKWARD, Items.RABBIT_FOOT, Potions.LEAPING);
        PotionBrewing.addMix(Potions.LEAPING, Items.REDSTONE, Potions.LONG_LEAPING);
        PotionBrewing.addMix(Potions.LEAPING, Items.GLOWSTONE_DUST, Potions.STRONG_LEAPING);
        PotionBrewing.addMix(Potions.LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
        PotionBrewing.addMix(Potions.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
        PotionBrewing.addMix(Potions.SLOWNESS, Items.REDSTONE, Potions.LONG_SLOWNESS);
        PotionBrewing.addMix(Potions.SLOWNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SLOWNESS);
        PotionBrewing.addMix(Potions.AWKWARD, Items.TURTLE_HELMET, Potions.TURTLE_MASTER);
        PotionBrewing.addMix(Potions.TURTLE_MASTER, Items.REDSTONE, Potions.LONG_TURTLE_MASTER);
        PotionBrewing.addMix(Potions.TURTLE_MASTER, Items.GLOWSTONE_DUST, Potions.STRONG_TURTLE_MASTER);
        PotionBrewing.addMix(Potions.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
        PotionBrewing.addMix(Potions.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
        PotionBrewing.addMix(Potions.AWKWARD, Items.SUGAR, Potions.SWIFTNESS);
        PotionBrewing.addMix(Potions.SWIFTNESS, Items.REDSTONE, Potions.LONG_SWIFTNESS);
        PotionBrewing.addMix(Potions.SWIFTNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SWIFTNESS);
        PotionBrewing.addMix(Potions.AWKWARD, Items.PUFFERFISH, Potions.WATER_BREATHING);
        PotionBrewing.addMix(Potions.WATER_BREATHING, Items.REDSTONE, Potions.LONG_WATER_BREATHING);
        PotionBrewing.addMix(Potions.AWKWARD, Items.GLISTERING_MELON_SLICE, Potions.HEALING);
        PotionBrewing.addMix(Potions.HEALING, Items.GLOWSTONE_DUST, Potions.STRONG_HEALING);
        PotionBrewing.addMix(Potions.HEALING, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
        PotionBrewing.addMix(Potions.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
        PotionBrewing.addMix(Potions.HARMING, Items.GLOWSTONE_DUST, Potions.STRONG_HARMING);
        PotionBrewing.addMix(Potions.POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
        PotionBrewing.addMix(Potions.LONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
        PotionBrewing.addMix(Potions.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
        PotionBrewing.addMix(Potions.AWKWARD, Items.SPIDER_EYE, Potions.POISON);
        PotionBrewing.addMix(Potions.POISON, Items.REDSTONE, Potions.LONG_POISON);
        PotionBrewing.addMix(Potions.POISON, Items.GLOWSTONE_DUST, Potions.STRONG_POISON);
        PotionBrewing.addMix(Potions.AWKWARD, Items.GHAST_TEAR, Potions.REGENERATION);
        PotionBrewing.addMix(Potions.REGENERATION, Items.REDSTONE, Potions.LONG_REGENERATION);
        PotionBrewing.addMix(Potions.REGENERATION, Items.GLOWSTONE_DUST, Potions.STRONG_REGENERATION);
        PotionBrewing.addMix(Potions.AWKWARD, Items.BLAZE_POWDER, Potions.STRENGTH);
        PotionBrewing.addMix(Potions.STRENGTH, Items.REDSTONE, Potions.LONG_STRENGTH);
        PotionBrewing.addMix(Potions.STRENGTH, Items.GLOWSTONE_DUST, Potions.STRONG_STRENGTH);
        PotionBrewing.addMix(Potions.WATER, Items.FERMENTED_SPIDER_EYE, Potions.WEAKNESS);
        PotionBrewing.addMix(Potions.WEAKNESS, Items.REDSTONE, Potions.LONG_WEAKNESS);
        PotionBrewing.addMix(Potions.AWKWARD, Items.PHANTOM_MEMBRANE, Potions.SLOW_FALLING);
        PotionBrewing.addMix(Potions.SLOW_FALLING, Items.REDSTONE, Potions.LONG_SLOW_FALLING);
    }

    private static void addContainerRecipe(Item item, Item item2, Item item3) {
        if (!(item instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registry.ITEM.getKey(item));
        }
        if (!(item3 instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registry.ITEM.getKey(item3));
        }
        POTION_ITEM_CONVERSIONS.add(new MixPredicate<Item>(item, Ingredient.fromItems(item2), item3));
    }

    private static void addContainer(Item item) {
        if (!(item instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registry.ITEM.getKey(item));
        }
        POTION_ITEMS.add(Ingredient.fromItems(item));
    }

    private static void addMix(Potion potion, Item item, Potion potion2) {
        POTION_TYPE_CONVERSIONS.add(new MixPredicate<Potion>(potion, Ingredient.fromItems(item), potion2));
    }

    private static boolean lambda$static$0(ItemStack itemStack) {
        for (Ingredient ingredient : POTION_ITEMS) {
            if (!ingredient.test(itemStack)) continue;
            return false;
        }
        return true;
    }

    static class MixPredicate<T> {
        private final T input;
        private final Ingredient reagent;
        private final T output;

        public MixPredicate(T t, Ingredient ingredient, T t2) {
            this.input = t;
            this.reagent = ingredient;
            this.output = t2;
        }
    }
}

