package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C13PacketPlayerAbilities implements Packet
{
  private boolean invulnerable;
  private boolean flying;
  private boolean allowFlying;
  private boolean creativeMode;
  private float flySpeed;
  private float walkSpeed;
  private static final String __OBFID = "CL_00001364";
  
  public C13PacketPlayerAbilities() {}
  
  public C13PacketPlayerAbilities(PlayerCapabilities capabilities)
  {
    setInvulnerable(disableDamage);
    setFlying(isFlying);
    setAllowFlying(allowFlying);
    setCreativeMode(isCreativeMode);
    setFlySpeed(capabilities.getFlySpeed());
    setWalkSpeed(capabilities.getWalkSpeed());
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    byte var2 = data.readByte();
    setInvulnerable((var2 & 0x1) > 0);
    setFlying((var2 & 0x2) > 0);
    setAllowFlying((var2 & 0x4) > 0);
    setCreativeMode((var2 & 0x8) > 0);
    setFlySpeed(data.readFloat());
    setWalkSpeed(data.readFloat());
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    byte var2 = 0;
    
    if (isInvulnerable())
    {
      var2 = (byte)(var2 | 0x1);
    }
    
    if (isFlying())
    {
      var2 = (byte)(var2 | 0x2);
    }
    
    if (isAllowFlying())
    {
      var2 = (byte)(var2 | 0x4);
    }
    
    if (isCreativeMode())
    {
      var2 = (byte)(var2 | 0x8);
    }
    
    data.writeByte(var2);
    data.writeFloat(flySpeed);
    data.writeFloat(walkSpeed);
  }
  
  public void func_180761_a(INetHandlerPlayServer p_180761_1_)
  {
    p_180761_1_.processPlayerAbilities(this);
  }
  
  public boolean isInvulnerable()
  {
    return invulnerable;
  }
  
  public void setInvulnerable(boolean isInvulnerable)
  {
    invulnerable = isInvulnerable;
  }
  
  public boolean isFlying()
  {
    return flying;
  }
  
  public void setFlying(boolean isFlying)
  {
    flying = isFlying;
  }
  
  public boolean isAllowFlying()
  {
    return allowFlying;
  }
  
  public void setAllowFlying(boolean isAllowFlying)
  {
    allowFlying = isAllowFlying;
  }
  
  public boolean isCreativeMode()
  {
    return creativeMode;
  }
  
  public void setCreativeMode(boolean isCreativeMode)
  {
    creativeMode = isCreativeMode;
  }
  
  public void setFlySpeed(float flySpeedIn)
  {
    flySpeed = flySpeedIn;
  }
  
  public void setWalkSpeed(float walkSpeedIn)
  {
    walkSpeed = walkSpeedIn;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180761_a((INetHandlerPlayServer)handler);
  }
}
