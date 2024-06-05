package net.minecraft.src;

import java.io.*;

public class Packet13PlayerLookMove extends Packet10Flying
{
    public Packet13PlayerLookMove() {
        this.rotating = true;
        this.moving = true;
    }
    
    public Packet13PlayerLookMove(final double par1, final double par3, final double par5, final double par7, final float par9, final float par10, final boolean par11) {
        this.xPosition = par1;
        this.yPosition = par3;
        this.stance = par5;
        this.zPosition = par7;
        this.yaw = par9;
        this.pitch = par10;
        this.onGround = par11;
        this.rotating = true;
        this.moving = true;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readDouble();
        this.yPosition = par1DataInputStream.readDouble();
        this.stance = par1DataInputStream.readDouble();
        this.zPosition = par1DataInputStream.readDouble();
        this.yaw = par1DataInputStream.readFloat();
        this.pitch = par1DataInputStream.readFloat();
        super.readPacketData(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeDouble(this.xPosition);
        par1DataOutputStream.writeDouble(this.yPosition);
        par1DataOutputStream.writeDouble(this.stance);
        par1DataOutputStream.writeDouble(this.zPosition);
        par1DataOutputStream.writeFloat(this.yaw);
        par1DataOutputStream.writeFloat(this.pitch);
        super.writePacketData(par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 41;
    }
}
