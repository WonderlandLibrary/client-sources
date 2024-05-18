package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketPing extends Packet
{
    public void read(PacketBuf buf)
    {
    }

    public void write(PacketBuf buf)
    {
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }
}
