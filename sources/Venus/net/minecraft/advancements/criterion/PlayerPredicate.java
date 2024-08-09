/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.ServerRecipeBook;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameType;

public class PlayerPredicate {
    public static final PlayerPredicate ANY = new Default().create();
    private final MinMaxBounds.IntBound level;
    private final GameType gamemode;
    private final Map<Stat<?>, MinMaxBounds.IntBound> stats;
    private final Object2BooleanMap<ResourceLocation> recipes;
    private final Map<ResourceLocation, IAdvancementPredicate> advancements;

    private static IAdvancementPredicate deserializeAdvancementPredicate(JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            boolean bl = jsonElement.getAsBoolean();
            return new CompletedAdvancementPredicate(bl);
        }
        Object2BooleanOpenHashMap<String> object2BooleanOpenHashMap = new Object2BooleanOpenHashMap<String>();
        JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "criterion data");
        jsonObject.entrySet().forEach(arg_0 -> PlayerPredicate.lambda$deserializeAdvancementPredicate$0(object2BooleanOpenHashMap, arg_0));
        return new CriteriaPredicate(object2BooleanOpenHashMap);
    }

    private PlayerPredicate(MinMaxBounds.IntBound intBound, GameType gameType, Map<Stat<?>, MinMaxBounds.IntBound> map, Object2BooleanMap<ResourceLocation> object2BooleanMap, Map<ResourceLocation, IAdvancementPredicate> map2) {
        this.level = intBound;
        this.gamemode = gameType;
        this.stats = map;
        this.recipes = object2BooleanMap;
        this.advancements = map2;
    }

    public boolean test(Entity entity2) {
        if (this == ANY) {
            return false;
        }
        if (!(entity2 instanceof ServerPlayerEntity)) {
            return true;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
        if (!this.level.test(serverPlayerEntity.experienceLevel)) {
            return true;
        }
        if (this.gamemode != GameType.NOT_SET && this.gamemode != serverPlayerEntity.interactionManager.getGameType()) {
            return true;
        }
        ServerStatisticsManager serverStatisticsManager = serverPlayerEntity.getStats();
        for (Map.Entry<Stat<?>, MinMaxBounds.IntBound> object : this.stats.entrySet()) {
            int advancementManager = serverStatisticsManager.getValue(object.getKey());
            if (object.getValue().test(advancementManager)) continue;
            return true;
        }
        ServerRecipeBook serverRecipeBook = serverPlayerEntity.getRecipeBook();
        for (Object2BooleanMap.Entry entry : this.recipes.object2BooleanEntrySet()) {
            if (serverRecipeBook.isUnlocked((ResourceLocation)entry.getKey()) == entry.getBooleanValue()) continue;
            return true;
        }
        if (!this.advancements.isEmpty()) {
            PlayerAdvancements playerAdvancements = serverPlayerEntity.getAdvancements();
            AdvancementManager advancementManager = serverPlayerEntity.getServer().getAdvancementManager();
            for (Map.Entry<ResourceLocation, IAdvancementPredicate> entry : this.advancements.entrySet()) {
                Advancement advancement = advancementManager.getAdvancement(entry.getKey());
                if (advancement != null && entry.getValue().test(playerAdvancements.getProgress(advancement))) continue;
                return true;
            }
        }
        return false;
    }

    public static PlayerPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            Object object;
            Object object2;
            Object object3;
            Object object5;
            JsonElement jsonElement222;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "player");
            MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("level"));
            String string = JSONUtils.getString(jsonObject, "gamemode", "");
            GameType gameType = GameType.parseGameTypeWithDefault(string, GameType.NOT_SET);
            HashMap<Stat<?>, MinMaxBounds.IntBound> hashMap = Maps.newHashMap();
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "stats", null);
            if (jsonArray != null) {
                for (JsonElement jsonElement222 : jsonArray) {
                    object5 = JSONUtils.getJsonObject(jsonElement222, "stats entry");
                    ResourceLocation object42 = new ResourceLocation(JSONUtils.getString((JsonObject)object5, "type"));
                    object3 = Registry.STATS.getOrDefault(object42);
                    if (object3 == null) {
                        throw new JsonParseException("Invalid stat type: " + object42);
                    }
                    ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(object5, "stat"));
                    object2 = PlayerPredicate.getStat(object3, resourceLocation);
                    object = MinMaxBounds.IntBound.fromJson(((JsonObject)object5).get("value"));
                    hashMap.put((Stat<?>)object2, (MinMaxBounds.IntBound)object);
                }
            }
            Object2BooleanOpenHashMap object2BooleanOpenHashMap = new Object2BooleanOpenHashMap();
            jsonElement222 = JSONUtils.getJsonObject(jsonObject, "recipes", new JsonObject());
            for (Map.Entry entry : ((JsonObject)jsonElement222).entrySet()) {
                object3 = new ResourceLocation((String)entry.getKey());
                boolean bl = JSONUtils.getBoolean((JsonElement)entry.getValue(), "recipe present");
                object2BooleanOpenHashMap.put(object3, bl);
            }
            object5 = Maps.newHashMap();
            JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "advancements", new JsonObject());
            for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                object2 = new ResourceLocation(entry.getKey());
                object = PlayerPredicate.deserializeAdvancementPredicate(entry.getValue());
                object5.put((ResourceLocation)object2, (IAdvancementPredicate)object);
            }
            return new PlayerPredicate(intBound, gameType, (Map<Stat<?>, MinMaxBounds.IntBound>)hashMap, object2BooleanOpenHashMap, (Map<ResourceLocation, IAdvancementPredicate>)object5);
        }
        return ANY;
    }

    private static <T> Stat<T> getStat(StatType<T> statType, ResourceLocation resourceLocation) {
        Registry<T> registry = statType.getRegistry();
        T t = registry.getOrDefault(resourceLocation);
        if (t == null) {
            throw new JsonParseException("Unknown object " + resourceLocation + " for stat type " + Registry.STATS.getKey(statType));
        }
        return statType.get(t);
    }

    private static <T> ResourceLocation getRegistryKeyForStat(Stat<T> stat) {
        return stat.getType().getRegistry().getKey(stat.getValue());
    }

    public JsonElement serialize() {
        JsonElement jsonElement;
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("level", this.level.serialize());
        if (this.gamemode != GameType.NOT_SET) {
            jsonObject.addProperty("gamemode", this.gamemode.getName());
        }
        if (!this.stats.isEmpty()) {
            jsonElement = new JsonArray();
            this.stats.forEach((arg_0, arg_1) -> PlayerPredicate.lambda$serialize$1((JsonArray)jsonElement, arg_0, arg_1));
            jsonObject.add("stats", jsonElement);
        }
        if (!this.recipes.isEmpty()) {
            jsonElement = new JsonObject();
            this.recipes.forEach((arg_0, arg_1) -> PlayerPredicate.lambda$serialize$2((JsonObject)jsonElement, arg_0, arg_1));
            jsonObject.add("recipes", jsonElement);
        }
        if (!this.advancements.isEmpty()) {
            jsonElement = new JsonObject();
            this.advancements.forEach((arg_0, arg_1) -> PlayerPredicate.lambda$serialize$3((JsonObject)jsonElement, arg_0, arg_1));
            jsonObject.add("advancements", jsonElement);
        }
        return jsonObject;
    }

    private static void lambda$serialize$3(JsonObject jsonObject, ResourceLocation resourceLocation, IAdvancementPredicate iAdvancementPredicate) {
        jsonObject.add(resourceLocation.toString(), iAdvancementPredicate.serialize());
    }

    private static void lambda$serialize$2(JsonObject jsonObject, ResourceLocation resourceLocation, Boolean bl) {
        jsonObject.addProperty(resourceLocation.toString(), bl);
    }

    private static void lambda$serialize$1(JsonArray jsonArray, Stat stat, MinMaxBounds.IntBound intBound) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", Registry.STATS.getKey(stat.getType()).toString());
        jsonObject.addProperty("stat", PlayerPredicate.getRegistryKeyForStat(stat).toString());
        jsonObject.add("value", intBound.serialize());
        jsonArray.add(jsonObject);
    }

    private static void lambda$deserializeAdvancementPredicate$0(Object2BooleanMap object2BooleanMap, Map.Entry entry) {
        boolean bl = JSONUtils.getBoolean((JsonElement)entry.getValue(), "criterion test");
        object2BooleanMap.put((String)entry.getKey(), bl);
    }

    static class CompletedAdvancementPredicate
    implements IAdvancementPredicate {
        private final boolean completion;

        public CompletedAdvancementPredicate(boolean bl) {
            this.completion = bl;
        }

        @Override
        public JsonElement serialize() {
            return new JsonPrimitive(this.completion);
        }

        @Override
        public boolean test(AdvancementProgress advancementProgress) {
            return advancementProgress.isDone() == this.completion;
        }

        @Override
        public boolean test(Object object) {
            return this.test((AdvancementProgress)object);
        }
    }

    static class CriteriaPredicate
    implements IAdvancementPredicate {
        private final Object2BooleanMap<String> completion;

        public CriteriaPredicate(Object2BooleanMap<String> object2BooleanMap) {
            this.completion = object2BooleanMap;
        }

        @Override
        public JsonElement serialize() {
            JsonObject jsonObject = new JsonObject();
            this.completion.forEach(jsonObject::addProperty);
            return jsonObject;
        }

        @Override
        public boolean test(AdvancementProgress advancementProgress) {
            for (Object2BooleanMap.Entry entry : this.completion.object2BooleanEntrySet()) {
                CriterionProgress criterionProgress = advancementProgress.getCriterionProgress((String)entry.getKey());
                if (criterionProgress != null && criterionProgress.isObtained() == entry.getBooleanValue()) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean test(Object object) {
            return this.test((AdvancementProgress)object);
        }
    }

    static interface IAdvancementPredicate
    extends Predicate<AdvancementProgress> {
        public JsonElement serialize();
    }

    public static class Default {
        private MinMaxBounds.IntBound level = MinMaxBounds.IntBound.UNBOUNDED;
        private GameType gameType = GameType.NOT_SET;
        private final Map<Stat<?>, MinMaxBounds.IntBound> statValues = Maps.newHashMap();
        private final Object2BooleanMap<ResourceLocation> recipes = new Object2BooleanOpenHashMap<ResourceLocation>();
        private final Map<ResourceLocation, IAdvancementPredicate> advancements = Maps.newHashMap();

        public PlayerPredicate create() {
            return new PlayerPredicate(this.level, this.gameType, this.statValues, this.recipes, this.advancements);
        }
    }
}

