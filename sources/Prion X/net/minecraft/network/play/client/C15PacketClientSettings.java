package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C15PacketClientSettings implements Packet
{
  private String lang;
  private int view;
  private EntityPlayer.EnumChatVisibility chatVisibility;
  private boolean enableColors;
  private int field_179711_e;
  private static final String __OBFID = "CL_00001350";
  
  public C15PacketClientSettings() {}
  
  public C15PacketClientSettings(String p_i45946_1_, int p_i45946_2_, EntityPlayer.EnumChatVisibility p_i45946_3_, boolean p_i45946_4_, int p_i45946_5_)
  {
    lang = p_i45946_1_;
    view = p_i45946_2_;
    chatVisibility = p_i45946_3_;
    enableColors = p_i45946_4_;
    field_179711_e = p_i45946_5_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    lang = data.readStringFromBuffer(7);
    view = data.readByte();
    chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(data.readByte());
    enableColors = data.readBoolean();
    field_179711_e = data.readUnsignedByte();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeString(lang);
    data.writeByte(view);
    data.writeByte(chatVisibility.getChatVisibility());
    data.writeBoolean(enableColors);
    data.writeByte(field_179711_e);
  }
  



  public void processPacket(INetHandlerPlayServer handler)
  {
    handler.processClientSettings(this);
  }
  
  public String getLang()
  {
    return lang;
  }
  
  public EntityPlayer.EnumChatVisibility getChatVisibility()
  {
    return chatVisibility;
  }
  
  public boolean isColorsEnabled()
  {
    return enableColors;
  }
  
  public int getView()
  {
    return field_179711_e;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayServer)handler);
  }
}
