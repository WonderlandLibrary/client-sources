/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.ValueObject;
import net.minecraft.realms.IPersistentSerializable;

public class RealmsWorldResetDto
extends ValueObject
implements IPersistentSerializable {
    @SerializedName(value="seed")
    private final String field_230628_a_;
    @SerializedName(value="worldTemplateId")
    private final long field_230629_b_;
    @SerializedName(value="levelType")
    private final int field_230630_c_;
    @SerializedName(value="generateStructures")
    private final boolean field_230631_d_;

    public RealmsWorldResetDto(String string, long l, int n, boolean bl) {
        this.field_230628_a_ = string;
        this.field_230629_b_ = l;
        this.field_230630_c_ = n;
        this.field_230631_d_ = bl;
    }
}

