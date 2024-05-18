package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.handling.PacketHandler;

public class PacketPlayFriendPlayingOn extends Packet
{
    private LabyModPlayer player;
    private String gameModeName;

    public PacketPlayFriendPlayingOn(LabyModPlayer player, String gameModeName)
    {
        this.player = player;
        this.gameModeName = gameModeName;
    }

    public PacketPlayFriendPlayingOn()
    {
    }

    public void read(PacketBuf buf)
    {
        this.player = buf.readPlayer();
        this.gameModeName = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        buf.writePlayer(this.player);
        buf.writeString(this.gameModeName);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getGameModeName()
    {
        return this.gameModeName;
    }

    public LabyModPlayer getPlayer()
    {
        return this.player;
    }
}
