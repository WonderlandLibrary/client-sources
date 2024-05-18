package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.handling.PacketHandler;

public class PacketLoginTime extends Packet
{
    private LabyModPlayer player;
    private long dateJoined;
    private long lastOnline;

    public PacketLoginTime(LabyModPlayer player, long dateJoined, long lastOnline)
    {
        this.player = player;
        this.dateJoined = dateJoined;
        this.lastOnline = lastOnline;
    }

    public PacketLoginTime()
    {
    }

    public void read(PacketBuf buf)
    {
        this.player = buf.readPlayer();
        this.dateJoined = buf.readLong();
        this.lastOnline = buf.readLong();
    }

    public void write(PacketBuf buf)
    {
        buf.writePlayer(this.player);
        buf.writeLong(this.dateJoined);
        buf.writeLong(this.lastOnline);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public long getDateJoined()
    {
        return this.dateJoined;
    }

    public long getLastOnline()
    {
        return this.lastOnline;
    }

    public LabyModPlayer getPlayer()
    {
        return this.player;
    }
}
