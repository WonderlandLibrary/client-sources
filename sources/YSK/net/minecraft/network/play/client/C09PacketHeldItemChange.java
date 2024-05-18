package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C09PacketHeldItemChange implements Packet<INetHandlerPlayServer>
{
    private int slotId;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public C09PacketHeldItemChange(final int slotId) {
        this.slotId = slotId;
    }
    
    public C09PacketHeldItemChange() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processHeldItemChange(this);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
