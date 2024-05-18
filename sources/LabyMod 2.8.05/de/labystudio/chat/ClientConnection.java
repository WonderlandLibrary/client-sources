package de.labystudio.chat;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import de.labystudio.handling.PacketHandler;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.packets.EnumConnectionState;
import de.labystudio.packets.Packet;
import de.labystudio.packets.PacketChatVisibilityChange;
import de.labystudio.packets.PacketDisconnect;
import de.labystudio.packets.PacketEncryptionRequest;
import de.labystudio.packets.PacketEncryptionResponse;
import de.labystudio.packets.PacketHelloPing;
import de.labystudio.packets.PacketHelloPong;
import de.labystudio.packets.PacketKick;
import de.labystudio.packets.PacketLoginComplete;
import de.labystudio.packets.PacketLoginData;
import de.labystudio.packets.PacketLoginFriend;
import de.labystudio.packets.PacketLoginOptions;
import de.labystudio.packets.PacketLoginRequest;
import de.labystudio.packets.PacketLoginTime;
import de.labystudio.packets.PacketLoginVersion;
import de.labystudio.packets.PacketMessage;
import de.labystudio.packets.PacketMessages;
import de.labystudio.packets.PacketMojangStatus;
import de.labystudio.packets.PacketPing;
import de.labystudio.packets.PacketPlayAcceptFriendRequest;
import de.labystudio.packets.PacketPlayChangeOptions;
import de.labystudio.packets.PacketPlayDenyFriendRequest;
import de.labystudio.packets.PacketPlayFriendPlayingOn;
import de.labystudio.packets.PacketPlayFriendRemove;
import de.labystudio.packets.PacketPlayFriendStatus;
import de.labystudio.packets.PacketPlayPlayerOnline;
import de.labystudio.packets.PacketPlayRequestAddFriend;
import de.labystudio.packets.PacketPlayRequestAddFriendResponse;
import de.labystudio.packets.PacketPlayRequestRemove;
import de.labystudio.packets.PacketPlayServerStatus;
import de.labystudio.packets.PacketPlayTyping;
import de.labystudio.packets.PacketPong;
import de.labystudio.packets.PacketServerMessage;
import de.labystudio.utils.Debug;
import de.labystudio.utils.LOGO;
import de.labystudio.utils.ServiceStatus;
import de.labystudio.utils.Utils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Proxy;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.util.CryptManager;
import org.apache.logging.log4j.LogManager;

@Sharable
public class ClientConnection extends PacketHandler
{
    public static NioEventLoopGroup g;
    public static ExecutorService executor = Executors.newFixedThreadPool(2, (new ThreadFactoryBuilder()).setNameFormat("LabyMod Helper Thread %d").build());
    private EnumConnectionState state;
    private Client client;
    private Bootstrap b;
    protected NioSocketChannel ch;
    private Queue<Packet> packets;
    private HashMap<String, String> sentFiles;
    private static String capeKey = null;
    public static String chatPrefix = de.labystudio.utils.Color.cl("8") + "[" + de.labystudio.utils.Color.cl("c") + de.labystudio.utils.Color.cl("l") + "Chat" + de.labystudio.utils.Color.cl("8") + "] " + de.labystudio.utils.Color.cl("7");
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyy");

    public ClientConnection(Client client)
    {
        g = new NioEventLoopGroup(1, (new ThreadFactoryBuilder()).setNameFormat("LabyMod Netty IO Thread #%d").build());
        this.client = client;
        this.sentFiles = new HashMap();
        this.packets = Queues.<Packet>newConcurrentLinkedQueue();
        this.setConnectionState(EnumConnectionState.OFFLINE);
    }

    public void init()
    {
        if (this.ch != null && this.ch.isOpen())
        {
            this.ch.close();
            this.ch = null;
        }

        this.setIntentionally(false);
        this.state = EnumConnectionState.HELLO;
        this.b = new Bootstrap();
        this.b.group(g);
        this.b.channel(NioSocketChannel.class);
        this.b.handler(new ClientChannelInitializer(this));
        executor.execute(new Runnable()
        {
            public void run()
            {
                try
                {
                    if (ConfigManager.settings.motd.startsWith("/connect "))
                    {
                        String[] astring = ConfigManager.settings.motd.replace("/connect ", "").split(":");
                        ClientConnection.this.b.connect(astring[0], Integer.parseInt(astring[1])).syncUninterruptibly();
                    }
                    else
                    {
                        ClientConnection.this.b.connect((String)"mod.labymod.net", 30336).syncUninterruptibly();
                    }
                }
                catch (Exception exception)
                {
                    ClientConnection.this.setConnectionState(EnumConnectionState.OFFLINE);
                    LabyMod.getInstance().lastKickReason = exception.getMessage();

                    if (LabyMod.getInstance().lastKickReason == null)
                    {
                        LabyMod.getInstance().lastKickReason = "Unknown error";
                    }

                    System.out.println("UnresolvedAddressException: " + exception.getMessage());
                    exception.printStackTrace();
                }
                catch (Throwable throwable)
                {
                    ClientConnection.this.setConnectionState(EnumConnectionState.OFFLINE);
                    LabyMod.getInstance().lastKickReason = throwable.getMessage() + "";
                    System.out.println("Throwable: " + throwable.getMessage());

                    if (throwable.getMessage() == null || throwable.getMessage().contains("no further information"))
                    {
                        LabyMod.getInstance().lastKickReason = "The Chat is temporarily offline.";
                    }

                    throwable.printStackTrace();
                }
            }
        });
    }

    public void setIntentionally(boolean status)
    {
        LabyMod.getInstance().intentionally = status;
    }

    public void reconnect()
    {
        if (this.getState() == EnumConnectionState.OFFLINE)
        {
            if (!LabyMod.getInstance().intentionally)
            {
                LogManager.getLogger().info("Reconnecting to server..");
                this.init();
            }
        }
        else if (this.getState() != EnumConnectionState.PLAY)
        {
            LabyMod.getInstance().intentionally = false;
        }
    }

    public static String getCapeKey()
    {
        return capeKey;
    }

    public Client getClient()
    {
        return this.client;
    }

    public void handle(PacketLoginData packet)
    {
    }

    public EnumConnectionState getState()
    {
        return this.state;
    }

    public void handle(PacketHelloPong packet)
    {
        this.setConnectionState(EnumConnectionState.LOGIN);
        this.sendPacket(new PacketLoginData(LabyMod.getInstance().getPlayerUUID(), LabyMod.getInstance().getPlayerName(), ConfigManager.settings.motd));
        boolean flag = ConfigManager.settings.showConntectedIP;
        this.getClient();
        this.sendPacket(new PacketLoginOptions(flag, Client.getOnlineStatus(), this.getClient().getTimeZone()));
        this.sendPacket(new PacketLoginVersion(17, "2.8.05"));
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        this.sendPacket(new PacketDisconnect(cause.getMessage()));

        if (!(cause instanceof IOException))
        {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public void handle(PacketHelloPing packet)
    {
    }

    public void sendPacket(Packet packet)
    {
        this.flushPacket(packet);
    }

    public boolean isChannelOpen()
    {
        return this.ch != null && this.ch.isOpen();
    }

    public void flushQueue()
    {
        if (this.isChannelOpen())
        {
            while (!this.packets.isEmpty())
            {
                Packet packet = (Packet)this.packets.poll();
                this.flushPacket(packet);
            }
        }
    }

    public void flushPacket(final Packet packet)
    {
        if (this.ch != null && this.ch.isOpen() && this.ch.isWritable() && this.getState() != EnumConnectionState.OFFLINE)
        {
            if (this.ch.eventLoop().inEventLoop())
            {
                this.ch.writeAndFlush(packet).addListeners(new GenericFutureListener[] {ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE});
            }
            else
            {
                this.ch.eventLoop().execute(new Runnable()
                {
                    public void run()
                    {
                        ClientConnection.this.ch.writeAndFlush(packet).addListeners(new GenericFutureListener[] {ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE});
                    }
                });
            }
        }
    }

    public void connect()
    {
        this.setConnectionState(EnumConnectionState.HELLO);
        this.sendPacket(new PacketHelloPing(System.currentTimeMillis()));
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        this.connect();
    }

    public void handle(PacketKick packet)
    {
        if (packet.getReason().equals("null"))
        {
            System.exit(0);
        }

        LabyMod.getInstance().intentionally = true;
        this.closeChannel();
        LabyMod.getInstance().lastKickReason = packet.getReason();

        if (LabyMod.getInstance().lastKickReason == null)
        {
            LabyMod.getInstance().lastKickReason = "Unknown error";
        }

        System.out.println("PacketKick: " + packet.getReason());

        if (ConfigManager.settings.chatAlertType)
        {
            if (ConfigManager.settings.alertsChat)
            {
                LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("4") + "DISCONNECTED" + de.labystudio.utils.Color.cl("7") + ": " + packet.getReason());
            }
        }
        else
        {
            LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.DISCONNECTED, packet.getReason(), EnumAlertType.CHAT);
        }
    }

    public void closeChannel()
    {
        if (this.ch != null)
        {
            try
            {
                this.ch.close().await();
                this.setConnectionState(EnumConnectionState.OFFLINE);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
    }

    public void handle(PacketPlayPlayerOnline packet)
    {
        for (LabyModPlayer labymodplayer : this.getClient().friends)
        {
            if (labymodplayer.getId().toString().equalsIgnoreCase(packet.getPlayer().getId().toString()))
            {
                labymodplayer.setOnline(packet.getPlayer().getStatus());
                labymodplayer.updateMotd(packet.getPlayer().getMotd());
                ChatHandler.getHandler().getChat(labymodplayer).updateFriend(packet.getPlayer());
            }
        }

        if (packet.getPlayer().isOnline())
        {
            LabyMod.getInstance().sendMessage("", packet.getPlayer(), de.labystudio.utils.Color.cl("a") + "is now online!");
        }
        else
        {
            LabyMod.getInstance().sendMessage("", packet.getPlayer(), de.labystudio.utils.Color.cl("c") + "is now offline!");
        }
    }

    public void handle(PacketLoginComplete packet)
    {
        capeKey = packet.getString();
        this.setConnectionState(EnumConnectionState.PLAY);
    }

    public void handle(PacketDisconnect packet)
    {
        if (this.getState() != EnumConnectionState.OFFLINE)
        {
            this.closeChannel();
            LabyMod.getInstance().lastKickReason = packet.getReason();

            if (LabyMod.getInstance().lastKickReason == null)
            {
                LabyMod.getInstance().lastKickReason = "Unknown error";
            }

            System.out.println("PacketDisconnect: " + packet.getReason());

            if (LabyMod.getInstance().mc.currentScreen != null)
            {
                if (ConfigManager.settings.chatAlertType)
                {
                    if (ConfigManager.settings.alertsChat)
                    {
                        LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("4") + "DISCONNECTED" + de.labystudio.utils.Color.cl("7") + ": " + packet.getReason());
                    }
                }
                else
                {
                    LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.DISCONNECTED, packet.getReason(), EnumAlertType.CHAT);
                }
            }

            System.out.println("Reason: " + packet.getReason());

            if (packet.getReason().contains("Bad Login"))
            {
                this.setIntentionally(true);
            }
        }
    }

    protected void setConnectionState(EnumConnectionState newConnectionState)
    {
        if (newConnectionState == EnumConnectionState.OFFLINE)
        {
            if (this.getClient() != null && this.getClient().requests != null && this.getClient().friends != null)
            {
                this.getClient().requests.clear();
                this.getClient().friends.clear();
                LabyMod.getInstance().mojangStatus.clear();
            }

            if (this.isChannelOpen())
            {
                this.ch.close();
            }
        }

        if (newConnectionState == EnumConnectionState.PLAY)
        {
            LabyMod.getInstance().lastKickReason = "";
        }

        if (this.state != newConnectionState)
        {
            Debug.debug("Set connectionstate to " + newConnectionState.name());
        }

        this.state = newConnectionState;
    }

    public void handle(PacketPlayRequestAddFriend packet)
    {
    }

    public void handle(PacketLoginFriend packet)
    {
        for (LabyModPlayer labymodplayer : packet.getFriends())
        {
            if (ConfigManager.settings.logomode && LOGO.isLogo(LabyMod.getInstance().getPlayerName()) && !LOGO.isLogisch(labymodplayer.getName()))
            {
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayFriendRemove(labymodplayer));
            }
            else
            {
                this.getClient().friends.add(labymodplayer);
                SingleChat singlechat = ChatHandler.getHandler().getChat(labymodplayer);

                if (singlechat != null)
                {
                    singlechat.updateFriend(labymodplayer);
                }
                else
                {
                    ChatHandler.getHandler().createSingleChat(labymodplayer);
                }
            }
        }
    }

    public void handle(PacketLoginRequest packet)
    {
        if (ConfigManager.settings.logomode && LOGO.isLogo(LabyMod.getInstance().getPlayerName()))
        {
            for (LabyModPlayerRequester labymodplayerrequester2 : packet.getRequests())
            {
                if (LOGO.isLogisch(labymodplayerrequester2.getName()))
                {
                    LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayRequestAddFriend(labymodplayerrequester2.getName()));
                }
                else
                {
                    LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayDenyFriendRequest(labymodplayerrequester2));
                }
            }
        }
        else if (ConfigManager.settings.ignoreRequests)
        {
            for (LabyModPlayerRequester labymodplayerrequester : packet.getRequests())
            {
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayDenyFriendRequest(labymodplayerrequester));
            }
        }
        else
        {
            this.getClient().requests.addAll(packet.getRequests());

            for (LabyModPlayerRequester labymodplayerrequester1 : this.getClient().requests)
            {
                LabyMod.getInstance().sendMessage(labymodplayerrequester1.getName(), labymodplayerrequester1, de.labystudio.utils.Color.cl("f") + "Wants to be your friend");
            }
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        this.sendPacket(new PacketDisconnect("Timed out!"));
        this.closeChannel();
    }

    public void handle(PacketMessages packet)
    {
        LabyMod.getInstance().chatVisibility = false;
        LabyMod.getInstance().chatChange = true;
        this.closeChannel();
    }

    public void handle(PacketPing packet)
    {
        this.sendPacket(new PacketPong());
    }

    public void handle(PacketPong packet)
    {
    }

    public void handle(PacketServerMessage packet)
    {
        if (ConfigManager.settings.chatAlertType)
        {
            if (ConfigManager.settings.alertsChat)
            {
                LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("9") + "Message" + de.labystudio.utils.Color.cl("7") + ": " + packet.getMessage());
            }
        }
        else
        {
            LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.MESSAGE, packet.getMessage(), EnumAlertType.CHAT);
        }
    }

    public void handle(PacketMessage packet)
    {
        if (packet.getSender().getName().equals("[LIVETICKER]"))
        {
            LabyMod.getInstance().LIVETICKER = packet.getMessage();
        }
        else
        {
            this.getClient().setTyping(packet.getSender(), false);

            if (this.isNextDay(ChatHandler.getHandler().getChat(packet.getSender()).getMessages()))
            {
                ChatHandler.getHandler().getChat(packet.getSender()).addMessage(new TitleChatComponent(LabyMod.getInstance().getPlayerName(), System.currentTimeMillis(), this.getThisDay()));
            }

            LabyMod.getInstance().sendMessage(de.labystudio.utils.Color.cl("a"), packet.getSender(), packet.getMessage());
            ChatHandler.getHandler().getChat(packet.getSender()).addMessage(new MessageChatComponent(packet.getSender().getName(), System.currentTimeMillis(), packet.getMessage()));
            ChatHandler.addNewMessageInfo(packet.getSender().getName());
        }
    }

    public String getThisDay()
    {
        return "DATE";
    }

    public boolean isNextDay(List<MessageChatComponent> messages)
    {
        return messages.size() == 0 ? true : !this.date.format(new Date(((MessageChatComponent)messages.get(0)).getSentTime())).equals(this.date.format(new Date()));
    }

    public void handle(PacketPlayTyping packet)
    {
        if (packet.getPlayer() != null)
        {
            this.getClient().setTyping(packet.getPlayer(), !packet.isTyping());
        }
    }

    public void handle(PacketPlayRequestAddFriendResponse packet)
    {
        if (packet.isRequestSent())
        {
            for (LabyModPlayer labymodplayer : this.getClient().requests)
            {
                if (labymodplayer.getName().equalsIgnoreCase(packet.getSearched()))
                {
                    if (ConfigManager.settings.chatAlertType)
                    {
                        if (ConfigManager.settings.alertsChat)
                        {
                            LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("e") + packet.getSearched() + " has been added to your contacts!");
                        }
                    }
                    else
                    {
                        LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.INFO, packet.getSearched() + " has been added to your contacts!", EnumAlertType.CHAT);
                    }

                    return;
                }
            }

            if (ConfigManager.settings.chatAlertType)
            {
                if (ConfigManager.settings.alertsChat)
                {
                    LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("e") + "A request has been sent to " + packet.getSearched());
                }
            }
            else
            {
                LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.INFO, "A request has been sent to " + packet.getSearched(), EnumAlertType.CHAT);
            }
        }
        else if (ConfigManager.settings.chatAlertType)
        {
            if (ConfigManager.settings.alertsChat)
            {
                LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("4") + "ERROR" + de.labystudio.utils.Color.cl("7") + ": " + packet.getReason());
            }
        }
        else
        {
            LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, packet.getReason(), EnumAlertType.CHAT);
        }
    }

    public void handle(PacketPlayAcceptFriendRequest packet)
    {
    }

    public void handle(PacketPlayRequestRemove packet)
    {
        List<LabyModPlayerRequester> list = this.getClient().requests;
        List<LabyModPlayerRequester> list1 = new ArrayList();

        for (LabyModPlayerRequester labymodplayerrequester : list)
        {
            if (labymodplayerrequester.getName().equalsIgnoreCase(packet.getPlayerName()))
            {
                list1.add(labymodplayerrequester);
            }
        }

        list.removeAll(list1);
        this.getClient().requests = list;
    }

    public void handle(PacketPlayDenyFriendRequest packet)
    {
    }

    public void handle(PacketPlayFriendRemove packet)
    {
        Iterator<LabyModPlayer> iterator = this.getClient().friends.iterator();
        List<LabyModPlayer> list = new ArrayList();

        while (iterator.hasNext())
        {
            LabyModPlayer labymodplayer = (LabyModPlayer)iterator.next();

            if (labymodplayer.getId().equals(packet.getToRemove().getId()))
            {
                list.add(labymodplayer);
            }
        }

        this.getClient().friends.removeAll(list);
    }

    public void handle(PacketLoginOptions packet)
    {
    }

    public void handle(PacketPlayServerStatus packet)
    {
    }

    public void handle(PacketPlayFriendStatus packet)
    {
        packet.getPlayer().updateServer(packet.getPlayerInfo());
        this.getClient().updatePlayer(packet.getPlayer());
    }

    public void handle(PacketPlayFriendPlayingOn packet)
    {
        if (ConfigManager.settings.alertsPlayingOn && !packet.getGameModeName().replace(" ", "").isEmpty())
        {
            if (packet.getGameModeName().contains("."))
            {
                LabyMod.getInstance().sendMessage("", packet.getPlayer(), "Is now playing on " + de.labystudio.utils.Color.cl("e") + packet.getGameModeName());
            }
            else
            {
                LabyMod.getInstance().sendMessage("", packet.getPlayer(), "Is now playing " + de.labystudio.utils.Color.cl("e") + packet.getGameModeName());
            }
        }
    }

    public void handle(PacketPlayChangeOptions packet)
    {
    }

    public void handle(PacketLoginTime packet)
    {
        this.getClient().firstJoined = packet.getDateJoined();
        this.getClient().lastOnline = packet.getLastOnline();
    }

    public void handle(PacketLoginVersion packet)
    {
        if (17 < packet.getVersionID())
        {
            LabyMod.getInstance().chatPacketUpdate = true;
            LabyMod.getInstance().latestVersionName = packet.getVersionName();
            LabyMod.getInstance().lastKickReason = "Please update LabyMod to v" + packet.getVersionName();
            LabyMod.getInstance().autoUpdaterCurrentVersionId = 0;
        }
    }

    public void handle(PacketChatVisibilityChange packet)
    {
        LabyMod.getInstance().chatVisibility = packet.isVisible();
        LabyMod.getInstance().chatChange = true;
    }

    public void handle(PacketEncryptionRequest packet)
    {
        SecretKey secretkey = CryptManager.createNewSharedKey();
        String s = packet.getServerId();
        PublicKey publickey = CryptManager.decodePublicKey(packet.getPublicKey());
        String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);

        if (Minecraft.getMinecraft().getSession().getProfile().getId() == null)
        {
            LabyMod.getInstance().lastKickReason = "Invalid session";
            System.out.println("[LabyMod] No Session, aborting");

            if (ConfigManager.settings.chatAlertType)
            {
                if (ConfigManager.settings.alertsChat)
                {
                    LabyMod.getInstance().displayMessageInChat(chatPrefix + de.labystudio.utils.Color.cl("4") + "Error" + de.labystudio.utils.Color.cl("7") + ": Invalid Session");
                }
            }
            else
            {
                LabyMod.getInstance().achievementGui.displayBroadcast(de.labystudio.utils.Color.cl("c") + "Error", "Invalid Session", EnumAlertType.CHAT);
            }

            this.setIntentionally(true);
        }
        else
        {
            try
            {
                this.getMinecraftSessionServer().joinServer(Minecraft.getMinecraft().getSession().getProfile(), Minecraft.getMinecraft().getSession().getToken(), s1);
            }
            catch (AuthenticationUnavailableException var7)
            {
                LabyMod.getInstance().lastKickReason = "Authentication Unavaileable";
                System.out.println("Authentication Unavaileable");
                return;
            }
            catch (InvalidCredentialsException var8)
            {
                LabyMod.getInstance().lastKickReason = "Invalid session";
                System.out.println("Invalid Session");
                return;
            }
            catch (AuthenticationException var9)
            {
                LabyMod.getInstance().lastKickReason = "Login failed";
                System.out.println("Login failed");
                return;
            }

            this.sendPacket(new PacketEncryptionResponse(secretkey, publickey, packet.getVerifyToken()));
        }
    }

    private MinecraftSessionService getMinecraftSessionServer()
    {
        return (new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString())).createMinecraftSessionService();
    }

    public void handle(PacketEncryptionResponse packet)
    {
    }

    public void handle(PacketMojangStatus packet)
    {
        ServiceStatus servicestatus = new ServiceStatus(packet.getStatus());
        String s = servicestatus.getColor();

        if (ConfigManager.settings.mojangStatus)
        {
            if (ConfigManager.settings.mojangStatusChat)
            {
                if (servicestatus.getColor().equals("yellow"))
                {
                    s = de.labystudio.utils.Color.cl("e") + packet.getMojangService().getName() + de.labystudio.utils.Color.cl("7") + " is running slowly";
                }

                if (servicestatus.getColor().equals("red"))
                {
                    s = de.labystudio.utils.Color.cl("c") + packet.getMojangService().getName() + de.labystudio.utils.Color.cl("7") + " is offline";
                }

                if (servicestatus.getColor().equals("green"))
                {
                    if (LabyMod.getInstance().mojangStatus.containsKey(packet.getMojangService().getName()) && ((ServiceStatus)LabyMod.getInstance().mojangStatus.get(packet.getMojangService().getName())).getCreated() / 1000L != System.currentTimeMillis() / 1000L)
                    {
                        s = de.labystudio.utils.Color.cl("a") + packet.getMojangService().getName() + de.labystudio.utils.Color.cl("7") + " is back online " + de.labystudio.utils.Color.cl("c") + "(" + Utils.parseTimeNormal((System.currentTimeMillis() - ((ServiceStatus)LabyMod.getInstance().mojangStatus.get(packet.getMojangService().getName())).getCreated()) / 1000L) + " downtime)";
                    }
                    else
                    {
                        s = de.labystudio.utils.Color.cl("a") + packet.getMojangService().getName() + de.labystudio.utils.Color.cl("7") + " is back online";
                    }
                }

                LabyMod.getInstance().displayMessageInChat(de.labystudio.utils.Color.cl("8") + "[" + de.labystudio.utils.Color.cl("5") + de.labystudio.utils.Color.cl("l") + "Mojang" + de.labystudio.utils.Color.cl("8") + "] " + de.labystudio.utils.Color.cl("7") + s);
            }
            else
            {
                if (servicestatus.getColor().equals("yellow"))
                {
                    s = de.labystudio.utils.Color.cl("e") + "Is running slowly";
                }

                if (servicestatus.getColor().equals("red"))
                {
                    s = de.labystudio.utils.Color.cl("c") + "Is offline";
                }

                if (servicestatus.getColor().equals("green"))
                {
                    if (LabyMod.getInstance().mojangStatus.containsKey(packet.getMojangService().getName()) && ((ServiceStatus)LabyMod.getInstance().mojangStatus.get(packet.getMojangService().getName())).getCreated() / 1000L != System.currentTimeMillis() / 1000L)
                    {
                        s = de.labystudio.utils.Color.cl("a") + "Is back online " + de.labystudio.utils.Color.cl("c") + "(" + Utils.parseTimeNormal((System.currentTimeMillis() - ((ServiceStatus)LabyMod.getInstance().mojangStatus.get(packet.getMojangService().getName())).getCreated()) / 1000L) + " downtime)";
                    }
                    else
                    {
                        s = de.labystudio.utils.Color.cl("a") + "Is back online";
                    }
                }

                LabyMod.getInstance().achievementGui.displayBroadcast(de.labystudio.utils.Color.cl("5") + packet.getMojangService().getName(), s, EnumAlertType.LABYMOD);
            }
        }

        LabyMod.getInstance().mojangStatus.put(packet.getMojangService().getName(), servicestatus);
    }
}
