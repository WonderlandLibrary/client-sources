/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CPlayerAbilitiesPacket
implements IPacket<IServerPlayNetHandler> {
    private boolean flying;

    public CPlayerAbilitiesPacket() {
    }

    public CPlayerAbilitiesPacket(PlayerAbilities playerAbilities) {
        this.flying = playerAbilities.isFlying;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        byte by = packetBuffer.readByte();
        this.flying = (by & 2) != 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int n = 0;
        if (this.flying) {
            n = (byte)(n | 2);
        }
        packetBuffer.writeByte(n);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processPlayerAbilities(this);
    }

    public boolean isFlying() {
        return this.flying;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

