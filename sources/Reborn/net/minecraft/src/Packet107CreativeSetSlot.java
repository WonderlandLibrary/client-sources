package net.minecraft.src;

import java.io.*;

public class Packet107CreativeSetSlot extends Packet
{
    public int slot;
    public ItemStack itemStack;
    
    public Packet107CreativeSetSlot() {
    }
    
    public Packet107CreativeSetSlot(final int par1, final ItemStack par2ItemStack) {
        this.slot = par1;
        this.itemStack = ((par2ItemStack != null) ? par2ItemStack.copy() : null);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleCreativeSetSlot(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.slot = par1DataInputStream.readShort();
        this.itemStack = Packet.readItemStack(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeShort(this.slot);
        Packet.writeItemStack(this.itemStack, par1DataOutputStream);
    }
    
    @Override
    public int getPacketSize() {
        return 8;
    }
}
