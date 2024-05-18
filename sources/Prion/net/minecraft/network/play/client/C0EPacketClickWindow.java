package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;











public class C0EPacketClickWindow
  implements Packet
{
  private int windowId;
  private int slotId;
  private int usedButton;
  private short actionNumber;
  private ItemStack clickedItem;
  private int mode;
  private static final String __OBFID = "CL_00001353";
  
  public C0EPacketClickWindow() {}
  
  public C0EPacketClickWindow(int p_i45246_1_, int p_i45246_2_, int p_i45246_3_, int p_i45246_4_, ItemStack p_i45246_5_, short p_i45246_6_)
  {
    windowId = p_i45246_1_;
    slotId = p_i45246_2_;
    usedButton = p_i45246_3_;
    clickedItem = (p_i45246_5_ != null ? p_i45246_5_.copy() : null);
    actionNumber = p_i45246_6_;
    mode = p_i45246_4_;
  }
  



  public void processPacket(INetHandlerPlayServer handler)
  {
    handler.processClickWindow(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    windowId = data.readByte();
    slotId = data.readShort();
    usedButton = data.readByte();
    actionNumber = data.readShort();
    mode = data.readByte();
    clickedItem = data.readItemStackFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(windowId);
    data.writeShort(slotId);
    data.writeByte(usedButton);
    data.writeShort(actionNumber);
    data.writeByte(mode);
    data.writeItemStackToBuffer(clickedItem);
  }
  
  public int getWindowId()
  {
    return windowId;
  }
  
  public int getSlotId()
  {
    return slotId;
  }
  
  public int getUsedButton()
  {
    return usedButton;
  }
  
  public short getActionNumber()
  {
    return actionNumber;
  }
  
  public ItemStack getClickedItem()
  {
    return clickedItem;
  }
  
  public int getMode()
  {
    return mode;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayServer)handler);
  }
}
