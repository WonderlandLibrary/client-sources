package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketDisconnect extends Packet
{
    private String reason;

    public PacketDisconnect()
    {
    }

    public PacketDisconnect(String reason)
    {
        this.reason = reason;
    }

    public void read(PacketBuf buf)
    {
        this.reason = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        if (this.getReason() == null)
        {
            buf.writeString("Client Error");
        }
        else
        {
            buf.writeString(this.getReason());
        }
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getReason()
    {
        return this.reason;
    }
}
