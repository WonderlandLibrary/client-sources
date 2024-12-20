package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.IOException;

public class SCameraPacket implements IPacket<IClientPlayNetHandler>
{
    public int entityId;

    public SCameraPacket()
    {
    }

    public SCameraPacket(Entity entityIn)
    {
        this.entityId = entityIn.getEntityId();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.entityId = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.entityId);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleCamera(this);
    }

    @Nullable
    public Entity getEntity(World worldIn)
    {
        return worldIn.getEntityByID(this.entityId);
    }
}
