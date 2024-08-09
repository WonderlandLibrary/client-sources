/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

public class AdvancementProgress
implements Comparable<AdvancementProgress> {
    private final Map<String, CriterionProgress> criteria = Maps.newHashMap();
    private String[][] requirements = new String[0][];

    public void update(Map<String, Criterion> map, String[][] stringArray) {
        Set<String> set = map.keySet();
        this.criteria.entrySet().removeIf(arg_0 -> AdvancementProgress.lambda$update$0(set, arg_0));
        for (String string : set) {
            if (this.criteria.containsKey(string)) continue;
            this.criteria.put(string, new CriterionProgress());
        }
        this.requirements = stringArray;
    }

    public boolean isDone() {
        if (this.requirements.length == 0) {
            return true;
        }
        for (String[] stringArray : this.requirements) {
            boolean bl = false;
            for (String string : stringArray) {
                CriterionProgress criterionProgress = this.getCriterionProgress(string);
                if (criterionProgress == null || !criterionProgress.isObtained()) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            return true;
        }
        return false;
    }

    public boolean hasProgress() {
        for (CriterionProgress criterionProgress : this.criteria.values()) {
            if (!criterionProgress.isObtained()) continue;
            return false;
        }
        return true;
    }

    public boolean grantCriterion(String string) {
        CriterionProgress criterionProgress = this.criteria.get(string);
        if (criterionProgress != null && !criterionProgress.isObtained()) {
            criterionProgress.obtain();
            return false;
        }
        return true;
    }

    public boolean revokeCriterion(String string) {
        CriterionProgress criterionProgress = this.criteria.get(string);
        if (criterionProgress != null && criterionProgress.isObtained()) {
            criterionProgress.reset();
            return false;
        }
        return true;
    }

    public String toString() {
        return "AdvancementProgress{criteria=" + this.criteria + ", requirements=" + Arrays.deepToString((Object[])this.requirements) + "}";
    }

    public void serializeToNetwork(PacketBuffer packetBuffer) {
        packetBuffer.writeVarInt(this.criteria.size());
        for (Map.Entry<String, CriterionProgress> entry : this.criteria.entrySet()) {
            packetBuffer.writeString(entry.getKey());
            entry.getValue().write(packetBuffer);
        }
    }

    public static AdvancementProgress fromNetwork(PacketBuffer packetBuffer) {
        AdvancementProgress advancementProgress = new AdvancementProgress();
        int n = packetBuffer.readVarInt();
        for (int i = 0; i < n; ++i) {
            advancementProgress.criteria.put(packetBuffer.readString(Short.MAX_VALUE), CriterionProgress.read(packetBuffer));
        }
        return advancementProgress;
    }

    @Nullable
    public CriterionProgress getCriterionProgress(String string) {
        return this.criteria.get(string);
    }

    public float getPercent() {
        if (this.criteria.isEmpty()) {
            return 0.0f;
        }
        float f = this.requirements.length;
        float f2 = this.countCompletedRequirements();
        return f2 / f;
    }

    @Nullable
    public String getProgressText() {
        if (this.criteria.isEmpty()) {
            return null;
        }
        int n = this.requirements.length;
        if (n <= 1) {
            return null;
        }
        int n2 = this.countCompletedRequirements();
        return n2 + "/" + n;
    }

    private int countCompletedRequirements() {
        int n = 0;
        for (String[] stringArray : this.requirements) {
            boolean bl = false;
            for (String string : stringArray) {
                CriterionProgress criterionProgress = this.getCriterionProgress(string);
                if (criterionProgress == null || !criterionProgress.isObtained()) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            ++n;
        }
        return n;
    }

    public Iterable<String> getRemaningCriteria() {
        ArrayList<String> arrayList = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteria.entrySet()) {
            if (entry.getValue().isObtained()) continue;
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    public Iterable<String> getCompletedCriteria() {
        ArrayList<String> arrayList = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteria.entrySet()) {
            if (!entry.getValue().isObtained()) continue;
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    @Nullable
    public Date getFirstProgressDate() {
        Date date = null;
        for (CriterionProgress criterionProgress : this.criteria.values()) {
            if (!criterionProgress.isObtained() || date != null && !criterionProgress.getObtained().before(date)) continue;
            date = criterionProgress.getObtained();
        }
        return date;
    }

    @Override
    public int compareTo(AdvancementProgress advancementProgress) {
        Date date = this.getFirstProgressDate();
        Date date2 = advancementProgress.getFirstProgressDate();
        if (date == null && date2 != null) {
            return 0;
        }
        if (date != null && date2 == null) {
            return 1;
        }
        return date == null && date2 == null ? 0 : date.compareTo(date2);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((AdvancementProgress)object);
    }

    private static boolean lambda$update$0(Set set, Map.Entry entry) {
        return !set.contains(entry.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<AdvancementProgress>,
    JsonSerializer<AdvancementProgress> {
        @Override
        public JsonElement serialize(AdvancementProgress advancementProgress, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();
            for (Map.Entry<String, CriterionProgress> entry : advancementProgress.criteria.entrySet()) {
                CriterionProgress criterionProgress = entry.getValue();
                if (!criterionProgress.isObtained()) continue;
                jsonObject2.add(entry.getKey(), criterionProgress.serialize());
            }
            if (!jsonObject2.entrySet().isEmpty()) {
                jsonObject.add("criteria", jsonObject2);
            }
            jsonObject.addProperty("done", advancementProgress.isDone());
            return jsonObject;
        }

        @Override
        public AdvancementProgress deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "advancement");
            JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "criteria", new JsonObject());
            AdvancementProgress advancementProgress = new AdvancementProgress();
            for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                String string = entry.getKey();
                advancementProgress.criteria.put(string, CriterionProgress.fromJson(JSONUtils.getString(entry.getValue(), string)));
            }
            return advancementProgress;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((AdvancementProgress)object, type, jsonSerializationContext);
        }
    }
}

