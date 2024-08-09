package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITagCollectionSupplier;

import java.io.IOException;

public class STagsListPacket implements IPacket<IClientPlayNetHandler>
{
    private ITagCollectionSupplier tags;

    public STagsListPacket()
    {
    }

    public STagsListPacket(ITagCollectionSupplier p_i242087_1_)
    {
        this.tags = p_i242087_1_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.tags = ITagCollectionSupplier.readTagCollectionSupplierFromBuffer(buf);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        this.tags.writeTagCollectionSupplierToBuffer(buf);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleTags(this);
    }

    public ITagCollectionSupplier getTags()
    {
        return this.tags;
    }
}
