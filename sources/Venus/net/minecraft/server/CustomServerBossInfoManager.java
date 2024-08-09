/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.CustomServerBossInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CustomServerBossInfoManager {
    private final Map<ResourceLocation, CustomServerBossInfo> bars = Maps.newHashMap();

    @Nullable
    public CustomServerBossInfo get(ResourceLocation resourceLocation) {
        return this.bars.get(resourceLocation);
    }

    public CustomServerBossInfo add(ResourceLocation resourceLocation, ITextComponent iTextComponent) {
        CustomServerBossInfo customServerBossInfo = new CustomServerBossInfo(resourceLocation, iTextComponent);
        this.bars.put(resourceLocation, customServerBossInfo);
        return customServerBossInfo;
    }

    public void remove(CustomServerBossInfo customServerBossInfo) {
        this.bars.remove(customServerBossInfo.getId());
    }

    public Collection<ResourceLocation> getIDs() {
        return this.bars.keySet();
    }

    public Collection<CustomServerBossInfo> getBossbars() {
        return this.bars.values();
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (CustomServerBossInfo customServerBossInfo : this.bars.values()) {
            compoundNBT.put(customServerBossInfo.getId().toString(), customServerBossInfo.write());
        }
        return compoundNBT;
    }

    public void read(CompoundNBT compoundNBT) {
        for (String string : compoundNBT.keySet()) {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            this.bars.put(resourceLocation, CustomServerBossInfo.read(compoundNBT.getCompound(string), resourceLocation));
        }
    }

    public void onPlayerLogin(ServerPlayerEntity serverPlayerEntity) {
        for (CustomServerBossInfo customServerBossInfo : this.bars.values()) {
            customServerBossInfo.onPlayerLogin(serverPlayerEntity);
        }
    }

    public void onPlayerLogout(ServerPlayerEntity serverPlayerEntity) {
        for (CustomServerBossInfo customServerBossInfo : this.bars.values()) {
            customServerBossInfo.onPlayerLogout(serverPlayerEntity);
        }
    }
}

