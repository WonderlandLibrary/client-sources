package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S2DPacketOpenWindow implements Packet
{
  private int windowId;
  private String inventoryType;
  private IChatComponent windowTitle;
  private int slotCount;
  private int entityId;
  private static final String __OBFID = "CL_00001293";
  
  public S2DPacketOpenWindow() {}
  
  public S2DPacketOpenWindow(int p_i45981_1_, String p_i45981_2_, IChatComponent p_i45981_3_)
  {
    this(p_i45981_1_, p_i45981_2_, p_i45981_3_, 0);
  }
  
  public S2DPacketOpenWindow(int p_i45982_1_, String p_i45982_2_, IChatComponent p_i45982_3_, int p_i45982_4_)
  {
    windowId = p_i45982_1_;
    inventoryType = p_i45982_2_;
    windowTitle = p_i45982_3_;
    slotCount = p_i45982_4_;
  }
  
  public S2DPacketOpenWindow(int p_i45983_1_, String p_i45983_2_, IChatComponent p_i45983_3_, int p_i45983_4_, int p_i45983_5_)
  {
    this(p_i45983_1_, p_i45983_2_, p_i45983_3_, p_i45983_4_);
    entityId = p_i45983_5_;
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleOpenWindow(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    windowId = data.readUnsignedByte();
    inventoryType = data.readStringFromBuffer(32);
    windowTitle = data.readChatComponent();
    slotCount = data.readUnsignedByte();
    
    if (inventoryType.equals("EntityHorse"))
    {
      entityId = data.readInt();
    }
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(windowId);
    data.writeString(inventoryType);
    data.writeChatComponent(windowTitle);
    data.writeByte(slotCount);
    
    if (inventoryType.equals("EntityHorse"))
    {
      data.writeInt(entityId);
    }
  }
  
  public int func_148901_c()
  {
    return windowId;
  }
  
  public String func_148902_e()
  {
    return inventoryType;
  }
  
  public IChatComponent func_179840_c()
  {
    return windowTitle;
  }
  
  public int func_148898_f()
  {
    return slotCount;
  }
  
  public int func_148897_h()
  {
    return entityId;
  }
  
  public boolean func_148900_g()
  {
    return slotCount > 0;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
