/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SAdvancementInfoPacket
implements IPacket<IClientPlayNetHandler> {
    private boolean firstSync;
    private Map<ResourceLocation, Advancement.Builder> advancementsToAdd;
    private Set<ResourceLocation> advancementsToRemove;
    private Map<ResourceLocation, AdvancementProgress> progressUpdates;

    public SAdvancementInfoPacket() {
    }

    public SAdvancementInfoPacket(boolean bl, Collection<Advancement> collection, Set<ResourceLocation> set, Map<ResourceLocation, AdvancementProgress> map) {
        this.firstSync = bl;
        this.advancementsToAdd = Maps.newHashMap();
        for (Advancement advancement : collection) {
            this.advancementsToAdd.put(advancement.getId(), advancement.copy());
        }
        this.advancementsToRemove = set;
        this.progressUpdates = Maps.newHashMap(map);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleAdvancementInfo(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        ResourceLocation resourceLocation;
        int n;
        this.firstSync = packetBuffer.readBoolean();
        this.advancementsToAdd = Maps.newHashMap();
        this.advancementsToRemove = Sets.newLinkedHashSet();
        this.progressUpdates = Maps.newHashMap();
        int n2 = packetBuffer.readVarInt();
        for (n = 0; n < n2; ++n) {
            resourceLocation = packetBuffer.readResourceLocation();
            Advancement.Builder builder = Advancement.Builder.readFrom(packetBuffer);
            this.advancementsToAdd.put(resourceLocation, builder);
        }
        n2 = packetBuffer.readVarInt();
        for (n = 0; n < n2; ++n) {
            resourceLocation = packetBuffer.readResourceLocation();
            this.advancementsToRemove.add(resourceLocation);
        }
        n2 = packetBuffer.readVarInt();
        for (n = 0; n < n2; ++n) {
            resourceLocation = packetBuffer.readResourceLocation();
            this.progressUpdates.put(resourceLocation, AdvancementProgress.fromNetwork(packetBuffer));
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.firstSync);
        packetBuffer.writeVarInt(this.advancementsToAdd.size());
        for (Map.Entry<ResourceLocation, Advancement.Builder> object : this.advancementsToAdd.entrySet()) {
            ResourceLocation resourceLocation = object.getKey();
            Advancement.Builder builder = object.getValue();
            packetBuffer.writeResourceLocation(resourceLocation);
            builder.writeTo(packetBuffer);
        }
        packetBuffer.writeVarInt(this.advancementsToRemove.size());
        for (ResourceLocation resourceLocation : this.advancementsToRemove) {
            packetBuffer.writeResourceLocation(resourceLocation);
        }
        packetBuffer.writeVarInt(this.progressUpdates.size());
        for (Map.Entry entry : this.progressUpdates.entrySet()) {
            packetBuffer.writeResourceLocation((ResourceLocation)entry.getKey());
            ((AdvancementProgress)entry.getValue()).serializeToNetwork(packetBuffer);
        }
    }

    public Map<ResourceLocation, Advancement.Builder> getAdvancementsToAdd() {
        return this.advancementsToAdd;
    }

    public Set<ResourceLocation> getAdvancementsToRemove() {
        return this.advancementsToRemove;
    }

    public Map<ResourceLocation, AdvancementProgress> getProgressUpdates() {
        return this.progressUpdates;
    }

    public boolean isFirstSync() {
        return this.firstSync;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

