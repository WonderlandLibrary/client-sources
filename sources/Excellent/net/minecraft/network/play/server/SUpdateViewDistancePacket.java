package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class SUpdateViewDistancePacket implements IPacket<IClientPlayNetHandler>
{
    private int viewDistance;

    public SUpdateViewDistancePacket()
    {
    }

    public SUpdateViewDistancePacket(int viewDistanceIn)
    {
        this.viewDistance = viewDistanceIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.viewDistance = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.viewDistance);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleUpdateViewDistancePacket(this);
    }

    public int getViewDistance()
    {
        return this.viewDistance;
    }
}
