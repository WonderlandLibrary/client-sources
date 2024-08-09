/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class SChatPacket
implements IPacket<IClientPlayNetHandler> {
    private ITextComponent chatComponent;
    private ChatType type;
    private UUID field_240809_c_;

    public SChatPacket() {
    }

    public SChatPacket(ITextComponent iTextComponent, ChatType chatType, UUID uUID) {
        this.chatComponent = iTextComponent;
        this.type = chatType;
        this.field_240809_c_ = uUID;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.chatComponent = packetBuffer.readTextComponent();
        this.type = ChatType.byId(packetBuffer.readByte());
        this.field_240809_c_ = packetBuffer.readUniqueId();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeTextComponent(this.chatComponent);
        packetBuffer.writeByte(this.type.getId());
        packetBuffer.writeUniqueId(this.field_240809_c_);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleChat(this);
    }

    public ITextComponent getChatComponent() {
        return this.chatComponent;
    }

    public boolean isSystem() {
        return this.type == ChatType.SYSTEM || this.type == ChatType.GAME_INFO;
    }

    public ChatType getType() {
        return this.type;
    }

    public UUID func_240810_e_() {
        return this.field_240809_c_;
    }

    @Override
    public boolean shouldSkipErrors() {
        return false;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

