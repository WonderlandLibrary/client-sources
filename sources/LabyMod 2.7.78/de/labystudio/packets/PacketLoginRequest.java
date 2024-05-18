package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.chat.LabyModPlayerRequester;
import de.labystudio.handling.PacketHandler;
import java.util.ArrayList;
import java.util.List;

public class PacketLoginRequest extends Packet
{
    private List<LabyModPlayerRequester> requesters;

    public PacketLoginRequest(List<LabyModPlayerRequester> requesters)
    {
        this.requesters = requesters;
    }

    public PacketLoginRequest()
    {
    }

    public List<LabyModPlayerRequester> getRequests()
    {
        return this.requesters;
    }

    public void read(PacketBuf buf)
    {
        this.requesters = new ArrayList();
        int i = buf.readInt();

        for (int j = 0; j < i; ++j)
        {
            this.requesters.add((LabyModPlayerRequester)buf.readPlayer());
        }
    }

    public void write(PacketBuf buf)
    {
        buf.writeInt(this.getRequests().size());

        for (int i = 0; i < this.getRequests().size(); ++i)
        {
            buf.writePlayer((LabyModPlayer)this.getRequests().get(i));
        }
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }
}
