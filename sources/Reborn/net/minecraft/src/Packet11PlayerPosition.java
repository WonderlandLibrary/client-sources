package net.minecraft.src;

import java.io.*;

public class Packet11PlayerPosition extends Packet10Flying
{
    public Packet11PlayerPosition() {
        this.moving = true;
    }
    
    public Packet11PlayerPosition(final double par1, final double par3, final double par5, final double par7, final boolean par9) {
        this.xPosition = par1;
        this.yPosition = par3;
        this.stance = par5;
        this.zPosition = par7;
        this.onGround = par9;
        this.moving = true;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readDouble();
        this.yPosition = par1DataInputStream.readDouble();
        this.stance = par1DataInputStream.readDouble();
        this.zPosition = par1DataInputStream.readDouble();
        super.readPacketData(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeDouble(this.xPosition);
        par1DataOutputStream.writeDouble(this.yPosition);
        par1DataOutputStream.writeDouble(this.stance);
        par1DataOutputStream.writeDouble(this.zPosition);
        super.writePacketData(par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 33;
    }
}
