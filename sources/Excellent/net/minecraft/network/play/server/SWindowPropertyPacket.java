package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class SWindowPropertyPacket implements IPacket<IClientPlayNetHandler>
{
    private int windowId;
    private int property;
    private int value;

    public SWindowPropertyPacket()
    {
    }

    public SWindowPropertyPacket(int windowIdIn, int propertyIn, int valueIn)
    {
        this.windowId = windowIdIn;
        this.property = propertyIn;
        this.value = valueIn;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleWindowProperty(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.windowId = buf.readUnsignedByte();
        this.property = buf.readShort();
        this.value = buf.readShort();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeByte(this.windowId);
        buf.writeShort(this.property);
        buf.writeShort(this.value);
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public int getProperty()
    {
        return this.property;
    }

    public int getValue()
    {
        return this.value;
    }
}
