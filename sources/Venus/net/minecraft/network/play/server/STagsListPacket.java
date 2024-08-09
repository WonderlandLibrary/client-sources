/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITagCollectionSupplier;

public class STagsListPacket
implements IPacket<IClientPlayNetHandler> {
    private ITagCollectionSupplier tags;

    public STagsListPacket() {
    }

    public STagsListPacket(ITagCollectionSupplier iTagCollectionSupplier) {
        this.tags = iTagCollectionSupplier;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.tags = ITagCollectionSupplier.readTagCollectionSupplierFromBuffer(packetBuffer);
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        this.tags.writeTagCollectionSupplierToBuffer(packetBuffer);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleTags(this);
    }

    public ITagCollectionSupplier getTags() {
        return this.tags;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

