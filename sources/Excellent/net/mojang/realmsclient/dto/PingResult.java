package net.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import net.minecraft.realms.IPersistentSerializable;

import java.util.List;

public class PingResult extends ValueObject implements IPersistentSerializable
{
    @SerializedName("pingResults")
    public List<RegionPingResult> field_230571_a_ = Lists.newArrayList();
    @SerializedName("worldIds")
    public List<Long> field_230572_b_ = Lists.newArrayList();
}
