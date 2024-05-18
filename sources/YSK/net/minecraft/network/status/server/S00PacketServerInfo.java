package net.minecraft.network.status.server;

import net.minecraft.network.status.*;
import net.minecraft.network.*;
import java.io.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class S00PacketServerInfo implements Packet<INetHandlerStatusClient>
{
    private ServerStatusResponse response;
    private static final Gson GSON;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerStatusClient)netHandler);
    }
    
    public S00PacketServerInfo() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(S00PacketServerInfo.GSON.toJson((Object)this.response));
    }
    
    public ServerStatusResponse getResponse() {
        return this.response;
    }
    
    public S00PacketServerInfo(final ServerStatusResponse response) {
        this.response = response;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.response = (ServerStatusResponse)S00PacketServerInfo.GSON.fromJson(packetBuffer.readStringFromBuffer(9007 + 10175 + 12807 + 778), (Class)ServerStatusResponse.class);
    }
    
    @Override
    public void processPacket(final INetHandlerStatusClient netHandlerStatusClient) {
        netHandlerStatusClient.handleServerInfo(this);
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, (Object)new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter((Type)ServerStatusResponse.PlayerCountData.class, (Object)new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter((Type)ServerStatusResponse.class, (Object)new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.Serializer()).registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
