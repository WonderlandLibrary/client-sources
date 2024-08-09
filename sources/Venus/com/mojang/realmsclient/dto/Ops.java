/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Set;

public class Ops
extends ValueObject {
    public Set<String> field_230562_a_ = Sets.newHashSet();

    public static Ops func_230754_a_(String string) {
        Ops ops = new Ops();
        JsonParser jsonParser = new JsonParser();
        try {
            JsonElement jsonElement = jsonParser.parse(string);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement jsonElement2 = jsonObject.get("ops");
            if (jsonElement2.isJsonArray()) {
                for (JsonElement jsonElement3 : jsonElement2.getAsJsonArray()) {
                    ops.field_230562_a_.add(jsonElement3.getAsString());
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
        return ops;
    }
}

