/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.JSONUtils;

public class EntityHasScore
implements ILootCondition {
    private final Map<String, RandomValueRange> scores;
    private final LootContext.EntityTarget target;

    private EntityHasScore(Map<String, RandomValueRange> map, LootContext.EntityTarget entityTarget) {
        this.scores = ImmutableMap.copyOf(map);
        this.target = entityTarget;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.ENTITY_SCORES;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(this.target.getParameter());
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity2 = lootContext.get(this.target.getParameter());
        if (entity2 == null) {
            return true;
        }
        Scoreboard scoreboard = entity2.world.getScoreboard();
        for (Map.Entry<String, RandomValueRange> entry : this.scores.entrySet()) {
            if (this.entityScoreMatch(entity2, scoreboard, entry.getKey(), entry.getValue())) continue;
            return true;
        }
        return false;
    }

    protected boolean entityScoreMatch(Entity entity2, Scoreboard scoreboard, String string, RandomValueRange randomValueRange) {
        ScoreObjective scoreObjective = scoreboard.getObjective(string);
        if (scoreObjective == null) {
            return true;
        }
        String string2 = entity2.getScoreboardName();
        return !scoreboard.entityHasObjective(string2, scoreObjective) ? false : randomValueRange.isInRange(scoreboard.getOrCreateScore(string2, scoreObjective).getScorePoints());
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<EntityHasScore> {
        @Override
        public void serialize(JsonObject jsonObject, EntityHasScore entityHasScore, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject2 = new JsonObject();
            for (Map.Entry<String, RandomValueRange> entry : entityHasScore.scores.entrySet()) {
                jsonObject2.add(entry.getKey(), jsonSerializationContext.serialize(entry.getValue()));
            }
            jsonObject.add("scores", jsonObject2);
            jsonObject.add("entity", jsonSerializationContext.serialize((Object)entityHasScore.target));
        }

        @Override
        public EntityHasScore deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            Set<Map.Entry<String, JsonElement>> set = JSONUtils.getJsonObject(jsonObject, "scores").entrySet();
            LinkedHashMap<String, RandomValueRange> linkedHashMap = Maps.newLinkedHashMap();
            for (Map.Entry<String, JsonElement> entry : set) {
                linkedHashMap.put(entry.getKey(), JSONUtils.deserializeClass(entry.getValue(), "score", jsonDeserializationContext, RandomValueRange.class));
            }
            return new EntityHasScore(linkedHashMap, JSONUtils.deserializeClass(jsonObject, "entity", jsonDeserializationContext, LootContext.EntityTarget.class));
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (EntityHasScore)object, jsonSerializationContext);
        }
    }
}

