package net.minecraft.src;

import java.io.*;

public class Packet103SetSlot extends Packet
{
    public int windowId;
    public int itemSlot;
    public ItemStack myItemStack;
    
    public Packet103SetSlot() {
    }
    
    public Packet103SetSlot(final int par1, final int par2, final ItemStack par3ItemStack) {
        this.windowId = par1;
        this.itemSlot = par2;
        this.myItemStack = ((par3ItemStack == null) ? par3ItemStack : par3ItemStack.copy());
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSetSlot(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = par1DataInputStream.readByte();
        this.itemSlot = par1DataInputStream.readShort();
        this.myItemStack = Packet.readItemStack(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId);
        par1DataOutputStream.writeShort(this.itemSlot);
        Packet.writeItemStack(this.myItemStack, par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 8;
    }
}
