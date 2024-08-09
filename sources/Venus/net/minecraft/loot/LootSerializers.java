/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import net.minecraft.loot.BinomialRange;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IntClamper;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;

public class LootSerializers {
    public static GsonBuilder func_237386_a_() {
        return new GsonBuilder().registerTypeAdapter((Type)((Object)RandomValueRange.class), new RandomValueRange.Serializer()).registerTypeAdapter((Type)((Object)BinomialRange.class), new BinomialRange.Serializer()).registerTypeAdapter((Type)((Object)ConstantRange.class), new ConstantRange.Serializer()).registerTypeHierarchyAdapter(ILootCondition.class, LootConditionManager.func_237474_a_()).registerTypeHierarchyAdapter(LootContext.EntityTarget.class, new LootContext.EntityTarget.Serializer());
    }

    public static GsonBuilder func_237387_b_() {
        return LootSerializers.func_237386_a_().registerTypeAdapter((Type)((Object)IntClamper.class), new IntClamper.Serializer()).registerTypeHierarchyAdapter(LootEntry.class, LootEntryManager.func_237418_a_()).registerTypeHierarchyAdapter(ILootFunction.class, LootFunctionManager.func_237450_a_());
    }

    public static GsonBuilder func_237388_c_() {
        return LootSerializers.func_237387_b_().registerTypeAdapter((Type)((Object)LootPool.class), new LootPool.Serializer()).registerTypeAdapter((Type)((Object)LootTable.class), new LootTable.Serializer());
    }
}

