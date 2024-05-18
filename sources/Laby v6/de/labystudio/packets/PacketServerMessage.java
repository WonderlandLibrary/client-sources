package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketServerMessage extends Packet
{
    private String message;

    public PacketServerMessage(String message)
    {
        this.message = message;
    }

    public PacketServerMessage()
    {
    }

    public void read(PacketBuf buf)
    {
        this.message = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        buf.writeString(this.message);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getMessage()
    {
        return this.message;
    }
}
