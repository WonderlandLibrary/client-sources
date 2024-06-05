package net.minecraft.network.play.client;

import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C15PacketClientSettings implements Packet
{
    private String lang;
    private int view;
    private EntityPlayer.EnumChatVisibility chatVisibility;
    private boolean enableColors;
    private int field_179711_e;
    
    public C15PacketClientSettings() {
    }
    
    public C15PacketClientSettings(final String p_i45946_1_, final int p_i45946_2_, final EntityPlayer.EnumChatVisibility p_i45946_3_, final boolean p_i45946_4_, final int p_i45946_5_) {
        this.lang = p_i45946_1_;
        this.view = p_i45946_2_;
        this.chatVisibility = p_i45946_3_;
        this.enableColors = p_i45946_4_;
        this.field_179711_e = p_i45946_5_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.lang = data.readStringFromBuffer(7);
        this.view = data.readByte();
        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(data.readByte());
        this.enableColors = data.readBoolean();
        this.field_179711_e = data.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeString(this.lang);
        data.writeByte(this.view);
        data.writeByte(this.chatVisibility.getChatVisibility());
        data.writeBoolean(this.enableColors);
        data.writeByte(this.field_179711_e);
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processClientSettings(this);
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public EntityPlayer.EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    public boolean isColorsEnabled() {
        return this.enableColors;
    }
    
    public int getView() {
        return this.field_179711_e;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayServer)handler);
    }
}
