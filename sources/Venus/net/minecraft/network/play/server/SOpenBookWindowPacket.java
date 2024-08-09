/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

public class SOpenBookWindowPacket
implements IPacket<IClientPlayNetHandler> {
    private Hand hand;

    public SOpenBookWindowPacket() {
    }

    public SOpenBookWindowPacket(Hand hand) {
        this.hand = hand;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.hand = packetBuffer.readEnumValue(Hand.class);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.hand);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleOpenBookPacket(this);
    }

    public Hand getHand() {
        return this.hand;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

