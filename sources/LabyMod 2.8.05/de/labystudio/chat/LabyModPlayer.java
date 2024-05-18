package de.labystudio.chat;

import java.util.UUID;

public class LabyModPlayer
{
    private String timeZone;
    private long lastOnline;
    private long joinedFirst;
    private int contactsAmount;
    private String name;
    protected UUID id;
    private String motd;
    private ServerInfo serverInfo;
    private LabyModPlayer.OnlineStatus status;
    public int messages;
    private boolean notify;
    private boolean typing;
    private long lastMessage;

    public LabyModPlayer(String name, UUID id, boolean notify)
    {
        this(name, id, "* Offline *", LabyModPlayer.OnlineStatus.OFFLINE);
        this.notify = notify;
    }

    public LabyModPlayer(String name, UUID uuid, String motd, LabyModPlayer.OnlineStatus status)
    {
        this.messages = 0;
        this.notify = true;
        this.typing = false;
        this.id = uuid;
        this.name = name;
        this.motd = motd;
        this.status = status;
        this.timeZone = "";
        this.lastOnline = 0L;
        this.contactsAmount = 0;
        this.joinedFirst = 0L;
        this.serverInfo = new ServerInfo();
    }

    public LabyModPlayer(String name, UUID uuid, String motd, LabyModPlayer.OnlineStatus status, String timeZone, long lastOnline, long joinedFirst, int contactsAmount)
    {
        this(name, uuid, motd, status);
        this.timeZone = timeZone;
        this.lastOnline = lastOnline;
        this.joinedFirst = joinedFirst;
        this.contactsAmount = contactsAmount;
    }

    public boolean isNotify()
    {
        return this.notify;
    }

    public void setNotify(boolean notify)
    {
        this.notify = notify;
    }

    public LabyModPlayer.OnlineStatus getStatus()
    {
        return this.status;
    }

    public String getMotd()
    {
        return this.motd;
    }

    public UUID getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public void updateMotd(String motd)
    {
        this.motd = motd;
    }

    public void setOnline(LabyModPlayer.OnlineStatus status)
    {
        this.status = status;
    }

    public boolean isRequest()
    {
        return false;
    }

    public int getContactsAmount()
    {
        return this.contactsAmount;
    }

    public long getJoinedFirst()
    {
        return this.joinedFirst;
    }

    public boolean isOnline()
    {
        return this.status != LabyModPlayer.OnlineStatus.OFFLINE;
    }

    public long getLastOnline()
    {
        if (this.isOnline())
        {
            this.lastOnline = System.currentTimeMillis();
            return System.currentTimeMillis();
        }
        else
        {
            return this.lastOnline;
        }
    }

    public void setLastOnline(long lastOnline)
    {
        this.lastOnline = lastOnline;
    }

    public String getTimeZone()
    {
        return this.timeZone;
    }

    public ServerInfo getServerInfo()
    {
        return this.serverInfo;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof LabyModPlayer))
        {
            return false;
        }
        else
        {
            LabyModPlayer labymodplayer = (LabyModPlayer)obj;
            return labymodplayer.isRequest() != this.isRequest() ? false : labymodplayer.getId() == this.getId();
        }
    }

    public void updateServer(ServerInfo info)
    {
        this.serverInfo = info;
    }

    public void set(LabyModPlayer player)
    {
        this.updateServer(player.getServerInfo());
        this.updateMotd(player.getMotd());
        this.status = player.getStatus();
        this.contactsAmount = player.getContactsAmount();
        this.timeZone = player.getTimeZone();
    }

    public void updateTyping(boolean typing)
    {
        this.typing = typing;
    }

    public boolean isTyping()
    {
        return this.typing;
    }

    public void setLastMessage(long lastMessage)
    {
        this.lastMessage = lastMessage;
    }

    public long getLastMessage()
    {
        return this.lastMessage;
    }

    public static enum OnlineStatus
    {
        ONLINE((byte)0),
        AWAY((byte)1),
        BUSY((byte)2),
        OFFLINE((byte) - 1);

        private byte packetId;

        private OnlineStatus(byte packetId)
        {
            this.packetId = packetId;
        }

        public byte getPacketId()
        {
            return this.packetId;
        }

        public static LabyModPlayer.OnlineStatus fromPacketId(int packetId)
        {
            for (LabyModPlayer.OnlineStatus labymodplayer$onlinestatus : values())
            {
                if (labymodplayer$onlinestatus.packetId == packetId)
                {
                    return labymodplayer$onlinestatus;
                }
            }

            return OFFLINE;
        }
    }
}
