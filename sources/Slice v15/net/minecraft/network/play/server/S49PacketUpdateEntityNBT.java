package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S49PacketUpdateEntityNBT implements Packet
{
  private int field_179766_a;
  private NBTTagCompound field_179765_b;
  private static final String __OBFID = "CL_00002301";
  
  public S49PacketUpdateEntityNBT() {}
  
  public S49PacketUpdateEntityNBT(int p_i45979_1_, NBTTagCompound p_i45979_2_)
  {
    field_179766_a = p_i45979_1_;
    field_179765_b = p_i45979_2_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_179766_a = data.readVarIntFromBuffer();
    field_179765_b = data.readNBTTagCompoundFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_179766_a);
    data.writeNBTTagCompoundToBuffer(field_179765_b);
  }
  
  public void func_179762_a(INetHandlerPlayClient p_179762_1_)
  {
    p_179762_1_.func_175097_a(this);
  }
  
  public NBTTagCompound func_179763_a()
  {
    return field_179765_b;
  }
  
  public Entity func_179764_a(World worldIn)
  {
    return worldIn.getEntityByID(field_179766_a);
  }
  



  public void processPacket(INetHandler handler)
  {
    func_179762_a((INetHandlerPlayClient)handler);
  }
}
