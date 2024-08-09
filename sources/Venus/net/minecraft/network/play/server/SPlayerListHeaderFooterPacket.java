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

public class SPlayerListHeaderFooterPacket
implements IPacket<IClientPlayNetHandler> {
    private ITextComponent header;
    private ITextComponent footer;

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.header = packetBuffer.readTextComponent();
        this.footer = packetBuffer.readTextComponent();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeTextComponent(this.header);
        packetBuffer.writeTextComponent(this.footer);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handlePlayerListHeaderFooter(this);
    }

    public ITextComponent getHeader() {
        return this.header;
    }

    public ITextComponent getFooter() {
        return this.footer;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

