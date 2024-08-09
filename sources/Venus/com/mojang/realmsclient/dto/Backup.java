/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Backup
extends ValueObject {
    private static final Logger field_230558_f_ = LogManager.getLogger();
    public String field_230553_a_;
    public Date field_230554_b_;
    public long field_230555_c_;
    private boolean field_230559_g_;
    public Map<String, String> field_230556_d_ = Maps.newHashMap();
    public Map<String, String> field_230557_e_ = Maps.newHashMap();

    public static Backup func_230750_a_(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Backup backup = new Backup();
        try {
            backup.field_230553_a_ = JsonUtils.func_225171_a("backupId", jsonObject, "");
            backup.field_230554_b_ = JsonUtils.func_225173_a("lastModifiedDate", jsonObject);
            backup.field_230555_c_ = JsonUtils.func_225169_a("size", jsonObject, 0L);
            if (jsonObject.has("metadata")) {
                JsonObject jsonObject2 = jsonObject.getAsJsonObject("metadata");
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    if (entry.getValue().isJsonNull()) continue;
                    backup.field_230556_d_.put(Backup.func_230751_a_(entry.getKey()), entry.getValue().getAsString());
                }
            }
        } catch (Exception exception) {
            field_230558_f_.error("Could not parse Backup: " + exception.getMessage());
        }
        return backup;
    }

    private static String func_230751_a_(String string) {
        String[] stringArray = string.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            if (string2 == null || string2.length() < 1) continue;
            if ("of".equals(string2)) {
                stringBuilder.append(string2).append(" ");
                continue;
            }
            char c = Character.toUpperCase(string2.charAt(0));
            stringBuilder.append(c).append(string2.substring(1)).append(" ");
        }
        return stringBuilder.toString();
    }

    public boolean func_230749_a_() {
        return this.field_230559_g_;
    }

    public void func_230752_a_(boolean bl) {
        this.field_230559_g_ = bl;
    }
}

