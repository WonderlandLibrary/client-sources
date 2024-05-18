package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S31PacketWindowProperty implements Packet<INetHandlerPlayClient>
{
    private int windowId;
    private int varValue;
    private int varIndex;
    
    public int getVarIndex() {
        return this.varIndex;
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public S31PacketWindowProperty() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.varIndex = packetBuffer.readShort();
        this.varValue = packetBuffer.readShort();
    }
    
    public S31PacketWindowProperty(final int windowId, final int varIndex, final int varValue) {
        this.windowId = windowId;
        this.varIndex = varIndex;
        this.varValue = varValue;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.varIndex);
        packetBuffer.writeShort(this.varValue);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public int getVarValue() {
        return this.varValue;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWindowProperty(this);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
}
