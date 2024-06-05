package net.minecraft.src;

import java.io.*;

public class Packet15Place extends Packet
{
    private int xPosition;
    private int yPosition;
    private int zPosition;
    private int direction;
    private ItemStack itemStack;
    private float xOffset;
    private float yOffset;
    private float zOffset;
    
    public Packet15Place() {
    }
    
    public Packet15Place(final int par1, final int par2, final int par3, final int par4, final ItemStack par5ItemStack, final float par6, final float par7, final float par8) {
        this.xPosition = par1;
        this.yPosition = par2;
        this.zPosition = par3;
        this.direction = par4;
        this.itemStack = ((par5ItemStack != null) ? par5ItemStack.copy() : null);
        this.xOffset = par6;
        this.yOffset = par7;
        this.zOffset = par8;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.read();
        this.zPosition = par1DataInputStream.readInt();
        this.direction = par1DataInputStream.read();
        this.itemStack = Packet.readItemStack(par1DataInputStream);
        this.xOffset = par1DataInputStream.read() / 16.0f;
        this.yOffset = par1DataInputStream.read() / 16.0f;
        this.zOffset = par1DataInputStream.read() / 16.0f;
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.write(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.write(this.direction);
        Packet.writeItemStack(this.itemStack, par1DataOutputStream);
        par1DataOutputStream.write((int)(this.xOffset * 16.0f));
        par1DataOutputStream.write((int)(this.yOffset * 16.0f));
        par1DataOutputStream.write((int)(this.zOffset * 16.0f));
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handlePlace(this);
    }
    
    @Override
    public int getPacketSize() {
        return 19;
    }
    
    public int getXPosition() {
        return this.xPosition;
    }
    
    public int getYPosition() {
        return this.yPosition;
    }
    
    public int getZPosition() {
        return this.zPosition;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public float getXOffset() {
        return this.xOffset;
    }
    
    public float getYOffset() {
        return this.yOffset;
    }
    
    public float getZOffset() {
        return this.zOffset;
    }
}
