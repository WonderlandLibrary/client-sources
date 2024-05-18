package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;
import de.labystudio.utils.MojangService;

public class PacketMojangStatus extends Packet
{
    private MojangService service;
    private String status = "green";

    public PacketMojangStatus()
    {
    }

    public PacketMojangStatus(MojangService ms, String status)
    {
        this.service = ms;
        this.status = status;
    }

    public void read(PacketBuf buf)
    {
        this.service = MojangService.fromId(buf.readInt());
        this.status = buf.readString();
    }

    public void write(PacketBuf buf)
    {
        buf.writeInt(this.service.getId());
        buf.writeString(this.status);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public MojangService getMojangService()
    {
        return this.service;
    }

    public String getStatus()
    {
        return this.status;
    }
}
