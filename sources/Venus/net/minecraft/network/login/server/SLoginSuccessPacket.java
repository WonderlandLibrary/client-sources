/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.UUIDCodec;

public class SLoginSuccessPacket
implements IPacket<IClientLoginNetHandler> {
    private GameProfile profile;

    public SLoginSuccessPacket() {
    }

    public SLoginSuccessPacket(GameProfile gameProfile) {
        this.profile = gameProfile;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] nArray = new int[4];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = packetBuffer.readInt();
        }
        UUID uUID = UUIDCodec.decodeUUID(nArray);
        String string = packetBuffer.readString(16);
        this.profile = new GameProfile(uUID, string);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        for (int n : UUIDCodec.encodeUUID(this.profile.getId())) {
            packetBuffer.writeInt(n);
        }
        packetBuffer.writeString(this.profile.getName());
    }

    @Override
    public void processPacket(IClientLoginNetHandler iClientLoginNetHandler) {
        iClientLoginNetHandler.handleLoginSuccess(this);
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientLoginNetHandler)iNetHandler);
    }
}

