/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class GiftLootTables
implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        biConsumer.accept(LootTables.GAMEPLAY_CAT_MORNING_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.RABBIT_HIDE).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.RABBIT_FOOT).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.CHICKEN).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.FEATHER).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.ROTTEN_FLESH).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.STRING).weight(10)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.PHANTOM_MEMBRANE).weight(2))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_ARMORER_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.CHAINMAIL_HELMET)).addEntry(ItemLootEntry.builder(Items.CHAINMAIL_CHESTPLATE)).addEntry(ItemLootEntry.builder(Items.CHAINMAIL_LEGGINGS)).addEntry(ItemLootEntry.builder(Items.CHAINMAIL_BOOTS))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_BUTCHER_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COOKED_RABBIT)).addEntry(ItemLootEntry.builder(Items.COOKED_CHICKEN)).addEntry(ItemLootEntry.builder(Items.COOKED_PORKCHOP)).addEntry(ItemLootEntry.builder(Items.COOKED_BEEF)).addEntry(ItemLootEntry.builder(Items.COOKED_MUTTON))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.MAP)).addEntry(ItemLootEntry.builder(Items.PAPER))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CLERIC_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.REDSTONE)).addEntry(ItemLootEntry.builder(Items.LAPIS_LAZULI))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FARMER_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BREAD)).addEntry(ItemLootEntry.builder(Items.PUMPKIN_PIE)).addEntry(ItemLootEntry.builder(Items.COOKIE))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FISHERMAN_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.COD)).addEntry(ItemLootEntry.builder(Items.SALMON))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FLETCHER_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry((LootEntry.Builder<?>)ItemLootEntry.builder(Items.ARROW).weight(26)).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$0)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$1)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$2)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$3)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$4)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$5)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$6)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$7)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$8)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$9)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$10)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$11)))).addEntry((LootEntry.Builder<?>)((StandaloneLootEntry.Builder)ItemLootEntry.builder(Items.TIPPED_ARROW).acceptFunction(SetCount.builder(RandomValueRange.of(0.0f, 1.0f)))).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), GiftLootTables::lambda$accept$12))))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.LEATHER))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.BOOK))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_MASON_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.CLAY))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_SHEPHERD_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.WHITE_WOOL)).addEntry(ItemLootEntry.builder(Items.ORANGE_WOOL)).addEntry(ItemLootEntry.builder(Items.MAGENTA_WOOL)).addEntry(ItemLootEntry.builder(Items.LIGHT_BLUE_WOOL)).addEntry(ItemLootEntry.builder(Items.YELLOW_WOOL)).addEntry(ItemLootEntry.builder(Items.LIME_WOOL)).addEntry(ItemLootEntry.builder(Items.PINK_WOOL)).addEntry(ItemLootEntry.builder(Items.GRAY_WOOL)).addEntry(ItemLootEntry.builder(Items.LIGHT_GRAY_WOOL)).addEntry(ItemLootEntry.builder(Items.CYAN_WOOL)).addEntry(ItemLootEntry.builder(Items.PURPLE_WOOL)).addEntry(ItemLootEntry.builder(Items.BLUE_WOOL)).addEntry(ItemLootEntry.builder(Items.BROWN_WOOL)).addEntry(ItemLootEntry.builder(Items.GREEN_WOOL)).addEntry(ItemLootEntry.builder(Items.RED_WOOL)).addEntry(ItemLootEntry.builder(Items.BLACK_WOOL))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.STONE_PICKAXE)).addEntry(ItemLootEntry.builder(Items.STONE_AXE)).addEntry(ItemLootEntry.builder(Items.STONE_HOE)).addEntry(ItemLootEntry.builder(Items.STONE_SHOVEL))));
        biConsumer.accept(LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT, LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.STONE_AXE)).addEntry(ItemLootEntry.builder(Items.GOLDEN_AXE)).addEntry(ItemLootEntry.builder(Items.IRON_AXE))));
    }

    @Override
    public void accept(Object object) {
        this.accept((BiConsumer)object);
    }

    private static void lambda$accept$12(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:poison");
    }

    private static void lambda$accept$11(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:weakness");
    }

    private static void lambda$accept$10(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:night_vision");
    }

    private static void lambda$accept$9(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:invisibility");
    }

    private static void lambda$accept$8(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:water_breathing");
    }

    private static void lambda$accept$7(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:fire_resistance");
    }

    private static void lambda$accept$6(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:regeneration");
    }

    private static void lambda$accept$5(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:leaping");
    }

    private static void lambda$accept$4(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:harming");
    }

    private static void lambda$accept$3(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:healing");
    }

    private static void lambda$accept$2(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:strength");
    }

    private static void lambda$accept$1(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:slowness");
    }

    private static void lambda$accept$0(CompoundNBT compoundNBT) {
        compoundNBT.putString("Potion", "minecraft:swiftness");
    }
}

