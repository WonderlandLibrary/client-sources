/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.reflect.Type;
import net.minecraft.client.network.status.IClientStatusNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class SServerInfoPacket
implements IPacket<IClientStatusNetHandler> {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter((Type)((Object)ServerStatusResponse.Version.class), new ServerStatusResponse.Version.Serializer()).registerTypeAdapter((Type)((Object)ServerStatusResponse.Players.class), new ServerStatusResponse.Players.Serializer()).registerTypeAdapter((Type)((Object)ServerStatusResponse.class), new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
    private ServerStatusResponse response;

    public SServerInfoPacket() {
    }

    public SServerInfoPacket(ServerStatusResponse serverStatusResponse) {
        this.response = serverStatusResponse;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.response = JSONUtils.fromJson(GSON, packetBuffer.readString(Short.MAX_VALUE), ServerStatusResponse.class);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(GSON.toJson(this.response));
    }

    @Override
    public void processPacket(IClientStatusNetHandler iClientStatusNetHandler) {
        iClientStatusNetHandler.handleServerInfo(this);
    }

    public ServerStatusResponse getResponse() {
        return this.response;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientStatusNetHandler)iNetHandler);
    }
}

