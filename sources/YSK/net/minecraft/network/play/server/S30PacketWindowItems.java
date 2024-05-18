package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.item.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.*;

public class S30PacketWindowItems implements Packet<INetHandlerPlayClient>
{
    private ItemStack[] itemStacks;
    private int windowId;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        final short short1 = packetBuffer.readShort();
        this.itemStacks = new ItemStack[short1];
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < short1) {
            this.itemStacks[i] = packetBuffer.readItemStackFromBuffer();
            ++i;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S30PacketWindowItems(final int windowId, final List<ItemStack> list) {
        this.windowId = windowId;
        this.itemStacks = new ItemStack[list.size()];
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < this.itemStacks.length) {
            final ItemStack itemStack = list.get(i);
            final ItemStack[] itemStacks = this.itemStacks;
            final int n = i;
            ItemStack copy;
            if (itemStack == null) {
                copy = null;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                copy = itemStack.copy();
            }
            itemStacks[n] = copy;
            ++i;
        }
    }
    
    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.itemStacks.length);
        final ItemStack[] itemStacks;
        final int length = (itemStacks = this.itemStacks).length;
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < length) {
            packetBuffer.writeItemStackToBuffer(itemStacks[i]);
            ++i;
        }
    }
    
    public int func_148911_c() {
        return this.windowId;
    }
    
    public S30PacketWindowItems() {
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleWindowItems(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
}
