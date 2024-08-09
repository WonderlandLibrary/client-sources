/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsError {
    private static final Logger field_224975_a = LogManager.getLogger();
    private final String field_224976_b;
    private final int field_224977_c;

    private RealmsError(String string, int n) {
        this.field_224976_b = string;
        this.field_224977_c = n;
    }

    public static RealmsError func_241826_a_(String string) {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            String string2 = JsonUtils.func_225171_a("errorMsg", jsonObject, "");
            int n = JsonUtils.func_225172_a("errorCode", jsonObject, -1);
            return new RealmsError(string2, n);
        } catch (Exception exception) {
            field_224975_a.error("Could not parse RealmsError: " + exception.getMessage());
            field_224975_a.error("The error was: " + string);
            return new RealmsError("Failed to parse response from server", -1);
        }
    }

    public String func_224973_a() {
        return this.field_224976_b;
    }

    public int func_224974_b() {
        return this.field_224977_c;
    }
}

