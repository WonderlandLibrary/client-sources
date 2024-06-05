package net.minecraft.src;

import java.io.*;

public class Packet108EnchantItem extends Packet
{
    public int windowId;
    public int enchantment;
    
    public Packet108EnchantItem() {
    }
    
    public Packet108EnchantItem(final int par1, final int par2) {
        this.windowId = par1;
        this.enchantment = par2;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEnchantItem(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = par1DataInputStream.readByte();
        this.enchantment = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId);
        par1DataOutputStream.writeByte(this.enchantment);
    }
    
    @Override
    public int getPacketSize() {
        return 2;
    }
}
