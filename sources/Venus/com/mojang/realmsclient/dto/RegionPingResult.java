/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Locale;
import net.minecraft.realms.IPersistentSerializable;

public class RegionPingResult
extends ValueObject
implements IPersistentSerializable {
    @SerializedName(value="regionName")
    private final String field_230632_a_;
    @SerializedName(value="ping")
    private final int field_230633_b_;

    public RegionPingResult(String string, int n) {
        this.field_230632_a_ = string;
        this.field_230633_b_ = n;
    }

    public int func_230792_a_() {
        return this.field_230633_b_;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s --> %.2f ms", this.field_230632_a_, Float.valueOf(this.field_230633_b_));
    }
}

