package net.minecraft.network.status.server;

import net.minecraft.network.status.*;
import java.io.*;
import net.minecraft.network.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class S00PacketServerInfo implements Packet<INetHandlerStatusClient>
{
    private static final Gson GSON;
    private ServerStatusResponse response;
    
    public S00PacketServerInfo() {
    }
    
    public S00PacketServerInfo(final ServerStatusResponse responseIn) {
        this.response = responseIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.response = S00PacketServerInfo.GSON.fromJson(buf.readStringFromBuffer(32767), ServerStatusResponse.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeString(S00PacketServerInfo.GSON.toJson(this.response));
    }
    
    @Override
    public void processPacket(final INetHandlerStatusClient handler) {
        handler.handleServerInfo(this);
    }
    
    public ServerStatusResponse getResponse() {
        return this.response;
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
    }
}
