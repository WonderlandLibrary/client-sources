package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S09PacketHeldItemChange implements Packet<INetHandlerPlayClient>
{
    private int heldItemHotbarIndex;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.heldItemHotbarIndex = packetBuffer.readByte();
    }
    
    public S09PacketHeldItemChange() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleHeldItemChange(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.heldItemHotbarIndex);
    }
    
    public int getHeldItemHotbarIndex() {
        return this.heldItemHotbarIndex;
    }
    
    public S09PacketHeldItemChange(final int heldItemHotbarIndex) {
        this.heldItemHotbarIndex = heldItemHotbarIndex;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
