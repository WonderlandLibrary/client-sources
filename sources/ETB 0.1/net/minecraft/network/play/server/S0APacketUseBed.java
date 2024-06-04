package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S0APacketUseBed implements Packet
{
  private int playerID;
  private BlockPos field_179799_b;
  private static final String __OBFID = "CL_00001319";
  
  public S0APacketUseBed() {}
  
  public S0APacketUseBed(EntityPlayer p_i45964_1_, BlockPos p_i45964_2_)
  {
    playerID = p_i45964_1_.getEntityId();
    field_179799_b = p_i45964_2_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    playerID = data.readVarIntFromBuffer();
    field_179799_b = data.readBlockPos();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(playerID);
    data.writeBlockPos(field_179799_b);
  }
  
  public void func_180744_a(INetHandlerPlayClient p_180744_1_)
  {
    p_180744_1_.handleUseBed(this);
  }
  
  public EntityPlayer getPlayer(World worldIn)
  {
    return (EntityPlayer)worldIn.getEntityByID(playerID);
  }
  
  public BlockPos func_179798_a()
  {
    return field_179799_b;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180744_a((INetHandlerPlayClient)handler);
  }
}
