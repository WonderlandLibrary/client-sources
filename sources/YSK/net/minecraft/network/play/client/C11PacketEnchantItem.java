package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C11PacketEnchantItem implements Packet<INetHandlerPlayServer>
{
    private int button;
    private int windowId;
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processEnchantItem(this);
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeByte(this.button);
    }
    
    public int getButton() {
        return this.button;
    }
    
    public C11PacketEnchantItem() {
    }
    
    public C11PacketEnchantItem(final int windowId, final int button) {
        this.windowId = windowId;
        this.button = button;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.button = packetBuffer.readByte();
    }
    
    public int getWindowId() {
        return this.windowId;
    }
}
