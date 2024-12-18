package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class SAnimateHandPacket implements IPacket<IClientPlayNetHandler>
{
    private int entityId;
    private int type;

    public SAnimateHandPacket()
    {
    }

    public SAnimateHandPacket(Entity entityIn, int typeIn)
    {
        this.entityId = entityIn.getEntityId();
        this.type = typeIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarInt();
        this.type = buf.readUnsignedByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.entityId);
        buf.writeByte(this.type);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleAnimation(this);
    }

    public int getEntityID()
    {
        return this.entityId;
    }

    public int getAnimationType()
    {
        return this.type;
    }
}
