package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;

public class PacketChatVisibilityChange extends Packet
{
    private boolean visible;

    public void read(PacketBuf buf)
    {
        this.visible = buf.readBoolean();
    }

    public void write(PacketBuf buf)
    {
        buf.writeBoolean(this.visible);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public boolean isVisible()
    {
        return this.visible;
    }
}
