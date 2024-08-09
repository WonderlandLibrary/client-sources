/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerList
extends ValueObject {
    private static final Logger field_230606_b_ = LogManager.getLogger();
    public List<RealmsServer> field_230605_a_;

    public static RealmsServerList func_230783_a_(String string) {
        RealmsServerList realmsServerList = new RealmsServerList();
        realmsServerList.field_230605_a_ = Lists.newArrayList();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            if (jsonObject.get("servers").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("servers").getAsJsonArray();
                Iterator<JsonElement> iterator2 = jsonArray.iterator();
                while (iterator2.hasNext()) {
                    realmsServerList.field_230605_a_.add(RealmsServer.func_230770_a_(iterator2.next().getAsJsonObject()));
                }
            }
        } catch (Exception exception) {
            field_230606_b_.error("Could not parse McoServerList: " + exception.getMessage());
        }
        return realmsServerList;
    }
}

