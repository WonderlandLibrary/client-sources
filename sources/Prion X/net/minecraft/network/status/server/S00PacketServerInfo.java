package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier;
import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent.Serializer;

public class S00PacketServerInfo implements net.minecraft.network.Packet
{
  private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(net.minecraft.network.ServerStatusResponse.PlayerCountData.class, new net.minecraft.network.ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new net.minecraft.network.ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(net.minecraft.util.ChatStyle.class, new net.minecraft.util.ChatStyle.Serializer()).registerTypeAdapterFactory(new net.minecraft.util.EnumTypeAdapterFactory()).create();
  private ServerStatusResponse response;
  private static final String __OBFID = "CL_00001384";
  
  public S00PacketServerInfo() {}
  
  public S00PacketServerInfo(ServerStatusResponse responseIn)
  {
    response = responseIn;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    response = ((ServerStatusResponse)GSON.fromJson(data.readStringFromBuffer(32767), ServerStatusResponse.class));
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeString(GSON.toJson(response));
  }
  



  public void processPacket(INetHandlerStatusClient handler)
  {
    handler.handleServerInfo(this);
  }
  
  public ServerStatusResponse func_149294_c()
  {
    return response;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerStatusClient)handler);
  }
}
