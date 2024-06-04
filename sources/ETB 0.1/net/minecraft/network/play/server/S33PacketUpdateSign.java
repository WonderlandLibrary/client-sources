package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class S33PacketUpdateSign implements Packet
{
  private World field_179706_a;
  private BlockPos field_179705_b;
  private IChatComponent[] field_149349_d;
  private static final String __OBFID = "CL_00001338";
  
  public S33PacketUpdateSign() {}
  
  public S33PacketUpdateSign(World worldIn, BlockPos p_i45951_2_, IChatComponent[] p_i45951_3_)
  {
    field_179706_a = worldIn;
    field_179705_b = p_i45951_2_;
    field_149349_d = new IChatComponent[] { p_i45951_3_[0], p_i45951_3_[1], p_i45951_3_[2], p_i45951_3_[3] };
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_179705_b = data.readBlockPos();
    field_149349_d = new IChatComponent[4];
    
    for (int var2 = 0; var2 < 4; var2++)
    {
      field_149349_d[var2] = data.readChatComponent();
    }
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeBlockPos(field_179705_b);
    
    for (int var2 = 0; var2 < 4; var2++)
    {
      data.writeChatComponent(field_149349_d[var2]);
    }
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleUpdateSign(this);
  }
  
  public BlockPos func_179704_a()
  {
    return field_179705_b;
  }
  
  public IChatComponent[] func_180753_b()
  {
    return field_149349_d;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
