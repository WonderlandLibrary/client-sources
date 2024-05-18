package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.chat.ServerInfo;
import de.labystudio.handling.PacketHandler;

public class PacketPlayFriendStatus extends Packet
{
    private LabyModPlayer player;
    private ServerInfo playerInfo;

    public PacketPlayFriendStatus(LabyModPlayer player, ServerInfo playerInfo)
    {
        this.player = player;
        this.playerInfo = playerInfo;
    }

    public PacketPlayFriendStatus()
    {
    }

    public void read(PacketBuf buf)
    {
        this.player = buf.readPlayer();
        this.playerInfo = buf.readServerInfo();
    }

    public void write(PacketBuf buf)
    {
        buf.writePlayer(this.player);
        buf.writeServerInfo(this.playerInfo);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public LabyModPlayer getPlayer()
    {
        return this.player;
    }

    public ServerInfo getPlayerInfo()
    {
        return this.playerInfo;
    }
}
