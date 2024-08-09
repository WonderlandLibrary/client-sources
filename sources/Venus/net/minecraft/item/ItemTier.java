/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.function.Supplier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

public enum ItemTier implements IItemTier
{
    WOOD(0, 59, 2.0f, 0.0f, 15, ItemTier::lambda$static$0),
    STONE(1, 131, 4.0f, 1.0f, 5, ItemTier::lambda$static$1),
    IRON(2, 250, 6.0f, 2.0f, 14, ItemTier::lambda$static$2),
    DIAMOND(3, 1561, 8.0f, 3.0f, 10, ItemTier::lambda$static$3),
    GOLD(0, 32, 12.0f, 0.0f, 22, ItemTier::lambda$static$4),
    NETHERITE(4, 2031, 9.0f, 4.0f, 15, ItemTier::lambda$static$5);

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private ItemTier(int n2, int n3, float f, float f2, int n4, Supplier<Ingredient> supplier) {
        this.harvestLevel = n2;
        this.maxUses = n3;
        this.efficiency = f;
        this.attackDamage = f2;
        this.enchantability = n4;
        this.repairMaterial = new LazyValue<Ingredient>(supplier);
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    private static Ingredient lambda$static$5() {
        return Ingredient.fromItems(Items.NETHERITE_INGOT);
    }

    private static Ingredient lambda$static$4() {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }

    private static Ingredient lambda$static$3() {
        return Ingredient.fromItems(Items.DIAMOND);
    }

    private static Ingredient lambda$static$2() {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }

    private static Ingredient lambda$static$1() {
        return Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS);
    }

    private static Ingredient lambda$static$0() {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }
}

