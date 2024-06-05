package net.minecraft.src;

import java.io.*;

public class Packet12PlayerLook extends Packet10Flying
{
    public Packet12PlayerLook() {
        this.rotating = true;
    }
    
    public Packet12PlayerLook(final float par1, final float par2, final boolean par3) {
        this.yaw = par1;
        this.pitch = par2;
        this.onGround = par3;
        this.rotating = true;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.yaw = par1DataInputStream.readFloat();
        this.pitch = par1DataInputStream.readFloat();
        super.readPacketData(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeFloat(this.yaw);
        par1DataOutputStream.writeFloat(this.pitch);
        super.writePacketData(par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 9;
    }
}
