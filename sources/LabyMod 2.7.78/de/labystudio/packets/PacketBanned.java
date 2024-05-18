package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketBanned extends Packet
{
    private String reason;
    private long until;

    public PacketBanned(String reason, long until)
    {
        this.reason = reason;
        this.until = until;
    }

    public PacketBanned()
    {
    }

    public void read(PacketBuf buf)
    {
        this.reason = buf.readString();
        this.until = buf.readLong();
    }

    public void write(PacketBuf buf)
    {
        buf.writeString(this.reason);
        buf.writeLong(this.until);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getReason()
    {
        return this.reason;
    }

    public long getUntil()
    {
        return this.until;
    }
}
