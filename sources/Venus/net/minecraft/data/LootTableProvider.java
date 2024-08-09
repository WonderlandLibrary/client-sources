/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.data.loot.GiftLootTables;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.PiglinBarteringAddition;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableProvider
implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator dataGenerator;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> field_218444_e = ImmutableList.of(Pair.of(FishingLootTables::new, LootParameterSets.FISHING), Pair.of(ChestLootTables::new, LootParameterSets.CHEST), Pair.of(EntityLootTables::new, LootParameterSets.ENTITY), Pair.of(BlockLootTables::new, LootParameterSets.BLOCK), Pair.of(PiglinBarteringAddition::new, LootParameterSets.field_237453_h_), Pair.of(GiftLootTables::new, LootParameterSets.GIFT));

    public LootTableProvider(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) {
        Path path = this.dataGenerator.getOutputFolder();
        HashMap<ResourceLocation, LootTable> hashMap = Maps.newHashMap();
        this.field_218444_e.forEach(arg_0 -> LootTableProvider.lambda$act$1(hashMap, arg_0));
        ValidationTracker validationTracker = new ValidationTracker(LootParameterSets.GENERIC, LootTableProvider::lambda$act$2, hashMap::get);
        for (ResourceLocation resourceLocation : Sets.difference(LootTables.getReadOnlyLootTables(), hashMap.keySet())) {
            validationTracker.addProblem("Missing built-in table: " + resourceLocation);
        }
        hashMap.forEach((arg_0, arg_1) -> LootTableProvider.lambda$act$3(validationTracker, arg_0, arg_1));
        Multimap<String, String> multimap = validationTracker.getProblems();
        if (!multimap.isEmpty()) {
            multimap.forEach(LootTableProvider::lambda$act$4);
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        }
        hashMap.forEach((arg_0, arg_1) -> LootTableProvider.lambda$act$5(path, directoryCache, arg_0, arg_1));
    }

    private static Path getPath(Path path, ResourceLocation resourceLocation) {
        return path.resolve("data/" + resourceLocation.getNamespace() + "/loot_tables/" + resourceLocation.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "LootTables";
    }

    private static void lambda$act$5(Path path, DirectoryCache directoryCache, ResourceLocation resourceLocation, LootTable lootTable) {
        Path path2 = LootTableProvider.getPath(path, resourceLocation);
        try {
            IDataProvider.save(GSON, directoryCache, LootTableManager.toJson(lootTable), path2);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save loot table {}", (Object)path2, (Object)iOException);
        }
    }

    private static void lambda$act$4(String string, String string2) {
        LOGGER.warn("Found validation problem in " + string + ": " + string2);
    }

    private static void lambda$act$3(ValidationTracker validationTracker, ResourceLocation resourceLocation, LootTable lootTable) {
        LootTableManager.validateLootTable(validationTracker, resourceLocation, lootTable);
    }

    private static ILootCondition lambda$act$2(ResourceLocation resourceLocation) {
        return null;
    }

    private static void lambda$act$1(Map map, Pair pair) {
        ((Consumer)((Supplier)pair.getFirst()).get()).accept((arg_0, arg_1) -> LootTableProvider.lambda$act$0(map, pair, arg_0, arg_1));
    }

    private static void lambda$act$0(Map map, Pair pair, ResourceLocation resourceLocation, LootTable.Builder builder) {
        if (map.put(resourceLocation, builder.setParameterSet((LootParameterSet)pair.getSecond()).build()) != null) {
            throw new IllegalStateException("Duplicate loot table " + resourceLocation);
        }
    }
}

