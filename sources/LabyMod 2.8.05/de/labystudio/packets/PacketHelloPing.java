package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketHelloPing extends Packet
{
    private long a;

    public PacketHelloPing()
    {
    }

    public PacketHelloPing(long a)
    {
    }

    public void read(PacketBuf buf)
    {
        this.a = buf.readLong();
        buf.readInt();
    }

    public void write(PacketBuf buf)
    {
        buf.writeLong(this.a);
        buf.writeInt(17);
    }

    public int getId()
    {
        return 0;
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }
}
