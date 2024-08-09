/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PendingInvitesList
extends ValueObject {
    private static final Logger field_230570_b_ = LogManager.getLogger();
    public List<PendingInvite> field_230569_a_ = Lists.newArrayList();

    public static PendingInvitesList func_230756_a_(String string) {
        PendingInvitesList pendingInvitesList = new PendingInvitesList();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            if (jsonObject.get("invites").isJsonArray()) {
                Iterator<JsonElement> iterator2 = jsonObject.get("invites").getAsJsonArray().iterator();
                while (iterator2.hasNext()) {
                    pendingInvitesList.field_230569_a_.add(PendingInvite.func_230755_a_(iterator2.next().getAsJsonObject()));
                }
            }
        } catch (Exception exception) {
            field_230570_b_.error("Could not parse PendingInvitesList: " + exception.getMessage());
        }
        return pendingInvitesList;
    }
}

