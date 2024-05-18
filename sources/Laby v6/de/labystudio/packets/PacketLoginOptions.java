package de.labystudio.packets;

import de.labystudio.chat.LabyModPlayer;
import de.labystudio.handling.PacketHandler;
import java.util.TimeZone;

public class PacketLoginOptions extends Packet
{
    private boolean showServer;
    private LabyModPlayer.OnlineStatus status;
    private TimeZone timeZone;

    public PacketLoginOptions(boolean showServer, LabyModPlayer.OnlineStatus status, TimeZone timeZone)
    {
        this.showServer = showServer;
        this.status = status;
        this.timeZone = timeZone;
    }

    public PacketLoginOptions()
    {
    }

    public void read(PacketBuf buf)
    {
        this.showServer = buf.readBoolean();
        this.status = buf.readOnlineStatus();
        this.timeZone = TimeZone.getTimeZone(buf.readString());
    }

    public void write(PacketBuf buf)
    {
        buf.writeBoolean(this.showServer);
        buf.writeOnlineStatus(this.status);
        buf.writeString(this.timeZone.getID());
    }

    public void handle(PacketHandler packetHandler)
    {
        packetHandler.handle(this);
    }

    public PacketLoginOptions.Options getOptions()
    {
        return new PacketLoginOptions.Options(this.showServer, this.status, this.timeZone);
    }

    public static class Options
    {
        private final boolean showServer;
        private final LabyModPlayer.OnlineStatus onlineStatus;
        private final TimeZone timeZone;

        public Options(boolean showServer, LabyModPlayer.OnlineStatus onlineStatus, TimeZone timeZone)
        {
            this.showServer = showServer;
            this.timeZone = timeZone;
            this.onlineStatus = onlineStatus;
        }

        public boolean isShowServer()
        {
            return this.showServer;
        }

        public LabyModPlayer.OnlineStatus getOnlineStatus()
        {
            return this.onlineStatus;
        }

        public TimeZone getTimeZone()
        {
            return this.timeZone;
        }
    }
}
