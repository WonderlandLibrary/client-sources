/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.ValueObject;
import net.minecraft.realms.IPersistentSerializable;

public class RealmsDescriptionDto
extends ValueObject
implements IPersistentSerializable {
    @SerializedName(value="name")
    public String field_230578_a_;
    @SerializedName(value="description")
    public String field_230579_b_;

    public RealmsDescriptionDto(String string, String string2) {
        this.field_230578_a_ = string;
        this.field_230579_b_ = string2;
    }
}

