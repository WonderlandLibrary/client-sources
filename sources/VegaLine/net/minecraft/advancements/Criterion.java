/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Criterion {
    private final ICriterionInstance field_192147_a;

    public Criterion(ICriterionInstance p_i47470_1_) {
        this.field_192147_a = p_i47470_1_;
    }

    public Criterion() {
        this.field_192147_a = null;
    }

    public void func_192140_a(PacketBuffer p_192140_1_) {
    }

    public static Criterion func_192145_a(JsonObject p_192145_0_, JsonDeserializationContext p_192145_1_) {
        ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(p_192145_0_, "trigger"));
        ICriterionTrigger icriteriontrigger = CriteriaTriggers.func_192119_a(resourcelocation);
        if (icriteriontrigger == null) {
            throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
        }
        Object icriterioninstance = icriteriontrigger.func_192166_a(JsonUtils.getJsonObject(p_192145_0_, "conditions", new JsonObject()), p_192145_1_);
        return new Criterion((ICriterionInstance)icriterioninstance);
    }

    public static Criterion func_192146_b(PacketBuffer p_192146_0_) {
        return new Criterion();
    }

    public static Map<String, Criterion> func_192144_b(JsonObject p_192144_0_, JsonDeserializationContext p_192144_1_) {
        HashMap<String, Criterion> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : p_192144_0_.entrySet()) {
            map.put(entry.getKey(), Criterion.func_192145_a(JsonUtils.getJsonObject(entry.getValue(), "criterion"), p_192144_1_));
        }
        return map;
    }

    public static Map<String, Criterion> func_192142_c(PacketBuffer p_192142_0_) {
        HashMap<String, Criterion> map = Maps.newHashMap();
        int i = p_192142_0_.readVarIntFromBuffer();
        for (int j = 0; j < i; ++j) {
            map.put(p_192142_0_.readStringFromBuffer(Short.MAX_VALUE), Criterion.func_192146_b(p_192142_0_));
        }
        return map;
    }

    public static void func_192141_a(Map<String, Criterion> p_192141_0_, PacketBuffer p_192141_1_) {
        p_192141_1_.writeVarIntToBuffer(p_192141_0_.size());
        for (Map.Entry<String, Criterion> entry : p_192141_0_.entrySet()) {
            p_192141_1_.writeString(entry.getKey());
            entry.getValue().func_192140_a(p_192141_1_);
        }
    }

    @Nullable
    public ICriterionInstance func_192143_a() {
        return this.field_192147_a;
    }
}

