package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer implements Packet
{
  public double x;
  public double y;
  public double z;
  public float yaw;
  public float pitch;
  public boolean field_149474_g;
  public boolean field_149480_h;
  public boolean rotating;
  private static final String __OBFID = "CL_00001360";
  
  public C03PacketPlayer() {}
  
  public C03PacketPlayer(boolean p_i45256_1_)
  {
    field_149474_g = p_i45256_1_;
  }
  



  public void processPacket(INetHandlerPlayServer handler)
  {
    handler.processPlayer(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149474_g = (data.readUnsignedByte() != 0);
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeByte(field_149474_g ? 1 : 0);
  }
  
  public double getPositionX()
  {
    return x;
  }
  
  public double getPositionY()
  {
    return y;
  }
  
  public double getPositionZ()
  {
    return z;
  }
  
  public float getYaw()
  {
    return yaw;
  }
  
  public float getPitch()
  {
    return pitch;
  }
  
  public boolean func_149465_i()
  {
    return field_149474_g;
  }
  
  public boolean func_149466_j()
  {
    return field_149480_h;
  }
  
  public boolean getRotating()
  {
    return rotating;
  }
  
  public void func_149469_a(boolean p_149469_1_)
  {
    field_149480_h = p_149469_1_;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayServer)handler);
  }
  
  public static class C04PacketPlayerPosition extends C03PacketPlayer
  {
    private static final String __OBFID = "CL_00001361";
    
    public C04PacketPlayerPosition()
    {
      field_149480_h = true;
    }
    
    public C04PacketPlayerPosition(double p_i45942_1_, double p_i45942_3_, double p_i45942_5_, boolean p_i45942_7_)
    {
      x = p_i45942_1_;
      y = p_i45942_3_;
      z = p_i45942_5_;
      field_149474_g = p_i45942_7_;
      field_149480_h = true;
    }
    
    public void readPacketData(PacketBuffer data) throws IOException
    {
      x = data.readDouble();
      y = data.readDouble();
      z = data.readDouble();
      super.readPacketData(data);
    }
    
    public void writePacketData(PacketBuffer data) throws IOException
    {
      data.writeDouble(x);
      data.writeDouble(y);
      data.writeDouble(z);
      super.writePacketData(data);
    }
    
    public void processPacket(INetHandler handler)
    {
      super.processPacket((INetHandlerPlayServer)handler);
    }
  }
  
  public static class C05PacketPlayerLook extends C03PacketPlayer
  {
    private static final String __OBFID = "CL_00001363";
    
    public C05PacketPlayerLook()
    {
      rotating = true;
    }
    
    public C05PacketPlayerLook(float p_i45255_1_, float p_i45255_2_, boolean p_i45255_3_)
    {
      yaw = p_i45255_1_;
      pitch = p_i45255_2_;
      field_149474_g = p_i45255_3_;
      rotating = true;
    }
    
    public void readPacketData(PacketBuffer data) throws IOException
    {
      yaw = data.readFloat();
      pitch = data.readFloat();
      super.readPacketData(data);
    }
    
    public void writePacketData(PacketBuffer data) throws IOException
    {
      data.writeFloat(yaw);
      data.writeFloat(pitch);
      super.writePacketData(data);
    }
    
    public void processPacket(INetHandler handler)
    {
      super.processPacket((INetHandlerPlayServer)handler);
    }
  }
  
  public static class C06PacketPlayerPosLook extends C03PacketPlayer
  {
    private static final String __OBFID = "CL_00001362";
    
    public C06PacketPlayerPosLook()
    {
      field_149480_h = true;
      rotating = true;
    }
    
    public C06PacketPlayerPosLook(double p_i45941_1_, double p_i45941_3_, double p_i45941_5_, float p_i45941_7_, float p_i45941_8_, boolean p_i45941_9_)
    {
      x = p_i45941_1_;
      y = p_i45941_3_;
      z = p_i45941_5_;
      yaw = p_i45941_7_;
      pitch = p_i45941_8_;
      field_149474_g = p_i45941_9_;
      rotating = true;
      field_149480_h = true;
    }
    
    public void readPacketData(PacketBuffer data) throws IOException
    {
      x = data.readDouble();
      y = data.readDouble();
      z = data.readDouble();
      yaw = data.readFloat();
      pitch = data.readFloat();
      super.readPacketData(data);
    }
    
    public void writePacketData(PacketBuffer data) throws IOException
    {
      data.writeDouble(x);
      data.writeDouble(y);
      data.writeDouble(z);
      data.writeFloat(yaw);
      data.writeFloat(pitch);
      super.writePacketData(data);
    }
    
    public void processPacket(INetHandler handler)
    {
      super.processPacket((INetHandlerPlayServer)handler);
    }
  }
}
