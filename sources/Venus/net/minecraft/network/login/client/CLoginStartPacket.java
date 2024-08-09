/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.client;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.IServerLoginNetHandler;

public class CLoginStartPacket
implements IPacket<IServerLoginNetHandler> {
    private GameProfile profile;

    public CLoginStartPacket() {
    }

    public CLoginStartPacket(GameProfile gameProfile) {
        this.profile = gameProfile;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.profile = new GameProfile(null, packetBuffer.readString(16));
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.profile.getName());
    }

    @Override
    public void processPacket(IServerLoginNetHandler iServerLoginNetHandler) {
        iServerLoginNetHandler.processLoginStart(this);
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerLoginNetHandler)iNetHandler);
    }
}

