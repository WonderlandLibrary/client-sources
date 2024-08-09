/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class Criterion {
    private final ICriterionInstance criterionInstance;

    public Criterion(ICriterionInstance iCriterionInstance) {
        this.criterionInstance = iCriterionInstance;
    }

    public Criterion() {
        this.criterionInstance = null;
    }

    public void serializeToNetwork(PacketBuffer packetBuffer) {
    }

    public static Criterion deserializeCriterion(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
        ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "trigger"));
        ICriterionTrigger iCriterionTrigger = CriteriaTriggers.get(resourceLocation);
        if (iCriterionTrigger == null) {
            throw new JsonSyntaxException("Invalid criterion trigger: " + resourceLocation);
        }
        Object t = iCriterionTrigger.deserialize(JSONUtils.getJsonObject(jsonObject, "conditions", new JsonObject()), conditionArrayParser);
        return new Criterion((ICriterionInstance)t);
    }

    public static Criterion criterionFromNetwork(PacketBuffer packetBuffer) {
        return new Criterion();
    }

    public static Map<String, Criterion> deserializeAll(JsonObject jsonObject, ConditionArrayParser conditionArrayParser) {
        HashMap<String, Criterion> hashMap = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            hashMap.put(entry.getKey(), Criterion.deserializeCriterion(JSONUtils.getJsonObject(entry.getValue(), "criterion"), conditionArrayParser));
        }
        return hashMap;
    }

    public static Map<String, Criterion> criteriaFromNetwork(PacketBuffer packetBuffer) {
        HashMap<String, Criterion> hashMap = Maps.newHashMap();
        int n = packetBuffer.readVarInt();
        for (int i = 0; i < n; ++i) {
            hashMap.put(packetBuffer.readString(Short.MAX_VALUE), Criterion.criterionFromNetwork(packetBuffer));
        }
        return hashMap;
    }

    public static void serializeToNetwork(Map<String, Criterion> map, PacketBuffer packetBuffer) {
        packetBuffer.writeVarInt(map.size());
        for (Map.Entry<String, Criterion> entry : map.entrySet()) {
            packetBuffer.writeString(entry.getKey());
            entry.getValue().serializeToNetwork(packetBuffer);
        }
    }

    @Nullable
    public ICriterionInstance getCriterionInstance() {
        return this.criterionInstance;
    }

    public JsonElement serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("trigger", this.criterionInstance.getId().toString());
        JsonObject jsonObject2 = this.criterionInstance.serialize(ConditionArraySerializer.field_235679_a_);
        if (jsonObject2.size() != 0) {
            jsonObject.add("conditions", jsonObject2);
        }
        return jsonObject;
    }
}

