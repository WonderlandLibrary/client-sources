/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.network;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public interface Packet {
    public void readPacketData(PacketBuffer var1) throws IOException;

    public void writePacketData(PacketBuffer var1) throws IOException;

    public void processPacket(INetHandler var1);
}

