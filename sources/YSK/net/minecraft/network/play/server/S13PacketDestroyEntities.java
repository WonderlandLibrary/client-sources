package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S13PacketDestroyEntities implements Packet<INetHandlerPlayClient>
{
    private int[] entityIDs;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleDestroyEntities(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityIDs = new int[packetBuffer.readVarIntFromBuffer()];
        int i = "".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (i < this.entityIDs.length) {
            this.entityIDs[i] = packetBuffer.readVarIntFromBuffer();
            ++i;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityIDs.length);
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < this.entityIDs.length) {
            packetBuffer.writeVarIntToBuffer(this.entityIDs[i]);
            ++i;
        }
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
    
    public S13PacketDestroyEntities() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S13PacketDestroyEntities(final int... entityIDs) {
        this.entityIDs = entityIDs;
    }
    
    public int[] getEntityIDs() {
        return this.entityIDs;
    }
}
