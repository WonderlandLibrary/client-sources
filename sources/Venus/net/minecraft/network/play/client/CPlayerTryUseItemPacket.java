/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Hand;

public class CPlayerTryUseItemPacket
implements IPacket<IServerPlayNetHandler> {
    private Hand hand;

    public CPlayerTryUseItemPacket() {
    }

    public CPlayerTryUseItemPacket(Hand hand) {
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
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processTryUseItem(this);
    }

    public Hand getHand() {
        return this.hand;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

