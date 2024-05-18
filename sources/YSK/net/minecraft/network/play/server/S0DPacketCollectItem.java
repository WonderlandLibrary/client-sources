package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;

public class S0DPacketCollectItem implements Packet<INetHandlerPlayClient>
{
    private int collectedItemEntityId;
    private int entityId;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleCollectItem(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S0DPacketCollectItem(final int collectedItemEntityId, final int entityId) {
        this.collectedItemEntityId = collectedItemEntityId;
        this.entityId = entityId;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getCollectedItemEntityID() {
        return this.collectedItemEntityId;
    }
    
    public S0DPacketCollectItem() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.collectedItemEntityId);
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.collectedItemEntityId = packetBuffer.readVarIntFromBuffer();
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }
}
