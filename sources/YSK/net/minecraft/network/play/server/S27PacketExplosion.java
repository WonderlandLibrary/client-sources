package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;

public class S27PacketExplosion implements Packet<INetHandlerPlayClient>
{
    private double posY;
    private float field_149153_g;
    private double posX;
    private float field_149152_f;
    private float field_149159_h;
    private double posZ;
    private float strength;
    private List<BlockPos> affectedBlockPositions;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.posX = packetBuffer.readFloat();
        this.posY = packetBuffer.readFloat();
        this.posZ = packetBuffer.readFloat();
        this.strength = packetBuffer.readFloat();
        final int int1 = packetBuffer.readInt();
        this.affectedBlockPositions = (List<BlockPos>)Lists.newArrayListWithCapacity(int1);
        final int n = (int)this.posX;
        final int n2 = (int)this.posY;
        final int n3 = (int)this.posZ;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < int1) {
            this.affectedBlockPositions.add(new BlockPos(packetBuffer.readByte() + n, packetBuffer.readByte() + n2, packetBuffer.readByte() + n3));
            ++i;
        }
        this.field_149152_f = packetBuffer.readFloat();
        this.field_149153_g = packetBuffer.readFloat();
        this.field_149159_h = packetBuffer.readFloat();
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public float getStrength() {
        return this.strength;
    }
    
    public double getY() {
        return this.posY;
    }
    
    public double getX() {
        return this.posX;
    }
    
    public S27PacketExplosion(final double posX, final double posY, final double posZ, final float strength, final List<BlockPos> list, final Vec3 vec3) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.strength = strength;
        this.affectedBlockPositions = (List<BlockPos>)Lists.newArrayList((Iterable)list);
        if (vec3 != null) {
            this.field_149152_f = (float)vec3.xCoord;
            this.field_149153_g = (float)vec3.yCoord;
            this.field_149159_h = (float)vec3.zCoord;
        }
    }
    
    public float func_149149_c() {
        return this.field_149152_f;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleExplosion(this);
    }
    
    public float func_149144_d() {
        return this.field_149153_g;
    }
    
    public double getZ() {
        return this.posZ;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat((float)this.posX);
        packetBuffer.writeFloat((float)this.posY);
        packetBuffer.writeFloat((float)this.posZ);
        packetBuffer.writeFloat(this.strength);
        packetBuffer.writeInt(this.affectedBlockPositions.size());
        final int n = (int)this.posX;
        final int n2 = (int)this.posY;
        final int n3 = (int)this.posZ;
        final Iterator<BlockPos> iterator = this.affectedBlockPositions.iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos blockPos = iterator.next();
            final int n4 = blockPos.getX() - n;
            final int n5 = blockPos.getY() - n2;
            final int n6 = blockPos.getZ() - n3;
            packetBuffer.writeByte(n4);
            packetBuffer.writeByte(n5);
            packetBuffer.writeByte(n6);
        }
        packetBuffer.writeFloat(this.field_149152_f);
        packetBuffer.writeFloat(this.field_149153_g);
        packetBuffer.writeFloat(this.field_149159_h);
    }
    
    public S27PacketExplosion() {
    }
    
    public float func_149147_e() {
        return this.field_149159_h;
    }
    
    public List<BlockPos> getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }
}
