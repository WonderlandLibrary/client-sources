package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.*;

public class C0EPacketClickWindow implements Packet<INetHandlerPlayServer>
{
    private int mode;
    private ItemStack clickedItem;
    private int slotId;
    private int usedButton;
    private int windowId;
    private short actionNumber;
    
    public ItemStack getClickedItem() {
        return this.clickedItem;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slotId = packetBuffer.readShort();
        this.usedButton = packetBuffer.readByte();
        this.actionNumber = packetBuffer.readShort();
        this.mode = packetBuffer.readByte();
        this.clickedItem = packetBuffer.readItemStackFromBuffer();
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public C0EPacketClickWindow() {
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public short getActionNumber() {
        return this.actionNumber;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeByte(this.usedButton);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeByte(this.mode);
        packetBuffer.writeItemStackToBuffer(this.clickedItem);
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClickWindow(this);
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public int getUsedButton() {
        return this.usedButton;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C0EPacketClickWindow(final int windowId, final int slotId, final int usedButton, final int mode, final ItemStack itemStack, final short actionNumber) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.usedButton = usedButton;
        ItemStack copy;
        if (itemStack != null) {
            copy = itemStack.copy();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            copy = null;
        }
        this.clickedItem = copy;
        this.actionNumber = actionNumber;
        this.mode = mode;
    }
}
