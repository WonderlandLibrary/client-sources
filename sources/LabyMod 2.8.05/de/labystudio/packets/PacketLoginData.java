package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;
import java.util.UUID;

public class PacketLoginData extends Packet
{
    private UUID id;
    private String name;
    private String motd;

    public PacketLoginData()
    {
    }

    public PacketLoginData(UUID id, String name, String motd)
    {
        this.id = id;
        this.name = name;
        this.motd = motd;
    }

    public void read(PacketBuf buf)
    {
        this.id = UUID.fromString(buf.readString());
        this.name = buf.readString();
        this.motd = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        if (this.id == null)
        {
            buf.writeString(UUID.randomUUID().toString());
        }
        else
        {
            buf.writeString(this.id.toString());
        }

        buf.writeString(this.name);
        buf.writeString(this.motd);
    }

    public int getId()
    {
        return 0;
    }

    public UUID getUUID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public String getMotd()
    {
        return this.motd;
    }
}
