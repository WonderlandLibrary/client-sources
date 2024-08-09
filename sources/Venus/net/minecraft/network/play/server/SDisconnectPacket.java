/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

public class SDisconnectPacket
implements IPacket<IClientPlayNetHandler> {
    private ITextComponent reason;

    public SDisconnectPacket() {
    }

    public SDisconnectPacket(ITextComponent iTextComponent) {
        this.reason = iTextComponent;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.reason = packetBuffer.readTextComponent();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeTextComponent(this.reason);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleDisconnect(this);
    }

    public ITextComponent getReason() {
        return this.reason;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

