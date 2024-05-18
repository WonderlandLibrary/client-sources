package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;

public class C10PacketCreativeInventoryAction implements Packet<INetHandlerPlayServer>
{
    private int slotId;
    private ItemStack stack;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
        this.stack = packetBuffer.readItemStackFromBuffer();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processCreativeInventoryAction(this);
    }
    
    public C10PacketCreativeInventoryAction() {
    }
    
    public C10PacketCreativeInventoryAction(final int slotId, final ItemStack itemStack) {
        this.slotId = slotId;
        ItemStack copy;
        if (itemStack != null) {
            copy = itemStack.copy();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            copy = null;
        }
        this.stack = copy;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public int getSlotId() {
        return this.slotId;
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeItemStackToBuffer(this.stack);
    }
}
