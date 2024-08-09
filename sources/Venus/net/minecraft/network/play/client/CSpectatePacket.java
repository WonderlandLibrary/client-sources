/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.world.server.ServerWorld;

public class CSpectatePacket
implements IPacket<IServerPlayNetHandler> {
    private UUID id;

    public CSpectatePacket() {
    }

    public CSpectatePacket(UUID uUID) {
        this.id = uUID;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readUniqueId();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeUniqueId(this.id);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.handleSpectate(this);
    }

    @Nullable
    public Entity getEntity(ServerWorld serverWorld) {
        return serverWorld.getEntityByUuid(this.id);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

