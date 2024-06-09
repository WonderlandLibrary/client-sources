package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S25PacketBlockBreakAnim implements Packet
{
  private int breakerId;
  private BlockPos position;
  private int progress;
  private static final String __OBFID = "CL_00001284";
  
  public S25PacketBlockBreakAnim() {}
  
  public S25PacketBlockBreakAnim(int breakerId, BlockPos pos, int progress)
  {
    this.breakerId = breakerId;
    position = pos;
    this.progress = progress;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    breakerId = data.readVarIntFromBuffer();
    position = data.readBlockPos();
    progress = data.readUnsignedByte();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(breakerId);
    data.writeBlockPos(position);
    data.writeByte(progress);
  }
  
  public void handle(INetHandlerPlayClient handler)
  {
    handler.handleBlockBreakAnim(this);
  }
  
  public int func_148845_c()
  {
    return breakerId;
  }
  
  public BlockPos func_179821_b()
  {
    return position;
  }
  
  public int func_148846_g()
  {
    return progress;
  }
  



  public void processPacket(INetHandler handler)
  {
    handle((INetHandlerPlayClient)handler);
  }
}
