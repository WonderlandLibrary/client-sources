package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

import java.io.IOException;

public class SOpenBookWindowPacket implements IPacket<IClientPlayNetHandler>
{
    private Hand hand;

    public SOpenBookWindowPacket()
    {
    }

    public SOpenBookWindowPacket(Hand hand)
    {
        this.hand = hand;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.hand = buf.readEnumValue(Hand.class);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeEnumValue(this.hand);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleOpenBookPacket(this);
    }

    public Hand getHand()
    {
        return this.hand;
    }
}
