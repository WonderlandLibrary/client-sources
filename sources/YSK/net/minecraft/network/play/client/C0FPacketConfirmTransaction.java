package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class C0FPacketConfirmTransaction implements Packet<INetHandlerPlayServer>
{
    private boolean accepted;
    private int windowId;
    private short uid;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C0FPacketConfirmTransaction(final int windowId, final short uid, final boolean accepted) {
        this.windowId = windowId;
        this.uid = uid;
        this.accepted = accepted;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.uid);
        int n;
        if (this.accepted) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        packetBuffer.writeByte(n);
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public C0FPacketConfirmTransaction() {
    }
    
    public short getUid() {
        return this.uid;
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.uid = packetBuffer.readShort();
        int accepted;
        if (packetBuffer.readByte() != 0) {
            accepted = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            accepted = "".length();
        }
        this.accepted = (accepted != 0);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processConfirmTransaction(this);
    }
}
