/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.functions.EnchantRandomly;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class PiglinBarteringAddition
implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        biConsumer.accept(LootTables.PIGLIN_BARTERING, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.BOOK).weight(5)).acceptFunction(new EnchantRandomly.Builder().func_237424_a_(Enchantments.SOUL_SPEED))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.IRON_BOOTS).weight(8)).acceptFunction(new EnchantRandomly.Builder().func_237424_a_(Enchantments.SOUL_SPEED))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.POTION).weight(8)).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), PiglinBarteringAddition::lambda$accept$0)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SPLASH_POTION).weight(8)).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), PiglinBarteringAddition::lambda$accept$1)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.POTION).weight(10)).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), PiglinBarteringAddition::lambda$accept$2)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.IRON_NUGGET).weight(10)).acceptFunction(SetCount.builder(RandomValueRange.of(10.0f, 36.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.ENDER_PEARL).weight(10)).acceptFunction(SetCount.builder(RandomValueRange.of(2.0f, 4.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.STRING).weight(20)).acceptFunction(SetCount.builder(RandomValueRange.of(3.0f, 9.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.QUARTZ).weight(20)).acceptFunction(SetCount.builder(RandomValueRange.of(5.0f, 12.0f)))).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.OBSIDIAN).weight(40)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.CRYING_OBSIDIAN).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(1.0f, 3.0f)))).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.FIRE_CHARGE).weight(40)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.LEATHER).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(2.0f, 4.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SOUL_SAND).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(2.0f, 8.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.NETHER_BRICK).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(2.0f, 8.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.SPECTRAL_ARROW).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(6.0f, 12.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.GRAVEL).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(8.0f, 16.0f)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.BLACKSTONE).weight(40)).acceptFunction(SetCount.builder(RandomValueRange.of(8.0f, 16.0f))))));
    }

    @Override
    public void accept(Object object) {
        this.accept((BiConsumer)object);
    }

    private static void lambda$accept$2(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:water");
    }

    private static void lambda$accept$1(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:fire_resistance");
    }

    private static void lambda$accept$0(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:fire_resistance");
    }
}

