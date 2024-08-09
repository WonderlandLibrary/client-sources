/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.RegionPingResult;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.List;
import net.minecraft.realms.IPersistentSerializable;

public class PingResult
extends ValueObject
implements IPersistentSerializable {
    @SerializedName(value="pingResults")
    public List<RegionPingResult> field_230571_a_ = Lists.newArrayList();
    @SerializedName(value="worldIds")
    public List<Long> field_230572_b_ = Lists.newArrayList();
}

