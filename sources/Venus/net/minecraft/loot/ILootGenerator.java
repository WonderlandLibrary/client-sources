/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

public interface ILootGenerator {
    public int getEffectiveWeight(float var1);

    public void func_216188_a(Consumer<ItemStack> var1, LootContext var2);
}

