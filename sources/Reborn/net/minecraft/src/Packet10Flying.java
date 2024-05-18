package net.minecraft.src;

import java.io.*;

public class Packet10Flying extends Packet
{
    public double xPosition;
    public double yPosition;
    public double zPosition;
    public double stance;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean moving;
    public boolean rotating;
    
    public Packet10Flying() {
    }
    
    public Packet10Flying(final boolean par1) {
        this.onGround = par1;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleFlying(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.onGround = (par1DataInputStream.read() != 0);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.write(this.onGround ? 1 : 0);
    }
    
    @Override
    public int getPacketSize() {
        return 1;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        return true;
    }
}
