package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S03PacketTimeUpdate implements Packet<INetHandlerPlayClient>
{
    private long worldTime;
    private long totalWorldTime;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTimeUpdate(this);
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public long getTotalWorldTime() {
        return this.totalWorldTime;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public long getWorldTime() {
        return this.worldTime;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.totalWorldTime);
        packetBuffer.writeLong(this.worldTime);
    }
    
    public S03PacketTimeUpdate(final long totalWorldTime, final long worldTime, final boolean b) {
        this.totalWorldTime = totalWorldTime;
        this.worldTime = worldTime;
        if (!b) {
            this.worldTime = -this.worldTime;
            if (this.worldTime == 0L) {
                this.worldTime = -1L;
            }
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.totalWorldTime = packetBuffer.readLong();
        this.worldTime = packetBuffer.readLong();
    }
    
    public S03PacketTimeUpdate() {
    }
}
