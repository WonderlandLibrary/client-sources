/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.DynamicLootEntry;
import net.minecraft.loot.EmptyLootEntry;
import net.minecraft.loot.GroupLootEntry;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.LootTypesManager;
import net.minecraft.loot.ParentedLootEntry;
import net.minecraft.loot.SequenceLootEntry;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.TagLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootEntryManager {
    public static final LootPoolEntryType EMPTY = LootEntryManager.register("empty", new EmptyLootEntry.Serializer());
    public static final LootPoolEntryType ITEM = LootEntryManager.register("item", new ItemLootEntry.Serializer());
    public static final LootPoolEntryType LOOT_TABLE = LootEntryManager.register("loot_table", new TableLootEntry.Serializer());
    public static final LootPoolEntryType DYNAMIC = LootEntryManager.register("dynamic", new DynamicLootEntry.Serializer());
    public static final LootPoolEntryType TAG = LootEntryManager.register("tag", new TagLootEntry.Serializer());
    public static final LootPoolEntryType ALTERNATIVE = LootEntryManager.register("alternatives", ParentedLootEntry.getSerializer(AlternativesLootEntry::new));
    public static final LootPoolEntryType SEQUENCE = LootEntryManager.register("sequence", ParentedLootEntry.getSerializer(SequenceLootEntry::new));
    public static final LootPoolEntryType GROUP = LootEntryManager.register("group", ParentedLootEntry.getSerializer(GroupLootEntry::new));

    private static LootPoolEntryType register(String string, ILootSerializer<? extends LootEntry> iLootSerializer) {
        return Registry.register(Registry.LOOT_POOL_ENTRY_TYPE, new ResourceLocation(string), new LootPoolEntryType(iLootSerializer));
    }

    public static Object func_237418_a_() {
        return LootTypesManager.getLootTypeRegistryWrapper(Registry.LOOT_POOL_ENTRY_TYPE, "entry", "type", LootEntry::func_230420_a_).getSerializer();
    }
}

