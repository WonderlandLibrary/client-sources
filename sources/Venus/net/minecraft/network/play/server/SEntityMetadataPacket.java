/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.EntityDataManager;

public class SEntityMetadataPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private List<EntityDataManager.DataEntry<?>> dataManagerEntries;

    public SEntityMetadataPacket() {
    }

    public SEntityMetadataPacket(int n, EntityDataManager entityDataManager, boolean bl) {
        this.entityId = n;
        if (bl) {
            this.dataManagerEntries = entityDataManager.getAll();
            entityDataManager.setClean();
        } else {
            this.dataManagerEntries = entityDataManager.getDirty();
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.dataManagerEntries = EntityDataManager.readEntries(packetBuffer);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        EntityDataManager.writeEntries(this.dataManagerEntries, packetBuffer);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityMetadata(this);
    }

    public List<EntityDataManager.DataEntry<?>> getDataManagerEntries() {
        return this.dataManagerEntries;
    }

    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

