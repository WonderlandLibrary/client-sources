package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;

public class S2FPacketSetSlot implements Packet<INetHandlerPlayClient>
{
    private ItemStack item;
    private int slot;
    private int windowId;
    
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slot = packetBuffer.readShort();
        this.item = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSetSlot(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S2FPacketSetSlot() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slot);
        packetBuffer.writeItemStackToBuffer(this.item);
    }
    
    public S2FPacketSetSlot(final int windowId, final int slot, final ItemStack itemStack) {
        this.windowId = windowId;
        this.slot = slot;
        ItemStack copy;
        if (itemStack == null) {
            copy = null;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            copy = itemStack.copy();
        }
        this.item = copy;
    }
    
    public int func_149173_d() {
        return this.slot;
    }
    
    public ItemStack func_149174_e() {
        return this.item;
    }
    
    public int func_149175_c() {
        return this.windowId;
    }
}
