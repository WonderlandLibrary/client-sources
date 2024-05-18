package net.minecraft.src;

import java.util.*;
import java.io.*;

public class Packet104WindowItems extends Packet
{
    public int windowId;
    public ItemStack[] itemStack;
    
    public Packet104WindowItems() {
    }
    
    public Packet104WindowItems(final int par1, final List par2List) {
        this.windowId = par1;
        this.itemStack = new ItemStack[par2List.size()];
        for (int var3 = 0; var3 < this.itemStack.length; ++var3) {
            final ItemStack var4 = par2List.get(var3);
            this.itemStack[var3] = ((var4 == null) ? null : var4.copy());
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = par1DataInputStream.readByte();
        final short var2 = par1DataInputStream.readShort();
        this.itemStack = new ItemStack[var2];
        for (int var3 = 0; var3 < var2; ++var3) {
            this.itemStack[var3] = Packet.readItemStack(par1DataInputStream);
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId);
        par1DataOutputStream.writeShort(this.itemStack.length);
        for (int var2 = 0; var2 < this.itemStack.length; ++var2) {
            Packet.writeItemStack(this.itemStack[var2], par1DataOutputStream);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleWindowItems(this);
    }
    
    @Override
    public int getPacketSize() {
        return 3 + this.itemStack.length * 5;
    }
}
