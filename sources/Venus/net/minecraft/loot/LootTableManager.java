/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableManager
extends JsonReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON_INSTANCE = LootSerializers.func_237388_c_().create();
    private Map<ResourceLocation, LootTable> registeredLootTables = ImmutableMap.of();
    private final LootPredicateManager lootPredicateManager;

    public LootTableManager(LootPredicateManager lootPredicateManager) {
        super(GSON_INSTANCE, "loot_tables");
        this.lootPredicateManager = lootPredicateManager;
    }

    public LootTable getLootTableFromLocation(ResourceLocation resourceLocation) {
        return this.registeredLootTables.getOrDefault(resourceLocation, LootTable.EMPTY_LOOT_TABLE);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager iResourceManager, IProfiler iProfiler) {
        ImmutableMap.Builder<ResourceLocation, LootTable> builder = ImmutableMap.builder();
        JsonElement jsonElement = map.remove(LootTables.EMPTY);
        if (jsonElement != null) {
            LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", (Object)LootTables.EMPTY);
        }
        map.forEach((arg_0, arg_1) -> LootTableManager.lambda$apply$0(builder, arg_0, arg_1));
        builder.put(LootTables.EMPTY, LootTable.EMPTY_LOOT_TABLE);
        ImmutableMap<ResourceLocation, LootTable> immutableMap = builder.build();
        ValidationTracker validationTracker = new ValidationTracker(LootParameterSets.GENERIC, this.lootPredicateManager::func_227517_a_, immutableMap::get);
        immutableMap.forEach((arg_0, arg_1) -> LootTableManager.lambda$apply$1(validationTracker, arg_0, arg_1));
        validationTracker.getProblems().forEach(LootTableManager::lambda$apply$2);
        this.registeredLootTables = immutableMap;
    }

    public static void validateLootTable(ValidationTracker validationTracker, ResourceLocation resourceLocation, LootTable lootTable) {
        lootTable.validate(validationTracker.func_227529_a_(lootTable.getParameterSet()).func_227531_a_("{" + resourceLocation + "}", resourceLocation));
    }

    public static JsonElement toJson(LootTable lootTable) {
        return GSON_INSTANCE.toJsonTree(lootTable);
    }

    public Set<ResourceLocation> getLootTableKeys() {
        return this.registeredLootTables.keySet();
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((Map)object, iResourceManager, iProfiler);
    }

    private static void lambda$apply$2(String string, String string2) {
        LOGGER.warn("Found validation problem in " + string + ": " + string2);
    }

    private static void lambda$apply$1(ValidationTracker validationTracker, ResourceLocation resourceLocation, LootTable lootTable) {
        LootTableManager.validateLootTable(validationTracker, resourceLocation, lootTable);
    }

    private static void lambda$apply$0(ImmutableMap.Builder builder, ResourceLocation resourceLocation, JsonElement jsonElement) {
        try {
            LootTable lootTable = GSON_INSTANCE.fromJson(jsonElement, LootTable.class);
            builder.put(resourceLocation, lootTable);
        } catch (Exception exception) {
            LOGGER.error("Couldn't parse loot table {}", (Object)resourceLocation, (Object)exception);
        }
    }
}

