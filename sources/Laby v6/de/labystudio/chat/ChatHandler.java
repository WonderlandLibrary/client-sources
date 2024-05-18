package de.labystudio.chat;

import de.labystudio.gommehd.GommeHDSign;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.packets.PacketPlayServerStatus;
import de.labystudio.packets.PacketPlayTyping;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ChatHandler
{
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
        instance = this;
        this.chats = new ArrayList();
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

        File file1 = new File("LabyMod/Chatlog/" + LabyMod.getInstance().getPlayerName() + "/chatlog" + s);

        if (!file1.exists())
        {
            file1.getParentFile().mkdirs();
        }

        return file1;
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

    public SingleChat createSingleChat(LabyModPlayer friend)
    {
        if (this.getOnlyChat(friend) != null)
        {
            return this.getOnlyChat(friend);
        }
        else
        {
            SingleChat singlechat = new SingleChat(this.chats.size(), friend, new ArrayList());
            this.chats.add(singlechat);
            return singlechat;
        }
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
                GommeHDSign.autoJoin = false;
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
