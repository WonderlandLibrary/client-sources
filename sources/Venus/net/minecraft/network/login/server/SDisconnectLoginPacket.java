/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

public class SDisconnectLoginPacket
implements IPacket<IClientLoginNetHandler> {
    private ITextComponent reason;

    public SDisconnectLoginPacket() {
    }

    public SDisconnectLoginPacket(ITextComponent iTextComponent) {
        this.reason = iTextComponent;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.reason = ITextComponent.Serializer.getComponentFromJsonLenient(packetBuffer.readString(262144));
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeTextComponent(this.reason);
    }

    @Override
    public void processPacket(IClientLoginNetHandler iClientLoginNetHandler) {
        iClientLoginNetHandler.handleDisconnect(this);
    }

    public ITextComponent getReason() {
        return this.reason;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientLoginNetHandler)iNetHandler);
    }
}

