package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class S01PacketEncryptionRequest implements Packet
{
  private String hashedServerId;
  private PublicKey publicKey;
  private byte[] field_149611_c;
  private static final String __OBFID = "CL_00001376";
  
  public S01PacketEncryptionRequest() {}
  
  public S01PacketEncryptionRequest(String serverId, PublicKey key, byte[] p_i45268_3_)
  {
    hashedServerId = serverId;
    publicKey = key;
    field_149611_c = p_i45268_3_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    hashedServerId = data.readStringFromBuffer(20);
    publicKey = CryptManager.decodePublicKey(data.readByteArray());
    field_149611_c = data.readByteArray();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeString(hashedServerId);
    data.writeByteArray(publicKey.getEncoded());
    data.writeByteArray(field_149611_c);
  }
  



  public void processPacket(INetHandlerLoginClient handler)
  {
    handler.handleEncryptionRequest(this);
  }
  
  public String func_149609_c()
  {
    return hashedServerId;
  }
  
  public PublicKey func_149608_d()
  {
    return publicKey;
  }
  
  public byte[] func_149607_e()
  {
    return field_149611_c;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerLoginClient)handler);
  }
}
