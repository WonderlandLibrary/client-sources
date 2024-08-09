/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsNews
extends ValueObject {
    private static final Logger field_230581_b_ = LogManager.getLogger();
    public String field_230580_a_;

    public static RealmsNews func_230767_a_(String string) {
        RealmsNews realmsNews = new RealmsNews();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            realmsNews.field_230580_a_ = JsonUtils.func_225171_a("newsLink", jsonObject, null);
        } catch (Exception exception) {
            field_230581_b_.error("Could not parse RealmsNews: " + exception.getMessage());
        }
        return realmsNews;
    }
}

