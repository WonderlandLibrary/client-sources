/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementTreeNode;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementManager
extends JsonReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();
    private AdvancementList advancementList = new AdvancementList();
    private final LootPredicateManager lootPredicateManager;

    public AdvancementManager(LootPredicateManager lootPredicateManager) {
        super(GSON, "advancements");
        this.lootPredicateManager = lootPredicateManager;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager iResourceManager, IProfiler iProfiler) {
        HashMap<ResourceLocation, Advancement.Builder> hashMap = Maps.newHashMap();
        map.forEach((arg_0, arg_1) -> this.lambda$apply$0(hashMap, arg_0, arg_1));
        AdvancementList advancementList = new AdvancementList();
        advancementList.loadAdvancements(hashMap);
        for (Advancement advancement : advancementList.getRoots()) {
            if (advancement.getDisplay() == null) continue;
            AdvancementTreeNode.layout(advancement);
        }
        this.advancementList = advancementList;
    }

    @Nullable
    public Advancement getAdvancement(ResourceLocation resourceLocation) {
        return this.advancementList.getAdvancement(resourceLocation);
    }

    public Collection<Advancement> getAllAdvancements() {
        return this.advancementList.getAll();
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((Map)object, iResourceManager, iProfiler);
    }

    private void lambda$apply$0(Map map, ResourceLocation resourceLocation, JsonElement jsonElement) {
        try {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "advancement");
            Advancement.Builder builder = Advancement.Builder.deserialize(jsonObject, new ConditionArrayParser(resourceLocation, this.lootPredicateManager));
            map.put(resourceLocation, builder);
        } catch (JsonParseException | IllegalArgumentException runtimeException) {
            LOGGER.error("Parsing error loading custom advancement {}: {}", (Object)resourceLocation, (Object)runtimeException.getMessage());
        }
    }
}

