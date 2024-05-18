package net.minecraft.src;

import java.io.*;

public class Packet5PlayerInventory extends Packet
{
    public int entityID;
    public int slot;
    private ItemStack itemSlot;
    
    public Packet5PlayerInventory() {
    }
    
    public Packet5PlayerInventory(final int par1, final int par2, final ItemStack par3ItemStack) {
        this.entityID = par1;
        this.slot = par2;
        this.itemSlot = ((par3ItemStack == null) ? null : par3ItemStack.copy());
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityID = par1DataInputStream.readInt();
        this.slot = par1DataInputStream.readShort();
        this.itemSlot = Packet.readItemStack(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityID);
        par1DataOutputStream.writeShort(this.slot);
        Packet.writeItemStack(this.itemSlot, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handlePlayerInventory(this);
    }
    
    @Override
    public int getPacketSize() {
        return 8;
    }
    
    public ItemStack getItemSlot() {
        return this.itemSlot;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet5PlayerInventory var2 = (Packet5PlayerInventory)par1Packet;
        return var2.entityID == this.entityID && var2.slot == this.slot;
    }
}
