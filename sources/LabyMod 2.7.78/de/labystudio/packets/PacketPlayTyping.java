package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.handling.PacketHandler;

public class PacketPlayTyping extends Packet
{
    private LabyModPlayer player;
    private LabyModPlayer inChatWith;
    private boolean typing;

    public PacketPlayTyping(LabyModPlayer player, LabyModPlayer inChatWith, boolean typing)
    {
        this.player = player;
        this.inChatWith = inChatWith;
        this.typing = typing;
    }

    public PacketPlayTyping()
    {
    }

    public void read(PacketBuf buf)
    {
        this.player = buf.readPlayer();
        this.inChatWith = buf.readPlayer();
        this.typing = buf.readBoolean();
    }

    public void write(PacketBuf buf)
    {
        buf.writePlayer(this.player);
        buf.writePlayer(this.inChatWith);
        buf.writeBoolean(this.typing);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public LabyModPlayer getInChatWith()
    {
        return this.inChatWith;
    }

    public LabyModPlayer getPlayer()
    {
        return this.player;
    }

    public boolean isTyping()
    {
        return this.typing;
    }
}
