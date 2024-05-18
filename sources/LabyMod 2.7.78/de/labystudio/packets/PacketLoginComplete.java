package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketLoginComplete extends Packet
{
    private String capeKey;

    public PacketLoginComplete(String string)
    {
        this.capeKey = string;
    }

    public PacketLoginComplete()
    {
    }

    public void read(PacketBuf buf)
    {
        this.capeKey = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        buf.writeString(this.capeKey);
    }

    public int getId()
    {
        return 2;
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getString()
    {
        return this.capeKey;
    }
}
