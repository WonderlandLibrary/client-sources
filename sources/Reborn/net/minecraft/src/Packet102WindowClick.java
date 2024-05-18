package net.minecraft.src;

import java.io.*;

public class Packet102WindowClick extends Packet
{
    public int window_Id;
    public int inventorySlot;
    public int mouseClick;
    public short action;
    public ItemStack itemStack;
    public int holdingShift;
    
    public Packet102WindowClick() {
    }
    
    public Packet102WindowClick(final int par1, final int par2, final int par3, final int par4, final ItemStack par5ItemStack, final short par6) {
        this.window_Id = par1;
        this.inventorySlot = par2;
        this.mouseClick = par3;
        this.itemStack = ((par5ItemStack != null) ? par5ItemStack.copy() : null);
        this.action = par6;
        this.holdingShift = par4;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleWindowClick(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.window_Id = par1DataInputStream.readByte();
        this.inventorySlot = par1DataInputStream.readShort();
        this.mouseClick = par1DataInputStream.readByte();
        this.action = par1DataInputStream.readShort();
        this.holdingShift = par1DataInputStream.readByte();
        this.itemStack = Packet.readItemStack(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.window_Id);
        par1DataOutputStream.writeShort(this.inventorySlot);
        par1DataOutputStream.writeByte(this.mouseClick);
        par1DataOutputStream.writeShort(this.action);
        par1DataOutputStream.writeByte(this.holdingShift);
        Packet.writeItemStack(this.itemStack, par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 11;
    }
}
