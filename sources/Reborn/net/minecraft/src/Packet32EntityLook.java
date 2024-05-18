package net.minecraft.src;

import java.io.*;

public class Packet32EntityLook extends Packet30Entity
{
    public Packet32EntityLook() {
        this.rotating = true;
    }
    
    public Packet32EntityLook(final int par1, final byte par2, final byte par3) {
        super(par1);
        this.yaw = par2;
        this.pitch = par3;
        this.rotating = true;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        super.readPacketData(par1DataInputStream);
        this.yaw = par1DataInputStream.readByte();
        this.pitch = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        super.writePacketData(par1DataOutputStream);
        par1DataOutputStream.writeByte(this.yaw);
        par1DataOutputStream.writeByte(this.pitch);
    }
    
    @Override
    public int getPacketSize() {
        return 6;
    }
}
