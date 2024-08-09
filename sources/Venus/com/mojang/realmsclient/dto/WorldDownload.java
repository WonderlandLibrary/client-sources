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

public class WorldDownload
extends ValueObject {
    private static final Logger field_230646_d_ = LogManager.getLogger();
    public String field_230643_a_;
    public String field_230644_b_;
    public String field_230645_c_;

    public static WorldDownload func_230802_a_(String string) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
        WorldDownload worldDownload = new WorldDownload();
        try {
            worldDownload.field_230643_a_ = JsonUtils.func_225171_a("downloadLink", jsonObject, "");
            worldDownload.field_230644_b_ = JsonUtils.func_225171_a("resourcePackUrl", jsonObject, "");
            worldDownload.field_230645_c_ = JsonUtils.func_225171_a("resourcePackHash", jsonObject, "");
        } catch (Exception exception) {
            field_230646_d_.error("Could not parse WorldDownload: " + exception.getMessage());
        }
        return worldDownload;
    }
}

