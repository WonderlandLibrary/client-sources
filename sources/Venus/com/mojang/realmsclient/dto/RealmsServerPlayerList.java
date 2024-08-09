/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerPlayerList
extends ValueObject {
    private static final Logger field_230611_c_ = LogManager.getLogger();
    private static final JsonParser field_237698_d_ = new JsonParser();
    public long field_230609_a_;
    public List<String> field_230610_b_;

    public static RealmsServerPlayerList func_230785_a_(JsonObject jsonObject) {
        RealmsServerPlayerList realmsServerPlayerList = new RealmsServerPlayerList();
        try {
            JsonElement jsonElement;
            realmsServerPlayerList.field_230609_a_ = JsonUtils.func_225169_a("serverId", jsonObject, -1L);
            String string = JsonUtils.func_225171_a("playerList", jsonObject, null);
            realmsServerPlayerList.field_230610_b_ = string != null ? ((jsonElement = field_237698_d_.parse(string)).isJsonArray() ? RealmsServerPlayerList.func_230784_a_(jsonElement.getAsJsonArray()) : Lists.newArrayList()) : Lists.newArrayList();
        } catch (Exception exception) {
            field_230611_c_.error("Could not parse RealmsServerPlayerList: " + exception.getMessage());
        }
        return realmsServerPlayerList;
    }

    private static List<String> func_230784_a_(JsonArray jsonArray) {
        ArrayList<String> arrayList = Lists.newArrayList();
        for (JsonElement jsonElement : jsonArray) {
            try {
                arrayList.add(jsonElement.getAsString());
            } catch (Exception exception) {}
        }
        return arrayList;
    }
}

