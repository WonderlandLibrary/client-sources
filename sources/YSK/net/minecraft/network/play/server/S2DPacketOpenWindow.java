package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S2DPacketOpenWindow implements Packet<INetHandlerPlayClient>
{
    private int slotCount;
    private int entityId;
    private int windowId;
    private IChatComponent windowTitle;
    private String inventoryType;
    private static final String[] I;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeString(this.inventoryType);
        packetBuffer.writeChatComponent(this.windowTitle);
        packetBuffer.writeByte(this.slotCount);
        if (this.inventoryType.equals(S2DPacketOpenWindow.I[" ".length()])) {
            packetBuffer.writeInt(this.entityId);
        }
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public IChatComponent getWindowTitle() {
        return this.windowTitle;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.inventoryType = packetBuffer.readStringFromBuffer(0x10 ^ 0x30);
        this.windowTitle = packetBuffer.readChatComponent();
        this.slotCount = packetBuffer.readUnsignedByte();
        if (this.inventoryType.equals(S2DPacketOpenWindow.I["".length()])) {
            this.entityId = packetBuffer.readInt();
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S2DPacketOpenWindow(final int n, final String s, final IChatComponent chatComponent, final int n2, final int entityId) {
        this(n, s, chatComponent, n2);
        this.entityId = entityId;
    }
    
    public boolean hasSlots() {
        if (this.slotCount > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getGuiId() {
        return this.inventoryType;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("$\u001e\u0017\u0001<\u00188\f\u001a;\u0004", "apchH");
        S2DPacketOpenWindow.I[" ".length()] = I("\u0010\u0018;\u0005:,> \u001e=0", "UvOlN");
    }
    
    static {
        I();
    }
    
    public S2DPacketOpenWindow(final int n, final String s, final IChatComponent chatComponent) {
        this(n, s, chatComponent, "".length());
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleOpenWindow(this);
    }
    
    public int getWindowId() {
        return this.windowId;
    }
    
    public int getSlotCount() {
        return this.slotCount;
    }
    
    public S2DPacketOpenWindow() {
    }
    
    public S2DPacketOpenWindow(final int windowId, final String inventoryType, final IChatComponent windowTitle, final int slotCount) {
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.windowTitle = windowTitle;
        this.slotCount = slotCount;
    }
}
