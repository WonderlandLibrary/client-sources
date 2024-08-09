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

public class Subscription
extends ValueObject {
    private static final Logger field_230637_d_ = LogManager.getLogger();
    public long field_230634_a_;
    public int field_230635_b_;
    public Type field_230636_c_ = Type.NORMAL;

    public static Subscription func_230793_a_(String string) {
        Subscription subscription = new Subscription();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            subscription.field_230634_a_ = JsonUtils.func_225169_a("startDate", jsonObject, 0L);
            subscription.field_230635_b_ = JsonUtils.func_225172_a("daysLeft", jsonObject, 0);
            subscription.field_230636_c_ = Subscription.func_230794_b_(JsonUtils.func_225171_a("subscriptionType", jsonObject, Type.NORMAL.name()));
        } catch (Exception exception) {
            field_230637_d_.error("Could not parse Subscription: " + exception.getMessage());
        }
        return subscription;
    }

    private static Type func_230794_b_(String string) {
        try {
            return Type.valueOf(string);
        } catch (Exception exception) {
            return Type.NORMAL;
        }
    }

    public static enum Type {
        NORMAL,
        RECURRING;

    }
}

