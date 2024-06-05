package net.minecraft.network;

import java.io.*;

public interface Packet
{
    void readPacketData(final PacketBuffer p0) throws IOException;
    
    void writePacketData(final PacketBuffer p0) throws IOException;
    
    void processPacket(final INetHandler p0);
}
