package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class C00PacketLoginStart
  implements Packet
{
  private GameProfile profile;
  private static final String __OBFID = "CL_00001379";
  
  public C00PacketLoginStart() {}
  
  public C00PacketLoginStart(GameProfile profileIn)
  {
    profile = profileIn;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    profile = new GameProfile(null, data.readStringFromBuffer(16));
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeString(profile.getName());
  }
  
  public void func_180773_a(INetHandlerLoginServer p_180773_1_)
  {
    p_180773_1_.processLoginStart(this);
  }
  
  public GameProfile getProfile()
  {
    return profile;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180773_a((INetHandlerLoginServer)handler);
  }
}
