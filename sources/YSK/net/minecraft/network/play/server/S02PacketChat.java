package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S02PacketChat implements Packet<INetHandlerPlayClient>
{
    private IChatComponent chatComponent;
    private byte type;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.chatComponent);
        packetBuffer.writeByte(this.type);
    }
    
    public byte getType() {
        return this.type;
    }
    
    public boolean isChat() {
        if (this.type != " ".length() && this.type != "  ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public S02PacketChat(final IChatComponent chatComponent) {
        this(chatComponent, (byte)" ".length());
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.chatComponent = packetBuffer.readChatComponent();
        this.type = packetBuffer.readByte();
    }
    
    public IChatComponent getChatComponent() {
        return this.chatComponent;
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleChat(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S02PacketChat() {
    }
    
    public S02PacketChat(final IChatComponent chatComponent, final byte type) {
        this.chatComponent = chatComponent;
        this.type = type;
    }
}
