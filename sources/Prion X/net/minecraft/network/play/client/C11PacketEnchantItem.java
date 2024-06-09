package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C11PacketEnchantItem implements Packet
{
  private int id;
  private int button;
  private static final String __OBFID = "CL_00001352";
  
  public C11PacketEnchantItem() {}
  
  public C11PacketEnchantItem(int p_i45245_1_, int p_i45245_2_)
  {
    id = p_i45245_1_;
    button = p_i45245_2_;
  }
  



  public void processPacket(INetHandlerPlayServer handler)
  {
    handler.processEnchantItem(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    id = data.readByte();
    button = data.readByte();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(id);
    data.writeByte(button);
  }
  
  public int getId()
  {
    return id;
  }
  
  public int getButton()
  {
    return button;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayServer)handler);
  }
}
