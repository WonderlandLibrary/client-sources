package net.minecraft.network;

import java.io.*;

public interface Packet<T extends INetHandler>
{
    void writePacketData(final PacketBuffer p0) throws IOException;
    
    void readPacketData(final PacketBuffer p0) throws IOException;
    
    void processPacket(final T p0);
}
