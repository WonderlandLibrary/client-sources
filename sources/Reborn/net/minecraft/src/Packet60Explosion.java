package net.minecraft.src;

import java.io.*;
import java.util.*;

public class Packet60Explosion extends Packet
{
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public float explosionSize;
    public List chunkPositionRecords;
    private float playerVelocityX;
    private float playerVelocityY;
    private float playerVelocityZ;
    
    public Packet60Explosion() {
    }
    
    public Packet60Explosion(final double par1, final double par3, final double par5, final float par7, final List par8List, final Vec3 par9Vec3) {
        this.explosionX = par1;
        this.explosionY = par3;
        this.explosionZ = par5;
        this.explosionSize = par7;
        this.chunkPositionRecords = new ArrayList(par8List);
        if (par9Vec3 != null) {
            this.playerVelocityX = (float)par9Vec3.xCoord;
            this.playerVelocityY = (float)par9Vec3.yCoord;
            this.playerVelocityZ = (float)par9Vec3.zCoord;
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.explosionX = par1DataInputStream.readDouble();
        this.explosionY = par1DataInputStream.readDouble();
        this.explosionZ = par1DataInputStream.readDouble();
        this.explosionSize = par1DataInputStream.readFloat();
        final int var2 = par1DataInputStream.readInt();
        this.chunkPositionRecords = new ArrayList(var2);
        final int var3 = (int)this.explosionX;
        final int var4 = (int)this.explosionY;
        final int var5 = (int)this.explosionZ;
        for (int var6 = 0; var6 < var2; ++var6) {
            final int var7 = par1DataInputStream.readByte() + var3;
            final int var8 = par1DataInputStream.readByte() + var4;
            final int var9 = par1DataInputStream.readByte() + var5;
            this.chunkPositionRecords.add(new ChunkPosition(var7, var8, var9));
        }
        this.playerVelocityX = par1DataInputStream.readFloat();
        this.playerVelocityY = par1DataInputStream.readFloat();
        this.playerVelocityZ = par1DataInputStream.readFloat();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeDouble(this.explosionX);
        par1DataOutputStream.writeDouble(this.explosionY);
        par1DataOutputStream.writeDouble(this.explosionZ);
        par1DataOutputStream.writeFloat(this.explosionSize);
        par1DataOutputStream.writeInt(this.chunkPositionRecords.size());
        final int var2 = (int)this.explosionX;
        final int var3 = (int)this.explosionY;
        final int var4 = (int)this.explosionZ;
        for (final ChunkPosition var6 : this.chunkPositionRecords) {
            final int var7 = var6.x - var2;
            final int var8 = var6.y - var3;
            final int var9 = var6.z - var4;
            par1DataOutputStream.writeByte(var7);
            par1DataOutputStream.writeByte(var8);
            par1DataOutputStream.writeByte(var9);
        }
        par1DataOutputStream.writeFloat(this.playerVelocityX);
        par1DataOutputStream.writeFloat(this.playerVelocityY);
        par1DataOutputStream.writeFloat(this.playerVelocityZ);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleExplosion(this);
    }
    
    @Override
    public int getPacketSize() {
        return 32 + this.chunkPositionRecords.size() * 3 + 3;
    }
    
    public float getPlayerVelocityX() {
        return this.playerVelocityX;
    }
    
    public float getPlayerVelocityY() {
        return this.playerVelocityY;
    }
    
    public float getPlayerVelocityZ() {
        return this.playerVelocityZ;
    }
}
