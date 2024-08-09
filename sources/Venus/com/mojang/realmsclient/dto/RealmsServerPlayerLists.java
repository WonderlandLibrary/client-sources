/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerPlayerLists
extends ValueObject {
    private static final Logger field_230613_b_ = LogManager.getLogger();
    public List<RealmsServerPlayerList> field_230612_a_;

    public static RealmsServerPlayerLists func_230786_a_(String string) {
        RealmsServerPlayerLists realmsServerPlayerLists = new RealmsServerPlayerLists();
        realmsServerPlayerLists.field_230612_a_ = Lists.newArrayList();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            if (jsonObject.get("lists").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("lists").getAsJsonArray();
                Iterator<JsonElement> iterator2 = jsonArray.iterator();
                while (iterator2.hasNext()) {
                    realmsServerPlayerLists.field_230612_a_.add(RealmsServerPlayerList.func_230785_a_(iterator2.next().getAsJsonObject()));
                }
            }
        } catch (Exception exception) {
            field_230613_b_.error("Could not parse RealmsServerPlayerLists: " + exception.getMessage());
        }
        return realmsServerPlayerLists;
    }
}

