package net.minecraft.network.play.client;

import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

import java.io.IOException;

public class CUpdateBeaconPacket implements IPacket<IServerPlayNetHandler>
{
    private int primaryEffect;
    private int secondaryEffect;

    public CUpdateBeaconPacket()
    {
    }

    public CUpdateBeaconPacket(int primaryEffectIn, int secondaryEffectIn)
    {
        this.primaryEffect = primaryEffectIn;
        this.secondaryEffect = secondaryEffectIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.primaryEffect = buf.readVarInt();
        this.secondaryEffect = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.primaryEffect);
        buf.writeVarInt(this.secondaryEffect);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IServerPlayNetHandler handler)
    {
        handler.processUpdateBeacon(this);
    }

    public int getPrimaryEffect()
    {
        return this.primaryEffect;
    }

    public int getSecondaryEffect()
    {
        return this.secondaryEffect;
    }
}
