/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldTemplatePaginatedList
extends ValueObject {
    private static final Logger field_230661_e_ = LogManager.getLogger();
    public List<WorldTemplate> field_230657_a_;
    public int field_230658_b_;
    public int field_230659_c_;
    public int field_230660_d_;

    public WorldTemplatePaginatedList() {
    }

    public WorldTemplatePaginatedList(int n) {
        this.field_230657_a_ = Collections.emptyList();
        this.field_230658_b_ = 0;
        this.field_230659_c_ = n;
        this.field_230660_d_ = -1;
    }

    public static WorldTemplatePaginatedList func_230804_a_(String string) {
        WorldTemplatePaginatedList worldTemplatePaginatedList = new WorldTemplatePaginatedList();
        worldTemplatePaginatedList.field_230657_a_ = Lists.newArrayList();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            if (jsonObject.get("templates").isJsonArray()) {
                Iterator<JsonElement> iterator2 = jsonObject.get("templates").getAsJsonArray().iterator();
                while (iterator2.hasNext()) {
                    worldTemplatePaginatedList.field_230657_a_.add(WorldTemplate.func_230803_a_(iterator2.next().getAsJsonObject()));
                }
            }
            worldTemplatePaginatedList.field_230658_b_ = JsonUtils.func_225172_a("page", jsonObject, 0);
            worldTemplatePaginatedList.field_230659_c_ = JsonUtils.func_225172_a("size", jsonObject, 0);
            worldTemplatePaginatedList.field_230660_d_ = JsonUtils.func_225172_a("total", jsonObject, 0);
        } catch (Exception exception) {
            field_230661_e_.error("Could not parse WorldTemplatePaginatedList: " + exception.getMessage());
        }
        return worldTemplatePaginatedList;
    }
}

