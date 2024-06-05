package net.minecraft.src;

import java.io.*;

public class Packet62LevelSound extends Packet
{
    private String soundName;
    private int effectX;
    private int effectY;
    private int effectZ;
    private float volume;
    private int pitch;
    
    public Packet62LevelSound() {
        this.effectY = Integer.MAX_VALUE;
    }
    
    public Packet62LevelSound(final String par1Str, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.effectY = Integer.MAX_VALUE;
        this.soundName = par1Str;
        this.effectX = (int)(par2 * 8.0);
        this.effectY = (int)(par4 * 8.0);
        this.effectZ = (int)(par6 * 8.0);
        this.volume = par8;
        this.pitch = (int)(par9 * 63.0f);
        if (this.pitch < 0) {
            this.pitch = 0;
        }
        if (this.pitch > 255) {
            this.pitch = 255;
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.soundName = Packet.readString(par1DataInputStream, 32);
        this.effectX = par1DataInputStream.readInt();
        this.effectY = par1DataInputStream.readInt();
        this.effectZ = par1DataInputStream.readInt();
        this.volume = par1DataInputStream.readFloat();
        this.pitch = par1DataInputStream.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.soundName, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.effectX);
        par1DataOutputStream.writeInt(this.effectY);
        par1DataOutputStream.writeInt(this.effectZ);
        par1DataOutputStream.writeFloat(this.volume);
        par1DataOutputStream.writeByte(this.pitch);
    }
    
    public String getSoundName() {
        return this.soundName;
    }
    
    public double getEffectX() {
        return this.effectX / 8.0f;
    }
    
    public double getEffectY() {
        return this.effectY / 8.0f;
    }
    
    public double getEffectZ() {
        return this.effectZ / 8.0f;
    }
    
    public float getVolume() {
        return this.volume;
    }
    
    public float getPitch() {
        return this.pitch / 63.0f;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleLevelSound(this);
    }
    
    @Override
    public int getPacketSize() {
        return 24;
    }
}
