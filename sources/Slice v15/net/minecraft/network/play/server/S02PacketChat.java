package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S02PacketChat implements Packet
{
  private IChatComponent chatComponent;
  private byte field_179842_b;
  private static final String __OBFID = "CL_00001289";
  
  public S02PacketChat() {}
  
  public S02PacketChat(IChatComponent component)
  {
    this(component, (byte)1);
  }
  
  public S02PacketChat(IChatComponent p_i45986_1_, byte p_i45986_2_)
  {
    chatComponent = p_i45986_1_;
    field_179842_b = p_i45986_2_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    chatComponent = data.readChatComponent();
    field_179842_b = data.readByte();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeChatComponent(chatComponent);
    data.writeByte(field_179842_b);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleChat(this);
  }
  
  public IChatComponent func_148915_c()
  {
    return chatComponent;
  }
  
  public boolean isChat()
  {
    return (field_179842_b == 1) || (field_179842_b == 2);
  }
  
  public byte func_179841_c()
  {
    return field_179842_b;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
