package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier;
import net.minecraft.network.ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer;
import net.minecraft.network.ServerStatusResponse.PlayerCountData;
import net.minecraft.network.ServerStatusResponse.PlayerCountData.Serializer;
import net.minecraft.network.ServerStatusResponse.Serializer;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChatStyle.Serializer;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent.Serializer;

public class S00PacketServerInfo
  implements Packet<INetHandler>
{
  private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
  private ServerStatusResponse response;
  private static final String __OBFID = "CL_00001384";
  
  public S00PacketServerInfo() {}
  
  public S00PacketServerInfo(ServerStatusResponse responseIn)
  {
    this.response = responseIn;
  }
  
  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    this.response = ((ServerStatusResponse)GSON.fromJson(data.readStringFromBuffer(32767), ServerStatusResponse.class));
  }
  
  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeString(GSON.toJson(this.response));
  }
  
  public void processPacket(INetHandlerStatusClient handler)
  {
    handler.handleServerInfo(this);
  }
  
  public ServerStatusResponse func_149294_c()
  {
    return this.response;
  }
  
  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerStatusClient)handler);
  }
}
