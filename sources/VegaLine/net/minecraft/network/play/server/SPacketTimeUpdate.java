/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import ru.govno.client.utils.TPSDetect;

public class SPacketTimeUpdate
implements Packet<INetHandlerPlayClient> {
    private long totalWorldTime;
    private long worldTime;

    public SPacketTimeUpdate() {
    }

    public SPacketTimeUpdate(long totalWorldTimeIn, long worldTimeIn, boolean p_i46902_5_) {
        this.totalWorldTime = totalWorldTimeIn;
        this.worldTime = worldTimeIn;
        if (!p_i46902_5_) {
            this.worldTime = -this.worldTime;
            if (this.worldTime == 0L) {
                this.worldTime = -1L;
            }
        }
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.totalWorldTime = buf.readLong();
        this.worldTime = buf.readLong();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeLong(this.totalWorldTime);
        buf.writeLong(this.worldTime);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        TPSDetect.onTimeUpdate();
        handler.handleTimeUpdate(this);
    }

    public long getTotalWorldTime() {
        return this.totalWorldTime;
    }

    public long getWorldTime() {
        return this.worldTime;
    }
}

