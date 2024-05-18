package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class S11PacketSpawnExperienceOrb implements Packet<INetHandlerPlayClient>
{
    private int posZ;
    private int posX;
    private int entityID;
    private int posY;
    private int xpValue;
    
    public int getZ() {
        return this.posZ;
    }
    
    public int getY() {
        return this.posY;
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeShort(this.xpValue);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnExperienceOrb(this);
    }
    
    public S11PacketSpawnExperienceOrb(final EntityXPOrb entityXPOrb) {
        this.entityID = entityXPOrb.getEntityId();
        this.posX = MathHelper.floor_double(entityXPOrb.posX * 32.0);
        this.posY = MathHelper.floor_double(entityXPOrb.posY * 32.0);
        this.posZ = MathHelper.floor_double(entityXPOrb.posZ * 32.0);
        this.xpValue = entityXPOrb.getXpValue();
    }
    
    public int getX() {
        return this.posX;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S11PacketSpawnExperienceOrb() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.xpValue = packetBuffer.readShort();
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    public int getXPValue() {
        return this.xpValue;
    }
}
