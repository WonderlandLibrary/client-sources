package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C10PacketCreativeInventoryAction implements Packet
{
  private int slotId;
  private ItemStack stack;
  private static final String __OBFID = "CL_00001369";
  
  public C10PacketCreativeInventoryAction() {}
  
  public C10PacketCreativeInventoryAction(int p_i45263_1_, ItemStack p_i45263_2_)
  {
    slotId = p_i45263_1_;
    stack = (p_i45263_2_ != null ? p_i45263_2_.copy() : null);
  }
  
  public void func_180767_a(INetHandlerPlayServer p_180767_1_)
  {
    p_180767_1_.processCreativeInventoryAction(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    slotId = data.readShort();
    stack = data.readItemStackFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeShort(slotId);
    data.writeItemStackToBuffer(stack);
  }
  
  public int getSlotId()
  {
    return slotId;
  }
  
  public ItemStack getStack()
  {
    return stack;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180767_a((INetHandlerPlayServer)handler);
  }
}
