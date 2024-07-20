/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public interface Packet<T extends INetHandler> {
    public void readPacketData(PacketBuffer var1) throws IOException;

    public void writePacketData(PacketBuffer var1) throws IOException;

    public void processPacket(T var1);
}

