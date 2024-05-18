package net.minecraft.network.status.client;

import net.minecraft.network.status.*;
import java.io.*;
import net.minecraft.network.*;

public class C01PacketPing implements Packet<INetHandlerStatusServer>
{
    private long clientTime;
    
    public C01PacketPing() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusServer)netHandler);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public long getClientTime() {
        return this.clientTime;
    }
    
    public C01PacketPing(final long clientTime) {
        this.clientTime = clientTime;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }
    
    @Override
    public void processPacket(final INetHandlerStatusServer netHandlerStatusServer) {
        netHandlerStatusServer.processPing(this);
    }
}
