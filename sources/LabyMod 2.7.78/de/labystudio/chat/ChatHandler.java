package de.labystudio.chat;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.labymod.Timings;
import de.labystudio.packets.PacketPlayServerStatus;
import de.labystudio.packets.PacketPlayTyping;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.h2.engine.Database;

public class ChatHandler
{
    private Database database;
    private Connection connection;
    private List<SingleChat> chats;
    private static ChatHandler instance;
    public static int playerStatus = 0;
    public static boolean isTyping = false;
    public static ArrayList<LabyModPlayer> typingPartner = new ArrayList();

    public static ChatHandler getHandler()
    {
        return instance;
    }

    public static boolean getServerStatus()
    {
        return false;
    }

    public ChatHandler()
    {
        Timings.start("ChatHandler");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    ChatHandler.this.connection.close();
                }
                catch (SQLException sqlexception)
                {
                    sqlexception.printStackTrace();
                }
            }
        }));
        instance = this;
        this.chats = new ArrayList();
        Timings.stop("ChatHandler");
    }

    public SingleChat getChat(String player)
    {
        for (SingleChat singlechat : this.chats)
        {
            if (singlechat.getFriend().getName().equalsIgnoreCase(player))
            {
                return singlechat;
            }
        }

        return null;
    }

    public SingleChat getChat(LabyModPlayer player)
    {
        for (SingleChat singlechat : this.chats)
        {
            if (singlechat.getFriend().getId().equals(player.getId()))
            {
                return singlechat;
            }

            if (singlechat.getFriend().getName().equalsIgnoreCase(player.getName()))
            {
                return singlechat;
            }
        }

        return this.createSingleChat(player);
    }

    public SingleChat getOnlyChat(LabyModPlayer player)
    {
        for (SingleChat singlechat : this.chats)
        {
            if (singlechat.getFriend().getId().equals(player.getId()))
            {
                return singlechat;
            }

            if (singlechat.getFriend().getName().equalsIgnoreCase(player.getName()))
            {
                return singlechat;
            }
        }

        return null;
    }

    public static LabyModPlayer getInfo(String name)
    {
        if (name.equalsIgnoreCase(LabyMod.getInstance().getPlayerName()))
        {
            String s = LabyMod.getInstance().getPlayerName();
            UUID uuid = LabyMod.getInstance().getPlayerUUID();
            String s1 = ConfigManager.settings.motd;
            LabyMod.getInstance().getClient();
            return new LabyModPlayer(s, uuid, s1, Client.getOnlineStatus());
        }
        else
        {
            List<LabyModPlayer> list = new ArrayList();
            list.addAll(LabyMod.getInstance().client.friends);

            for (LabyModPlayer labymodplayer : list)
            {
                if (labymodplayer.getName().equalsIgnoreCase(name))
                {
                    return labymodplayer;
                }
            }

            return null;
        }
    }

    public static List<LabyModPlayer> getMyFriends()
    {
        List<LabyModPlayer> list = new ArrayList();
        list.addAll(LabyMod.getInstance().client.requests);
        List<LabyModPlayer> list1 = new ArrayList();
        list1.addAll(LabyMod.getInstance().client.friends);

        if (ConfigManager.settings.showSettingsFriend == 3)
        {
            list.addAll(list1);
            Collections.sort(list, new Comparator<LabyModPlayer>()
            {
                public int compare(LabyModPlayer a, LabyModPlayer b)
                {
                    return (int)(a.getLastMessage() - b.getLastMessage());
                }
            });
        }
        else
        {
            for (LabyModPlayer labymodplayer : list1)
            {
                if (labymodplayer.isOnline())
                {
                    list.add(labymodplayer);
                }
            }

            for (LabyModPlayer labymodplayer1 : list1)
            {
                if (!labymodplayer1.isOnline())
                {
                    list.add(labymodplayer1);
                }
            }
        }

        return list;
    }

    public static File getLogFile(int i)
    {
        String s = "";

        if (i > 0)
        {
            s = "_" + i;
        }

        File file1 = new File(Source.file_Chatlog + "/" + LabyMod.getInstance().getPlayerName() + "/chatlog" + s);

        if (!file1.exists())
        {
            file1.getParentFile().mkdirs();
        }

        return file1;
    }

    public void shutdown()
    {
        if (this.connection != null)
        {
            try
            {
                this.connection.close();
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
        }
    }

    public Connection getConnection()
    {
        try
        {
            if (this.connection == null || this.connection.isClosed())
            {
                this.initConnection();
            }
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }

        return this.connection;
    }

    public void initConnection()
    {
        for (int i = 0; i <= 10; ++i)
        {
            try
            {
                Class.forName("org.h2.Driver").newInstance();
                this.connection = DriverManager.getConnection("jdbc:h2:" + getLogFile(i).getAbsolutePath());
                break;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public void initDatabase()
    {
        Timings.start("Chat initDatabase");

        try
        {
            this.initConnection();

            if (this.connection == null)
            {
                File file1 = new File(Source.file_Chatlog);

                if (file1.exists())
                {
                    file1.delete();
                }

                this.initConnection();
            }

            if (this.connection == null || this.connection.isClosed())
            {
                return;
            }

            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS friends (id INT AUTO_INCREMENT PRIMARY KEY, friend_id VARCHAR(60), showAlerts BOOLEAN)").executeUpdate();
            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS single_chats (id INT AUTO_INCREMENT PRIMARY KEY, friend_name VARCHAR(16), friend_uuid VARCHAR(60))").executeUpdate();
            this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS single_chat_messages (id INT AUTO_INCREMENT PRIMARY KEY, single_chats_id INT, sender VARCHAR(20), sender_message VARCHAR(200), sent_time LONG)").executeUpdate();
            this.initChatlogs();
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }

        Timings.stop("Chat initDatabase");
    }

    public void updateChats(LabyModPlayer player)
    {
        for (int i = 0; i < this.chats.size(); ++i)
        {
            SingleChat singlechat = (SingleChat)this.chats.get(i);

            if (singlechat.getFriend().getName().equalsIgnoreCase(player.getName()))
            {
                singlechat.updateFriend(player);
            }
        }
    }

    public void initChatlogs()
    {
        int i = 0;

        try
        {
            for (ResultSet resultset = this.connection.prepareStatement("SELECT * FROM single_chats").executeQuery(); resultset.next(); ++i)
            {
                SingleChat singlechat = new SingleChat(resultset.getInt("id"), new LabyModPlayer(resultset.getString("friend_name"), UUID.fromString(resultset.getString("friend_uuid")), "* Offline *", LabyModPlayer.OnlineStatus.OFFLINE), this.loadChatlog(resultset.getInt("id")));
                this.chats.add(singlechat);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        LogManager.getLogger().info("Loaded {} Chats!", new Object[] {Integer.valueOf(i)});
    }

    public SingleChat createSingleChat(LabyModPlayer friend)
    {
        if (this.getOnlyChat(friend) != null)
        {
            return this.getOnlyChat(friend);
        }
        else
        {
            try
            {
                if (this.connection == null)
                {
                    LogManager.getLogger().error("FileSQL Connection is NULL");
                }

                this.connection.prepareStatement("INSERT INTO single_chats (friend_name, friend_uuid) VALUES (\'" + friend.getName() + "\', \'" + friend.getId().toString() + "\')").executeUpdate();
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }

            int i = this.chats.size();

            try
            {
                ResultSet resultset = this.connection.prepareStatement("SELECT id FROM single_chats WHERE friend_uuid=\'" + friend.getId().toString() + "\'").executeQuery();

                if (resultset.next())
                {
                    i = resultset.getInt("id");
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

            SingleChat singlechat = new SingleChat(i, friend, new ArrayList());
            this.chats.add(singlechat);
            return singlechat;
        }
    }

    public List<MessageChatComponent> loadChatlog(int single_chat_id) throws SQLException
    {
        List<MessageChatComponent> list = new ArrayList();
        ResultSet resultset = this.connection.prepareStatement("SELECT * FROM single_chat_messages WHERE single_chats_id=" + single_chat_id + " ORDER BY sent_time DESC LIMIT 100").executeQuery();

        while (resultset.next())
        {
            if (resultset.getString("sender_message").startsWith("<title>") && resultset.getString("sender_message").endsWith("</title>"))
            {
                TitleChatComponent titlechatcomponent = new TitleChatComponent(resultset.getString("sender"), resultset.getLong("sent_time"), resultset.getString("sender_message").replace("<title>", "").replace("</title>", ""));
                list.add(titlechatcomponent);
            }
            else
            {
                MessageChatComponent messagechatcomponent = new MessageChatComponent(resultset.getString("sender"), resultset.getLong("sent_time"), resultset.getString("sender_message"));
                list.add(messagechatcomponent);
            }
        }

        return list;
    }

    static void addNewMessageInfo(String sender)
    {
        int i = 0;

        for (LabyModPlayer labymodplayer : LabyMod.getInstance().client.getFriends())
        {
            if (labymodplayer.getName().equals(sender))
            {
                ++labymodplayer.messages;
                labymodplayer.setLastMessage(System.currentTimeMillis());
            }
        }
    }

    public static void setAFK(boolean isAFK)
    {
        if (LabyMod.getInstance().getClient() != null)
        {
            if (isAFK)
            {
                LabyMod.getInstance().getClient();

                if (Client.getOnlineStatus() == LabyModPlayer.OnlineStatus.ONLINE)
                {
                    LabyMod.getInstance().getClient().setOnlineStatus(LabyModPlayer.OnlineStatus.AWAY);
                    playerStatus = 1;
                    return;
                }
            }

            LabyMod.getInstance().getClient();

            if (Client.getOnlineStatus() == LabyModPlayer.OnlineStatus.AWAY)
            {
                LabyMod.getInstance().getClient().setOnlineStatus(LabyModPlayer.OnlineStatus.ONLINE);
                playerStatus = 0;
            }
        }
    }

    public static void setStatus(int i)
    {
        if (playerStatus != i)
        {
            playerStatus = i;
            LabyMod.getInstance().getClient().setOnlineStatus(LabyModPlayer.OnlineStatus.fromPacketId(i));
        }
    }

    public static void updateGameMode(String lobby)
    {
        if (lobby.isEmpty())
        {
            lobby = null;
        }

        if (!LabyMod.getInstance().gameMode.equals(lobby))
        {
            if (lobby == null && !LabyMod.getInstance().gameMode.isEmpty())
            {
                LabyMod.getInstance().gameMode = "";
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayServerStatus("", 0, (String)null));
            }
            else
            {
                LabyMod.getInstance().gameMode = lobby;
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayServerStatus(LabyMod.getInstance().ip, LabyMod.getInstance().port, LabyMod.getInstance().gameMode));
                LabyMod.getInstance().gommeHDAutoJoin = false;
            }
        }

        if (LabyMod.getInstance().gameMode == null)
        {
            LabyMod.getInstance().gameMode = "";
        }
    }

    public void newAccount()
    {
        this.chats.clear();
        this.shutdown();
        this.initConnection();
        this.initDatabase();
    }

    public static void resetTyping()
    {
        if (isTyping)
        {
            isTyping = false;

            for (LabyModPlayer labymodplayer : typingPartner)
            {
                LabyMod.getInstance().getClient().getClientConnection().sendPacket(new PacketPlayTyping(LabyMod.getInstance().client.build(), labymodplayer, true));
            }
        }
    }

    public static void updateIsWriting(LabyModPlayer selectedPlayer, String textField)
    {
        if (!isTyping && !textField.replace(" ", "").isEmpty() && selectedPlayer != null)
        {
            isTyping = true;
            typingPartner.add(selectedPlayer);
            LabyMod.getInstance().getClient().getClientConnection().sendPacket(new PacketPlayTyping(LabyMod.getInstance().client.build(), selectedPlayer, false));
        }

        if (isTyping && textField.replace(" ", "").isEmpty() || selectedPlayer == null || LabyMod.getInstance().mc.currentScreen == null)
        {
            isTyping = false;
            resetTyping();
        }
    }
}
