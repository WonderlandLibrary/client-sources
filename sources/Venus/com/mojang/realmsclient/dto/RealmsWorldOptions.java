/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Objects;
import net.minecraft.client.resources.I18n;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RealmsWorldOptions
extends ValueObject {
    public Boolean field_230614_a_;
    public Boolean field_230615_b_;
    public Boolean field_230616_c_;
    public Boolean field_230617_d_;
    public Integer field_230618_e_;
    public Boolean field_230619_f_;
    public Boolean field_230620_g_;
    public Integer field_230621_h_;
    public Integer field_230622_i_;
    public String field_230623_j_;
    public long field_230624_k_;
    public String field_230625_l_;
    public boolean field_230626_m_;
    public boolean field_230627_n_;
    private static final String field_237699_o_ = null;

    public RealmsWorldOptions(Boolean bl, Boolean bl2, Boolean bl3, Boolean bl4, Integer n, Boolean bl5, Integer n2, Integer n3, Boolean bl6, String string) {
        this.field_230614_a_ = bl;
        this.field_230615_b_ = bl2;
        this.field_230616_c_ = bl3;
        this.field_230617_d_ = bl4;
        this.field_230618_e_ = n;
        this.field_230619_f_ = bl5;
        this.field_230621_h_ = n2;
        this.field_230622_i_ = n3;
        this.field_230620_g_ = bl6;
        this.field_230623_j_ = string;
    }

    public static RealmsWorldOptions func_237700_a_() {
        return new RealmsWorldOptions(true, true, true, true, 0, false, 2, 0, false, "");
    }

    public static RealmsWorldOptions func_237701_b_() {
        RealmsWorldOptions realmsWorldOptions = RealmsWorldOptions.func_237700_a_();
        realmsWorldOptions.func_230789_a_(false);
        return realmsWorldOptions;
    }

    public void func_230789_a_(boolean bl) {
        this.field_230627_n_ = bl;
    }

    public static RealmsWorldOptions func_230788_a_(JsonObject jsonObject) {
        RealmsWorldOptions realmsWorldOptions = new RealmsWorldOptions(JsonUtils.func_225170_a("pvp", jsonObject, true), JsonUtils.func_225170_a("spawnAnimals", jsonObject, true), JsonUtils.func_225170_a("spawnMonsters", jsonObject, true), JsonUtils.func_225170_a("spawnNPCs", jsonObject, true), JsonUtils.func_225172_a("spawnProtection", jsonObject, 0), JsonUtils.func_225170_a("commandBlocks", jsonObject, false), JsonUtils.func_225172_a("difficulty", jsonObject, 2), JsonUtils.func_225172_a("gameMode", jsonObject, 0), JsonUtils.func_225170_a("forceGameMode", jsonObject, false), JsonUtils.func_225171_a("slotName", jsonObject, ""));
        realmsWorldOptions.field_230624_k_ = JsonUtils.func_225169_a("worldTemplateId", jsonObject, -1L);
        realmsWorldOptions.field_230625_l_ = JsonUtils.func_225171_a("worldTemplateImage", jsonObject, field_237699_o_);
        realmsWorldOptions.field_230626_m_ = JsonUtils.func_225170_a("adventureMap", jsonObject, false);
        return realmsWorldOptions;
    }

    public String func_230787_a_(int n) {
        if (this.field_230623_j_ != null && !this.field_230623_j_.isEmpty()) {
            return this.field_230623_j_;
        }
        return this.field_230627_n_ ? I18n.format("mco.configure.world.slot.empty", new Object[0]) : this.func_230790_b_(n);
    }

    public String func_230790_b_(int n) {
        return I18n.format("mco.configure.world.slot", n);
    }

    public String func_230791_c_() {
        JsonObject jsonObject = new JsonObject();
        if (!this.field_230614_a_.booleanValue()) {
            jsonObject.addProperty("pvp", this.field_230614_a_);
        }
        if (!this.field_230615_b_.booleanValue()) {
            jsonObject.addProperty("spawnAnimals", this.field_230615_b_);
        }
        if (!this.field_230616_c_.booleanValue()) {
            jsonObject.addProperty("spawnMonsters", this.field_230616_c_);
        }
        if (!this.field_230617_d_.booleanValue()) {
            jsonObject.addProperty("spawnNPCs", this.field_230617_d_);
        }
        if (this.field_230618_e_ != 0) {
            jsonObject.addProperty("spawnProtection", this.field_230618_e_);
        }
        if (this.field_230619_f_.booleanValue()) {
            jsonObject.addProperty("commandBlocks", this.field_230619_f_);
        }
        if (this.field_230621_h_ != 2) {
            jsonObject.addProperty("difficulty", this.field_230621_h_);
        }
        if (this.field_230622_i_ != 0) {
            jsonObject.addProperty("gameMode", this.field_230622_i_);
        }
        if (this.field_230620_g_.booleanValue()) {
            jsonObject.addProperty("forceGameMode", this.field_230620_g_);
        }
        if (!Objects.equals(this.field_230623_j_, "")) {
            jsonObject.addProperty("slotName", this.field_230623_j_);
        }
        return jsonObject.toString();
    }

    public RealmsWorldOptions clone() {
        return new RealmsWorldOptions(this.field_230614_a_, this.field_230615_b_, this.field_230616_c_, this.field_230617_d_, this.field_230618_e_, this.field_230619_f_, this.field_230621_h_, this.field_230622_i_, this.field_230620_g_, this.field_230623_j_);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

