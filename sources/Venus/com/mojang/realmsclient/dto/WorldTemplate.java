/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldTemplate
extends ValueObject {
    private static final Logger field_230656_j_ = LogManager.getLogger();
    public String field_230647_a_ = "";
    public String field_230648_b_ = "";
    public String field_230649_c_ = "";
    public String field_230650_d_ = "";
    public String field_230651_e_ = "";
    @Nullable
    public String field_230652_f_;
    public String field_230653_g_ = "";
    public String field_230654_h_ = "";
    public Type field_230655_i_ = Type.WORLD_TEMPLATE;

    public static WorldTemplate func_230803_a_(JsonObject jsonObject) {
        WorldTemplate worldTemplate = new WorldTemplate();
        try {
            worldTemplate.field_230647_a_ = JsonUtils.func_225171_a("id", jsonObject, "");
            worldTemplate.field_230648_b_ = JsonUtils.func_225171_a("name", jsonObject, "");
            worldTemplate.field_230649_c_ = JsonUtils.func_225171_a("version", jsonObject, "");
            worldTemplate.field_230650_d_ = JsonUtils.func_225171_a("author", jsonObject, "");
            worldTemplate.field_230651_e_ = JsonUtils.func_225171_a("link", jsonObject, "");
            worldTemplate.field_230652_f_ = JsonUtils.func_225171_a("image", jsonObject, null);
            worldTemplate.field_230653_g_ = JsonUtils.func_225171_a("trailer", jsonObject, "");
            worldTemplate.field_230654_h_ = JsonUtils.func_225171_a("recommendedPlayers", jsonObject, "");
            worldTemplate.field_230655_i_ = Type.valueOf(JsonUtils.func_225171_a("type", jsonObject, Type.WORLD_TEMPLATE.name()));
        } catch (Exception exception) {
            field_230656_j_.error("Could not parse WorldTemplate: " + exception.getMessage());
        }
        return worldTemplate;
    }

    public static enum Type {
        WORLD_TEMPLATE,
        MINIGAME,
        ADVENTUREMAP,
        EXPERIENCE,
        INSPIRATION;

    }
}

