/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.ValueObject;
import net.minecraft.realms.IPersistentSerializable;

public class PlayerInfo
extends ValueObject
implements IPersistentSerializable {
    @SerializedName(value="name")
    private String field_230573_a_;
    @SerializedName(value="uuid")
    private String field_230574_b_;
    @SerializedName(value="operator")
    private boolean field_230575_c_;
    @SerializedName(value="accepted")
    private boolean field_230576_d_;
    @SerializedName(value="online")
    private boolean field_230577_e_;

    public String func_230757_a_() {
        return this.field_230573_a_;
    }

    public void func_230758_a_(String string) {
        this.field_230573_a_ = string;
    }

    public String func_230760_b_() {
        return this.field_230574_b_;
    }

    public void func_230761_b_(String string) {
        this.field_230574_b_ = string;
    }

    public boolean func_230763_c_() {
        return this.field_230575_c_;
    }

    public void func_230759_a_(boolean bl) {
        this.field_230575_c_ = bl;
    }

    public boolean func_230765_d_() {
        return this.field_230576_d_;
    }

    public void func_230762_b_(boolean bl) {
        this.field_230576_d_ = bl;
    }

    public boolean func_230766_e_() {
        return this.field_230577_e_;
    }

    public void func_230764_c_(boolean bl) {
        this.field_230577_e_ = bl;
    }
}

