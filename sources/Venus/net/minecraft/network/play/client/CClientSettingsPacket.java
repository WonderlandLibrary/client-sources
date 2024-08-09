/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.HandSide;

public class CClientSettingsPacket
implements IPacket<IServerPlayNetHandler> {
    private String lang;
    private int view;
    private ChatVisibility chatVisibility;
    private boolean enableColors;
    private int modelPartFlags;
    private HandSide mainHand;

    public CClientSettingsPacket() {
    }

    public CClientSettingsPacket(String string, int n, ChatVisibility chatVisibility, boolean bl, int n2, HandSide handSide) {
        this.lang = string;
        this.view = n;
        this.chatVisibility = chatVisibility;
        this.enableColors = bl;
        this.modelPartFlags = n2;
        this.mainHand = handSide;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.lang = packetBuffer.readString(16);
        this.view = packetBuffer.readByte();
        this.chatVisibility = packetBuffer.readEnumValue(ChatVisibility.class);
        this.enableColors = packetBuffer.readBoolean();
        this.modelPartFlags = packetBuffer.readUnsignedByte();
        this.mainHand = packetBuffer.readEnumValue(HandSide.class);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.lang);
        packetBuffer.writeByte(this.view);
        packetBuffer.writeEnumValue(this.chatVisibility);
        packetBuffer.writeBoolean(this.enableColors);
        packetBuffer.writeByte(this.modelPartFlags);
        packetBuffer.writeEnumValue(this.mainHand);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processClientSettings(this);
    }

    public ChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }

    public boolean isColorsEnabled() {
        return this.enableColors;
    }

    public int getModelPartFlags() {
        return this.modelPartFlags;
    }

    public HandSide getMainHand() {
        return this.mainHand;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

