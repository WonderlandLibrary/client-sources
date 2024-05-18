package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.handling.PacketHandler;
import java.util.ArrayList;
import java.util.List;

public class PacketLoginFriend extends Packet
{
    private List<LabyModPlayer> friends;

    public PacketLoginFriend(List<LabyModPlayer> friends)
    {
        this.friends = friends;
    }

    public PacketLoginFriend()
    {
    }

    public void read(PacketBuf buf)
    {
        List<LabyModPlayer> list = new ArrayList();
        int i = buf.readInt();

        for (int j = 0; j < i; ++j)
        {
            list.add(buf.readPlayer());
        }

        this.friends = new ArrayList();
        this.friends.addAll(list);
    }

    public void write(PacketBuf buf)
    {
        buf.writeInt(this.getFriends().size());

        for (int i = 0; i < this.getFriends().size(); ++i)
        {
            LabyModPlayer labymodplayer = (LabyModPlayer)this.getFriends().get(i);
            buf.writePlayer(labymodplayer);
        }
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public List<LabyModPlayer> getFriends()
    {
        return this.friends;
    }
}
