package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.*;

public class C15PacketClientSettings implements Packet<INetHandlerPlayServer>
{
    private String lang;
    private EntityPlayer.EnumChatVisibility chatVisibility;
    private boolean enableColors;
    private int modelPartFlags;
    private int view;
    
    public C15PacketClientSettings(final String lang, final int view, final EntityPlayer.EnumChatVisibility chatVisibility, final boolean enableColors, final int modelPartFlags) {
        this.lang = lang;
        this.view = view;
        this.chatVisibility = chatVisibility;
        this.enableColors = enableColors;
        this.modelPartFlags = modelPartFlags;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.lang);
        packetBuffer.writeByte(this.view);
        packetBuffer.writeByte(this.chatVisibility.getChatVisibility());
        packetBuffer.writeBoolean(this.enableColors);
        packetBuffer.writeByte(this.modelPartFlags);
    }
    
    public boolean isColorsEnabled() {
        return this.enableColors;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getModelPartFlags() {
        return this.modelPartFlags;
    }
    
    public C15PacketClientSettings() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.lang = packetBuffer.readStringFromBuffer(0x9D ^ 0x9A);
        this.view = packetBuffer.readByte();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(packetBuffer.readByte());
        this.enableColors = packetBuffer.readBoolean();
        this.modelPartFlags = packetBuffer.readUnsignedByte();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processClientSettings(this);
    }
    
    public EntityPlayer.EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    public String getLang() {
        return this.lang;
    }
}
