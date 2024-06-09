/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;

public class S00PacketServerInfo
implements Packet {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter((Type)((Object)ServerStatusResponse.MinecraftProtocolVersionIdentifier.class), new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter((Type)((Object)ServerStatusResponse.PlayerCountData.class), new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter((Type)((Object)ServerStatusResponse.class), new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
    private ServerStatusResponse response;
    private static final String __OBFID = "CL_00001384";

    public S00PacketServerInfo() {
    }

    public S00PacketServerInfo(ServerStatusResponse responseIn) {
        this.response = responseIn;
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.response = GSON.fromJson(data.readStringFromBuffer(32767), ServerStatusResponse.class);
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeString(GSON.toJson(this.response));
    }

    public void processPacket(INetHandlerStatusClient handler) {
        handler.handleServerInfo(this);
    }

    public ServerStatusResponse func_149294_c() {
        return this.response;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.processPacket((INetHandlerStatusClient)handler);
    }
}

