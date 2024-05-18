package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayerRequester;
import de.labystudio.handling.PacketHandler;

public class PacketPlayDenyFriendRequest extends Packet
{
    private LabyModPlayerRequester denied;

    public PacketPlayDenyFriendRequest(LabyModPlayerRequester denied)
    {
        this.denied = denied;
    }

    public PacketPlayDenyFriendRequest()
    {
    }

    public void read(PacketBuf buf)
    {
        this.denied = (LabyModPlayerRequester)buf.readPlayer();
    }

    public void write(PacketBuf buf)
    {
        buf.writePlayer(this.denied);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public LabyModPlayerRequester getDenied()
    {
        return this.denied;
    }
}
