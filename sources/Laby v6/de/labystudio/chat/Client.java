package de.labystudio.chat;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.packets.EnumConnectionState;
import de.labystudio.packets.PacketDisconnect;
import de.labystudio.packets.PacketLoginOptions;
import de.labystudio.packets.PacketPlayChangeOptions;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class Client implements Runnable
{
    private Thread primaryThread;
    private ClientConnection clientConnection = new ClientConnection(this);
    private boolean running;
    protected List<LabyModPlayer> friends = new ArrayList();
    protected List<LabyModPlayerRequester> requests = new ArrayList();
    protected long lastOnline;
    protected long firstJoined;

    public PacketLoginOptions.Options getOptions()
    {
        return new PacketLoginOptions.Options(this.isShowServer(), getOnlineStatus(), this.getTimeZone());
    }

    public static LabyModPlayer.OnlineStatus getOnlineStatus()
    {
        return LabyModPlayer.OnlineStatus.fromPacketId(ConfigManager.settings.onlineStatus);
    }

    public boolean isShowServer()
    {
        return ConfigManager.settings.showConntectedIP;
    }

    public void setOnlineStatus(LabyModPlayer.OnlineStatus status)
    {
        ConfigManager.settings.onlineStatus = status.getPacketId();
        this.getClientConnection().sendPacket(new PacketPlayChangeOptions(this.isShowServer(), status, this.getTimeZone()));
    }

    public static boolean isBusy()
    {
        return getOnlineStatus() == LabyModPlayer.OnlineStatus.BUSY;
    }

    public void init()
    {
        this.clientConnection.init();
        this.running = true;
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            public void run()
            {
                if (Client.this.clientConnection.getState() != EnumConnectionState.OFFLINE)
                {
                    Client.this.clientConnection.sendPacket(new PacketDisconnect("Shutdown"));
                }
            }
        }));
    }

    public void run()
    {
    }

    public boolean isOnline()
    {
        return this.clientConnection.getState() == EnumConnectionState.PLAY;
    }

    public ClientConnection getClientConnection()
    {
        return this.clientConnection;
    }

    public List<LabyModPlayer> getFriends()
    {
        return this.friends;
    }

    public List<LabyModPlayerRequester> getRequests()
    {
        return this.requests;
    }

    public LabyModPlayer build()
    {
        LabyModPlayer labymodplayer = null;
        String s = LabyMod.getInstance().getPlayerName();
        UUID uuid = LabyMod.getInstance().getPlayerUUID();
        String s1 = ConfigManager.settings.motd;
        LabyMod.getInstance().getClient();
        labymodplayer = new LabyModPlayer(s, uuid, s1, getOnlineStatus(), "", System.currentTimeMillis(), this.firstJoined, this.getFriends().size());
        labymodplayer.updateServer(new ServerInfo(" ", 0));
        return labymodplayer;
    }

    public void reconnect()
    {
        this.disconnect();
        this.connect();
    }

    public void disconnect()
    {
        if (this.getClientConnection().getState() != EnumConnectionState.OFFLINE)
        {
            ClientConnection.executor.execute(new Runnable()
            {
                public void run()
                {
                    Client.this.getClientConnection().setConnectionState(EnumConnectionState.OFFLINE);

                    if (Client.this.getClientConnection().ch == null)
                    {
                        LabyMod.getInstance().getClient().requests.clear();
                    }
                    else
                    {
                        Client.this.getClientConnection().ch.writeAndFlush(new PacketDisconnect("Logout")).addListener(new ChannelFutureListener()
                        {
                            public void operationComplete(ChannelFuture arg0) throws Exception
                            {
                                Client.this.getClientConnection().ch.close();
                                Iterator<LabyModPlayer> iterator = Client.this.getFriends().iterator();

                                while (iterator.hasNext())
                                {
                                    ((LabyModPlayer)iterator.next()).setOnline(LabyModPlayer.OnlineStatus.OFFLINE);
                                }

                                LabyMod.getInstance().getClient().requests.clear();
                                Client.this.clientConnection = new ClientConnection(Client.this);
                            }
                        });
                    }
                }
            });
        }
    }

    public void connect()
    {
        if (this.getClientConnection().getState() != EnumConnectionState.PLAY)
        {
            this.getClientConnection().init();
            this.getClientConnection().connect();
        }
    }

    public void updatePlayer(LabyModPlayer toUpdate)
    {
        for (LabyModPlayer labymodplayer : this.getFriends())
        {
            if (labymodplayer.getId().equals(toUpdate.getId()))
            {
                labymodplayer.set(toUpdate);
            }
        }
    }

    public void newAccount()
    {
        this.friends.clear();
        this.requests.clear();
        this.reconnect();
        ChatHandler.getHandler().newAccount();
    }

    public boolean hasNotifications(LabyModPlayer friend)
    {
        for (LabyModPlayer labymodplayer : this.getFriends())
        {
            if (labymodplayer.getId().equals(friend.getId()))
            {
                return labymodplayer.isNotify();
            }
        }

        return false;
    }

    public void setTyping(LabyModPlayer player, boolean typing)
    {
        for (LabyModPlayer labymodplayer : this.getFriends())
        {
            if (labymodplayer.getId().equals(player.getId()))
            {
                labymodplayer.updateTyping(typing);
            }
        }
    }

    public boolean isTyping(LabyModPlayer player)
    {
        for (LabyModPlayer labymodplayer : this.getFriends())
        {
            if (labymodplayer.getId().equals(player.getId()))
            {
                return labymodplayer.isTyping();
            }
        }

        return false;
    }

    public TimeZone getTimeZone()
    {
        return Calendar.getInstance().getTimeZone();
    }
}
