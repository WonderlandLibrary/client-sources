package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S32PacketConfirmTransaction implements Packet<INetHandlerPlayClient>
{
    private short actionNumber;
    private boolean field_148893_c;
    private int windowId;
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public boolean func_148888_e() {
        return this.field_148893_c;
    }
    
    public short getActionNumber() {
        return this.actionNumber;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeBoolean(this.field_148893_c);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.actionNumber = packetBuffer.readShort();
        this.field_148893_c = packetBuffer.readBoolean();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleConfirmTransaction(this);
    }
    
    public S32PacketConfirmTransaction() {
    }
    
    public S32PacketConfirmTransaction(final int windowId, final short actionNumber, final boolean field_148893_c) {
        this.windowId = windowId;
        this.actionNumber = actionNumber;
        this.field_148893_c = field_148893_c;
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
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
