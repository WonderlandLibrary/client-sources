package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0CPacketInput
  implements Packet<INetHandler>
{
  private float strafeSpeed;
  private float forwardSpeed;
  private boolean jumping;
  private boolean sneaking;
  private static final String __OBFID = "CL_00001367";
  
  public C0CPacketInput() {}
  
  public C0CPacketInput(float p_i45261_1_, float p_i45261_2_, boolean p_i45261_3_, boolean p_i45261_4_)
  {
    this.strafeSpeed = p_i45261_1_;
    this.forwardSpeed = p_i45261_2_;
    this.jumping = p_i45261_3_;
    this.sneaking = p_i45261_4_;
  }
  
  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    this.strafeSpeed = data.readFloat();
    this.forwardSpeed = data.readFloat();
    byte var2 = data.readByte();
    this.jumping = ((var2 & 0x1) > 0);
    this.sneaking = ((var2 & 0x2) > 0);
  }
  
  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeFloat(this.strafeSpeed);
    data.writeFloat(this.forwardSpeed);
    byte var2 = 0;
    if (this.jumping) {
      var2 = (byte)(var2 | 0x1);
    }
    if (this.sneaking) {
      var2 = (byte)(var2 | 0x2);
    }
    data.writeByte(var2);
  }
  
  public void func_180766_a(INetHandlerPlayServer p_180766_1_)
  {
    p_180766_1_.processInput(this);
  }
  
  public float getStrafeSpeed()
  {
    return this.strafeSpeed;
  }
  
  public float getForwardSpeed()
  {
    return this.forwardSpeed;
  }
  
  public boolean isJumping()
  {
    return this.jumping;
  }
  
  public boolean isSneaking()
  {
    return this.sneaking;
  }
  
  public void processPacket(INetHandler handler)
  {
    func_180766_a((INetHandlerPlayServer)handler);
  }
}
