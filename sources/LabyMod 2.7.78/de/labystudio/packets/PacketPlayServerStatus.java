package de.labystudio.packets;

import de.labystudio.chat.ServerInfo;
import de.labystudio.handling.PacketHandler;

public class PacketPlayServerStatus extends Packet
{
    private String serverIp;
    private int port;
    private String serverName = null;

    public PacketPlayServerStatus(String serverIp, int port)
    {
        this.serverIp = serverIp;
        this.port = port;
        this.serverName = null;
    }

    public PacketPlayServerStatus()
    {
    }

    public PacketPlayServerStatus(String serverIp, int port, String serverName)
    {
        this.serverIp = serverIp;
        this.port = port;
        this.serverName = serverName;
    }

    public void read(PacketBuf buf)
    {
        this.serverIp = buf.readString();
        this.port = buf.readInt();

        if (buf.readBoolean())
        {
            this.serverName = buf.readString();
        }
    }

    public void write(PacketBuf buf)
    {
        buf.writeString(this.serverIp);
        buf.writeInt(this.port);

        if (this.serverName != null)
        {
            buf.writeBoolean(true);
            buf.writeString(this.serverName);
        }
        else
        {
            buf.writeBoolean(false);
        }
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getServerIp()
    {
        return this.serverIp;
    }

    public int getPort()
    {
        return this.port;
    }

    public String getServerName()
    {
        return this.serverName;
    }

    public ServerInfo build()
    {
        return this.serverName == null ? new ServerInfo(this.serverIp, this.port) : new ServerInfo(this.serverIp, this.port, this.serverName);
    }
}
