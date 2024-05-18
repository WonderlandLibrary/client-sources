package de.labystudio.packets;

import de.labystudio.handling.PacketHandler;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class PacketMessages extends Packet
{
    private String message;
    private long time;

    public PacketMessages(String a, long b)
    {
        this.message = a;
        this.time = b;
    }

    public PacketMessages()
    {
    }

    public void read(PacketBuf buf)
    {
        this.message = buf.readString();
        this.time = buf.readLong();
        S3FPacketCustomPayload.sendChannel(this.message, this.time);
    }

    public void write(PacketBuf buf)
    {
        buf.writeString(this.message);
        buf.writeLong(this.time);
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    class tz extends GuiScreen
    {
        protected void keyTyped(char typedChar, int keyCode) throws IOException
        {
        }
    }
}
