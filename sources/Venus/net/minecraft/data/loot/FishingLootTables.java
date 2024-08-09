/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LocationCheck;
import net.minecraft.loot.functions.EnchantWithLevels;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biomes;

public class FishingLootTables
implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    public static final ILootCondition.IBuilder IN_JUNGLE = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.JUNGLE));
    public static final ILootCondition.IBuilder IN_JUNGLE_HILLS = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.JUNGLE_HILLS));
    public static final ILootCondition.IBuilder IN_JUNGLE_EDGE = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.JUNGLE_EDGE));
    public static final ILootCondition.IBuilder IN_BAMBOO_JUNGLE = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.BAMBOO_JUNGLE));
    public static final ILootCondition.IBuilder IN_MODIFIED_JUNGLE = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.MODIFIED_JUNGLE));
    public static final ILootCondition.IBuilder IN_MODIFIED_JUNGLE_EDGE = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.MODIFIED_JUNGLE_EDGE));
    public static final ILootCondition.IBuilder IN_BAMBOO_JUNGLE_HILLS = LocationCheck.builder(LocationPredicate.Builder.builder().biome(Biomes.BAMBOO_JUNGLE_HILLS));

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        biConsumer.accept(LootTables.GAMEPLAY_FISHING, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_JUNK).weight(10)).quality(-2)).addEntry((LootEntry.Builder<?>)((LootEntry.Builder)((StandaloneLootEntry.Builder)TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_TREASURE).weight(5)).quality(2)).acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().fishing(FishingPredicate.func_234640_a_(true))))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_FISH).weight(85)).quality(-1))));
        biConsumer.accept(LootTables.GAMEPLAY_FISHING_FISH, LootTable.builder().addLootPool(LootPool.builder().addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.COD).weight(60)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.SALMON).weight(25)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.TROPICAL_FISH).weight(2)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.PUFFERFISH).weight(13))));
        biConsumer.accept(LootTables.GAMEPLAY_FISHING_JUNK, LootTable.builder().addLootPool(LootPool.builder().addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Blocks.LILY_PAD).weight(17)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.LEATHER_BOOTS).weight(10)).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0f, 0.9f)))).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.LEATHER).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.BONE).weight(10)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.POTION).weight(10)).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), FishingLootTables::lambda$accept$0)))).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.STRING).weight(5)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.FISHING_ROD).weight(2)).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0f, 0.9f)))).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.BOWL).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.STICK).weight(5)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.INK_SAC).weight(1)).acceptFunction(SetCount.builder(ConstantRange.of(10)))).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Blocks.TRIPWIRE_HOOK).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.ROTTEN_FLESH).weight(10)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Blocks.BAMBOO).acceptCondition(IN_JUNGLE.alternative(IN_JUNGLE_HILLS).alternative(IN_JUNGLE_EDGE).alternative(IN_BAMBOO_JUNGLE).alternative(IN_MODIFIED_JUNGLE).alternative(IN_MODIFIED_JUNGLE_EDGE).alternative(IN_BAMBOO_JUNGLE_HILLS))).weight(10))));
        biConsumer.accept(LootTables.GAMEPLAY_FISHING_TREASURE, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Items.NAME_TAG)).addEntry(ItemLootEntry.builder(Items.SADDLE)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.BOW).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0f, 0.25f)))).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30)).func_216059_e())).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.FISHING_ROD).acceptFunction(SetDamage.func_215931_a(RandomValueRange.of(0.0f, 0.25f)))).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30)).func_216059_e())).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.BOOK).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30)).func_216059_e())).addEntry(ItemLootEntry.builder(Items.NAUTILUS_SHELL))));
    }

    @Override
    public void accept(Object object) {
        this.accept((BiConsumer)object);
    }

    private static void lambda$accept$0(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:water");
    }
}

