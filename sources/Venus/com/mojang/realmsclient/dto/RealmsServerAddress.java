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

public class RealmsServerAddress
extends ValueObject {
    private static final Logger field_230604_d_ = LogManager.getLogger();
    public String field_230601_a_;
    public String field_230602_b_;
    public String field_230603_c_;

    public static RealmsServerAddress func_230782_a_(String string) {
        JsonParser jsonParser = new JsonParser();
        RealmsServerAddress realmsServerAddress = new RealmsServerAddress();
        try {
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            realmsServerAddress.field_230601_a_ = JsonUtils.func_225171_a("address", jsonObject, null);
            realmsServerAddress.field_230602_b_ = JsonUtils.func_225171_a("resourcePackUrl", jsonObject, null);
            realmsServerAddress.field_230603_c_ = JsonUtils.func_225171_a("resourcePackHash", jsonObject, null);
        } catch (Exception exception) {
            field_230604_d_.error("Could not parse RealmsServerAddress: " + exception.getMessage());
        }
        return realmsServerAddress;
    }
}

