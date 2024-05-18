package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.*;

public class S2CPacketSpawnGlobalEntity implements Packet<INetHandlerPlayClient>
{
    private int x;
    private int type;
    private int y;
    private int entityId;
    private int z;
    
    public int func_149051_d() {
        return this.x;
    }
    
    public int func_149050_e() {
        return this.y;
    }
    
    public int func_149052_c() {
        return this.entityId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readByte();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
    }
    
    public int func_149049_f() {
        return this.z;
    }
    
    public int func_149053_g() {
        return this.type;
    }
    
    public S2CPacketSpawnGlobalEntity(final Entity entity) {
        this.entityId = entity.getEntityId();
        this.x = MathHelper.floor_double(entity.posX * 32.0);
        this.y = MathHelper.floor_double(entity.posY * 32.0);
        this.z = MathHelper.floor_double(entity.posZ * 32.0);
        if (entity instanceof EntityLightningBolt) {
            this.type = " ".length();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnGlobalEntity(this);
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S2CPacketSpawnGlobalEntity() {
    }
}
