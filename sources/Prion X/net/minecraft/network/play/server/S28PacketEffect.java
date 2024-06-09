package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;




public class S28PacketEffect
  implements Packet
{
  private int soundType;
  private BlockPos field_179747_b;
  private int soundData;
  private boolean serverWide;
  private static final String __OBFID = "CL_00001307";
  
  public S28PacketEffect() {}
  
  public S28PacketEffect(int p_i45978_1_, BlockPos p_i45978_2_, int p_i45978_3_, boolean p_i45978_4_)
  {
    soundType = p_i45978_1_;
    field_179747_b = p_i45978_2_;
    soundData = p_i45978_3_;
    serverWide = p_i45978_4_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    soundType = data.readInt();
    field_179747_b = data.readBlockPos();
    soundData = data.readInt();
    serverWide = data.readBoolean();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeInt(soundType);
    data.writeBlockPos(field_179747_b);
    data.writeInt(soundData);
    data.writeBoolean(serverWide);
  }
  
  public void func_180739_a(INetHandlerPlayClient p_180739_1_)
  {
    p_180739_1_.handleEffect(this);
  }
  
  public boolean isSoundServerwide()
  {
    return serverWide;
  }
  
  public int getSoundType()
  {
    return soundType;
  }
  
  public int getSoundData()
  {
    return soundData;
  }
  
  public BlockPos func_179746_d()
  {
    return field_179747_b;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180739_a((INetHandlerPlayClient)handler);
  }
}
