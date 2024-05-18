package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.handling.PacketHandler;

public class PacketMessage extends Packet
{
    private LabyModPlayer sender;
    private LabyModPlayer to;
    private String message;
    private long sentTime;
    private long fileSize;
    private double audioTime;

    public PacketMessage(LabyModPlayer sender, LabyModPlayer to, String message, long fileSize, double time, long sentTime)
    {
        this.sender = sender;
        this.to = to;
        this.message = message;
        this.fileSize = fileSize;
        this.audioTime = time;
        this.sentTime = sentTime;
    }

    public PacketMessage()
    {
    }

    public void read(PacketBuf buf)
    {
        this.sender = buf.readPlayer();
        this.to = buf.readPlayer();
        this.message = buf.readString();
        this.fileSize = buf.readLong();
        this.audioTime = buf.readDouble();
        this.sentTime = buf.readLong();
    }

    public void write(PacketBuf buf)
    {
        buf.writePlayer(this.sender);
        buf.writePlayer(this.to);
        buf.writeString(this.message);
        buf.writeLong(this.fileSize);
        buf.writeDouble(this.audioTime);
        buf.writeLong(this.sentTime);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public double getAudioTime()
    {
        return this.audioTime;
    }

    public long getFileSize()
    {
        return this.fileSize;
    }

    public String getMessage()
    {
        return this.message;
    }

    public LabyModPlayer getSender()
    {
        return this.sender;
    }

    public LabyModPlayer getTo()
    {
        return this.to;
    }

    public long getSentTime()
    {
        return this.sentTime;
    }
}
