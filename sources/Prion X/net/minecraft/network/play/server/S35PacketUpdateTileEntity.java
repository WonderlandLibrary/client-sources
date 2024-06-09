package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;


public class S35PacketUpdateTileEntity
  implements Packet
{
  private BlockPos field_179824_a;
  private int metadata;
  private NBTTagCompound nbt;
  private static final String __OBFID = "CL_00001285";
  
  public S35PacketUpdateTileEntity() {}
  
  public S35PacketUpdateTileEntity(BlockPos p_i45990_1_, int p_i45990_2_, NBTTagCompound p_i45990_3_)
  {
    field_179824_a = p_i45990_1_;
    metadata = p_i45990_2_;
    nbt = p_i45990_3_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_179824_a = data.readBlockPos();
    metadata = data.readUnsignedByte();
    nbt = data.readNBTTagCompoundFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeBlockPos(field_179824_a);
    data.writeByte((byte)metadata);
    data.writeNBTTagCompoundToBuffer(nbt);
  }
  
  public void func_180725_a(INetHandlerPlayClient p_180725_1_)
  {
    p_180725_1_.handleUpdateTileEntity(this);
  }
  
  public BlockPos func_179823_a()
  {
    return field_179824_a;
  }
  
  public int getTileEntityType()
  {
    return metadata;
  }
  
  public NBTTagCompound getNbtCompound()
  {
    return nbt;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180725_a((INetHandlerPlayClient)handler);
  }
}
