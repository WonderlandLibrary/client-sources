package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketKick extends Packet
{
    private String cause;

    public PacketKick(String cause)
    {
        this.cause = cause;
    }

    public PacketKick()
    {
    }

    public void read(PacketBuf buf)
    {
        this.cause = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        buf.writeString(this.getReason());
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getReason()
    {
        return this.cause;
    }
}
