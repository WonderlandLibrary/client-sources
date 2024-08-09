package net.minecraft.loot;

import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public interface ILootGenerator
{
    /**
     * Gets the effective weight based on the loot entry's weight and quality multiplied by looter's luck.
     */
    int getEffectiveWeight(float luck);

    void func_216188_a(Consumer<ItemStack> p_216188_1_, LootContext p_216188_2_);
}
