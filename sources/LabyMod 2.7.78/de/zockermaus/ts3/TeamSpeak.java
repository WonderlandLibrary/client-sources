package de.zockermaus.ts3;

import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Timings;
import de.labystudio.utils.Color;
import de.labystudio.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeamSpeak
{
    private static final Logger logger = LogManager.getLogger("TeamSpeak");
    public static String chatPrefix = Color.cl("8") + "[" + Color.cl("5") + Color.cl("l") + "TeamSpeak" + Color.cl("8") + "] " + Color.cl("7");
    public static TeamSpeakOverlayWindow overlayWindows;
    public static String inputString = "";
    public static ArrayList<Chat> chats = new ArrayList();
    public static int selectedChat = -1;
    public static int selectedChannel = -1;
    public static int selectedUser = -1;
    public static boolean defaultScreen = false;
    public static int ySplit;
    public static int xSplit;
    public static int scrollChat = 0;
    public static int scrollChannel = 0;
    public static boolean callBack = false;
    public static int callBackClient = 0;
    public static boolean teamSpeakGroupPrefix = false;
    public static ArrayList<Integer> outOfView = new ArrayList();

    public static void enable()
    {
        Timings.start("Enable TeamSpeak");
        setupChat();
        new TeamSpeakController(new TeamSpeakListener());
        overlayWindows = new TeamSpeakOverlayWindow();
        Timings.stop("Enable TeamSpeak");
    }

    public static void setupChat()
    {
        chats.clear();
        chats.add(new Chat(-2, EnumTargetMode.SERVER));
        chats.add(new Chat(-1, EnumTargetMode.CHANNEL));
    }

    public static void addChat(TeamSpeakUser target, TeamSpeakUser sender, String message, EnumTargetMode mode)
    {
        if (message != null)
        {
            message = fix(message);
            message = colors(message);
            message = url(message);
        }

        if (mode == EnumTargetMode.USER)
        {
            TeamSpeakUser teamspeakuser = sender;

            if (sender == null)
            {
                teamspeakuser = target;
            }
            else if (sender.getClientId() == TeamSpeakController.getInstance().me().getClientId())
            {
                teamspeakuser = target;
            }

            if (teamspeakuser == null)
            {
                error("User is offline");
                return;
            }

            Chat chat = null;

            for (Chat chat1 : chats)
            {
                if (chat1.getSlotId() == teamspeakuser.getClientId())
                {
                    chat = chat1;
                    break;
                }
            }

            if (sender == null)
            {
                if (chat != null)
                {
                    chat.addMessage((TeamSpeakUser)null, message);
                }
            }
            else if (chat == null)
            {
                if (message == null)
                {
                    chats.add(new Chat(teamspeakuser, sender, mode));
                }
                else
                {
                    chats.add(new Chat(teamspeakuser, sender, mode, message));
                }
            }
            else if (message == null)
            {
                selectedChat = teamspeakuser.getClientId();
            }
            else
            {
                chat.addMessage(sender, message);
            }
        }

        if (mode == EnumTargetMode.CHANNEL)
        {
            Chat chat2 = null;

            for (Chat chat4 : chats)
            {
                if (chat4.getSlotId() == -1)
                {
                    chat2 = chat4;
                    break;
                }
            }

            if (chat2 != null)
            {
                chat2.addMessage(sender, message);
            }
        }

        if (mode == EnumTargetMode.SERVER)
        {
            Chat chat3 = null;

            for (Chat chat5 : chats)
            {
                if (chat5.getSlotId() == -2)
                {
                    chat3 = chat5;
                    break;
                }
            }

            if (chat3 != null)
            {
                chat3.addMessage(sender, message);
            }
        }
    }

    public static String replaceColor(String message, String name, String code)
    {
        return message.replaceAll("(?i)Color=" + name, Color.cl(code)).replace("[" + Color.cl(code) + "]", Color.cl(code));
    }

    public static String replaceHtml(String message, String tag, String toPrefix, String toSuffix)
    {
        return message.replaceAll("(?i)" + tag, toPrefix).replace("[" + toPrefix + "]", toPrefix).replace("[/" + toPrefix + "]", toSuffix);
    }

    public static String colors(String message)
    {
        message = replaceColor(message, "RED", "c");
        message = replaceColor(message, "BLUE", "9");
        message = replaceColor(message, "GREEN", "2");
        message = replaceColor(message, "YELLOW", "e");
        message = replaceColor(message, "GOLD", "6");
        message = replaceColor(message, "AQUA", "3");
        message = replaceColor(message, "WHITE", "f");
        message = replaceColor(message, "BLACK", "0");
        message = message.replaceAll("(?i)/Color", Color.cl("7")).replace("[" + Color.cl("7") + "]", Color.cl("7"));
        return message;
    }

    public static String url(String message)
    {
        message = replaceHtml(message, "URL", Color.cl("9") + Color.cl("n"), Color.cl("7"));
        return message;
    }

    public static String toUrl(String message)
    {
        ArrayList<String> arraylist = Utils.extractDomains(message);

        if (!arraylist.isEmpty())
        {
            for (String s : arraylist)
            {
                message = message.replace(s, "[URL]" + s + "[/URL]");
            }
        }

        return message;
    }

    public static String fix(String message)
    {
        message = message.replace("\\/", "/");
        message = message.replace("\\p", "|");
        message = message.replace("\\s", " ");
        message = message.replace("\\\\", "\\");
        message = message.replace("\\n", " ");
        return message;
    }

    public static String unFix(String message)
    {
        message = message.replace("\\", "\\\\");
        message = message.replace("/", "\\/");
        message = message.replace("|", "\\p");
        message = message.replace(" ", "\\s");
        return message;
    }

    public static String toStarSpacer(String channelName, int xSplit)
    {
        String s = channelName.split("]", 2)[1];
        String s1 = "";
        int i = xSplit / 10;

        for (int j = 0; j <= i; ++j)
        {
            s1 = s1 + s;
        }

        if (s1.length() > i)
        {
            s1 = s1.substring(0, i);
        }

        return s1;
    }

    public static String toCenterSpacer(String channelName)
    {
        return channelName.split("]", 2)[1];
    }

    public static boolean isSpacer(String channelName)
    {
        return channelName.toLowerCase().startsWith("[spacer") && channelName.contains("]");
    }

    public static boolean isStarSpacer(String channelName)
    {
        return channelName.toLowerCase().startsWith("[*spacer") && channelName.contains("]");
    }

    public static boolean isCenterSpacer(String channelName)
    {
        return channelName.toLowerCase().startsWith("[cspacer") && channelName.contains("]");
    }

    public static String country(String country)
    {
        return country == null ? "Unknown" : (country.equalsIgnoreCase("DE") ? "Germany" : (country.equalsIgnoreCase("AT") ? "Austria" : (country.equalsIgnoreCase("TR") ? "Turkey" : country)));
    }

    public static String status(boolean status, String on, String off)
    {
        return status ? on : off;
    }

    public static TeamSpeakChannel getFromOrder(int channelOrder)
    {
        for (TeamSpeakChannel teamspeakchannel : TeamSpeakBridge.getChannels())
        {
            if (teamspeakchannel.getChannelOrder() == channelOrder)
            {
                return teamspeakchannel;
            }
        }

        return null;
    }

    public static boolean isChannelNotInView(int channelId)
    {
        Iterator iterator = outOfView.iterator();

        while (iterator.hasNext())
        {
            int i = ((Integer)iterator.next()).intValue();

            if (i == channelId)
            {
                return true;
            }
        }

        return false;
    }

    public static void updateScroll(int channelId, boolean extend)
    {
        if (isChannelNotInView(channelId))
        {
            if (extend)
            {
                scrollChannel -= 10;
            }
            else
            {
                scrollChannel += 10;
            }
        }
    }

    public static void setDefaultScreen()
    {
        if (!defaultScreen)
        {
            defaultScreen = true;
            xSplit = LabyMod.getInstance().draw.getWidth() / 3 * 2;
            ySplit = LabyMod.getInstance().draw.getHeight() / 4 * 3;
        }
    }

    public static int booleanToInteger(boolean input)
    {
        return input ? 1 : 0;
    }

    public static void print(String msg)
    {
        logger.info(msg);
    }

    public static void error(String errorMessage)
    {
        String s = Color.cl("c") + errorMessage;

        if (selectedChat == -1)
        {
            addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.CHANNEL);
        }
        else if (selectedChat == -2)
        {
            addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);
        }
        else
        {
            TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(selectedChat);

            if (teamspeakuser != null)
            {
                addChat(teamspeakuser, (TeamSpeakUser)null, s, EnumTargetMode.USER);
            }
            else
            {
                addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);
            }
        }
    }

    public static void info(String message)
    {
        String s = Color.cl("c") + message;

        if (selectedChat == -1)
        {
            addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.CHANNEL);
        }
        else if (selectedChat == -2)
        {
            addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);
        }
        else
        {
            TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(selectedChat);

            if (teamspeakuser != null)
            {
                addChat(teamspeakuser, (TeamSpeakUser)null, s, EnumTargetMode.USER);
            }
            else
            {
                addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);
            }
        }
    }

    public static void infoAll(String message)
    {
        String s = Color.cl("c") + message;
        addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.CHANNEL);
        addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);

        if (selectedChat >= 0)
        {
            TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(selectedChat);

            if (teamspeakuser != null)
            {
                addChat(teamspeakuser, (TeamSpeakUser)null, s, EnumTargetMode.USER);
            }
            else
            {
                addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);
            }
        }
    }

    public static String getTalkColor(TeamSpeakUser user)
    {
        String s = Color.c(6);

        if (user == null)
        {
            return s;
        }
        else
        {
            if (user.isChannelCommander())
            {
                s = Color.cl("6");
            }

            if (user.isTalking())
            {
                if (user.isChannelCommander())
                {
                    s = Color.cl("e");
                }
                else
                {
                    s = Color.c(7);
                }
            }

            if (!user.hasClientInputHardware())
            {
                s = Color.c(8);
            }

            if (user.hasClientInputMuted())
            {
                s = Color.c(8);
            }

            if (!user.hasClientOutputHardware())
            {
                s = Color.c(9);
            }

            if (user.hasClientOutputMuted())
            {
                s = Color.c(9);
            }

            if (user.isAway())
            {
                s = Color.cl("7");
            }

            return s;
        }
    }

    public static String getAway(TeamSpeakUser user)
    {
        String s = "";

        if (user == null)
        {
            return s;
        }
        else
        {
            if (user.isAway() && !user.getAwayMessage().isEmpty())
            {
                s = Color.cl("7") + " [" + user.getAwayMessage() + "]";
            }

            return s;
        }
    }
}
