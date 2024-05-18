package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.potion.*;

public class S1EPacketRemoveEntityEffect implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private int effectId;
    
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.effectId = packetBuffer.readUnsignedByte();
    }
    
    public S1EPacketRemoveEntityEffect() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.effectId);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleRemoveEntityEffect(this);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public S1EPacketRemoveEntityEffect(final int entityId, final PotionEffect potionEffect) {
        this.entityId = entityId;
        this.effectId = potionEffect.getPotionID();
    }
    
    public int getEffectId() {
        return this.effectId;
    }
}
