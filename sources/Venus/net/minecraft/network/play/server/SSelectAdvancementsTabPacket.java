/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SSelectAdvancementsTabPacket
implements IPacket<IClientPlayNetHandler> {
    @Nullable
    private ResourceLocation tab;

    public SSelectAdvancementsTabPacket() {
    }

    public SSelectAdvancementsTabPacket(@Nullable ResourceLocation resourceLocation) {
        this.tab = resourceLocation;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSelectAdvancementsTab(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        if (packetBuffer.readBoolean()) {
            this.tab = packetBuffer.readResourceLocation();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBoolean(this.tab != null);
        if (this.tab != null) {
            packetBuffer.writeResourceLocation(this.tab);
        }
    }

    @Nullable
    public ResourceLocation getTab() {
        return this.tab;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

