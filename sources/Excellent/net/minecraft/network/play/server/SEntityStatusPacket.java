package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class SEntityStatusPacket implements IPacket<IClientPlayNetHandler>
{
    private int entityId;
    private byte logicOpcode;

    public SEntityStatusPacket()
    {
    }

    public SEntityStatusPacket(Entity entityIn, byte opcodeIn)
    {
        this.entityId = entityIn.getEntityId();
        this.logicOpcode = opcodeIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readInt();
        this.logicOpcode = buf.readByte();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeInt(this.entityId);
        buf.writeByte(this.logicOpcode);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleEntityStatus(this);
    }

    public Entity getEntity(World worldIn)
    {
        return worldIn.getEntityByID(this.entityId);
    }

    public byte getOpCode()
    {
        return this.logicOpcode;
    }
}
