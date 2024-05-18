package de.zockermaus.ts3;

import de.labystudio.utils.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamSpeakBridge
{
    public static TeamSpeakChannel getChannel(String clientName)
    {
        TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(clientName);
        return getChannel(teamspeakuser);
    }

    protected static TeamSpeakChannel getChannel(TeamSpeakUser user)
    {
        return TeamSpeakController.getInstance().getChannel(user.getChannelId());
    }

    public static List<TeamSpeakUser> getUsers()
    {
        return TeamSpeakUser.getUsers();
    }

    public static List<TeamSpeakChannel> getChannels()
    {
        return TeamSpeakChannel.getChannels();
    }

    public static TeamSpeakUser getUser(String clientName)
    {
        return TeamSpeakController.getInstance().getUser(clientName);
    }

    public static void pokeClient(TeamSpeakUser user, String message)
    {
        TeamSpeakController.getInstance().sendMessage("clientpoke msg=" + TeamSpeak.unFix(message) + " clid=" + user.getClientId());
        String s = Color.cl("7") + "You poked " + Color.cl("9") + user.getNickName() + Color.cl("7") + ".";

        if (!message.isEmpty())
        {
            s = Color.cl("7") + "You poked " + Color.cl("9") + user.getNickName() + Color.cl("7") + " with message: " + message;
        }

        TeamSpeak.addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.SERVER);
        TeamSpeak.addChat((TeamSpeakUser)null, (TeamSpeakUser)null, s, EnumTargetMode.CHANNEL);

        if (TeamSpeak.selectedChat >= 0)
        {
            TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(TeamSpeak.selectedChat);

            if (teamspeakuser != null && teamspeakuser.getClientId() == user.getClientId())
            {
                TeamSpeak.addChat(teamspeakuser, (TeamSpeakUser)null, s, EnumTargetMode.USER);
            }
        }
    }

    public static void messagePlayer(TeamSpeakUser user, String message)
    {
        TeamSpeakController.getInstance().sendMessage("sendtextmessage targetmode=1 target=" + user.getClientId() + " msg=" + TeamSpeak.unFix(TeamSpeak.toUrl(message)));
    }

    public static void messageChannel(String message)
    {
        TeamSpeakController.getInstance().sendMessage("sendtextmessage targetmode=2 msg=" + TeamSpeak.unFix(TeamSpeak.toUrl(message)));
    }

    public static void messageServer(String message)
    {
        TeamSpeakController.getInstance().sendMessage("sendtextmessage targetmode=3 msg=" + TeamSpeak.unFix(TeamSpeak.toUrl(message)));
    }

    public static void moveClient(int id, int to)
    {
        TeamSpeakController.getInstance().sendMessage("clientmove cid=" + to + " clid=" + id);
    }

    public static List<TeamSpeakUser> getChannelUsers(int channelId)
    {
        List<TeamSpeakUser> list = new ArrayList();
        list.addAll(TeamSpeakUser.getUsers());
        Collections.sort(list, new Comparator<TeamSpeakUser>()
        {
            public int compare(TeamSpeakUser o1, TeamSpeakUser o2)
            {
                return o1 != null && o2 != null ? (o1.getTalkPower() < o2.getTalkPower() ? 1 : (o1.getTalkPower() > o2.getTalkPower() ? -1 : 0)) : 0;
            }
        });
        List<TeamSpeakUser> list1 = new ArrayList();

        for (TeamSpeakUser teamspeakuser : list)
        {
            if (teamspeakuser != null && channelId == teamspeakuser.getChannelId())
            {
                list1.add(teamspeakuser);
            }
        }

        return list1;
    }

    public static void setNickname(String nickname)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_nickname=" + TeamSpeak.unFix(nickname));
    }

    public static void setAway(boolean away, String message)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_away=" + TeamSpeak.booleanToInteger(away));

        if (message.isEmpty())
        {
            TeamSpeakController.getInstance().sendMessage("clientupdate client_away_message");
        }
        else
        {
            TeamSpeakController.getInstance().sendMessage("clientupdate client_away_message=" + TeamSpeak.unFix(message));
        }
    }

    public static void setInputMuted(boolean muted)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_input_muted=" + TeamSpeak.booleanToInteger(muted));
    }

    public static void setOutputMuted(boolean muted)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_output_muted=" + TeamSpeak.booleanToInteger(muted));
    }

    public static void setInputDeactivated(boolean muted)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_input_deactivated=" + TeamSpeak.booleanToInteger(muted));
    }

    public static void setChannelCommander(boolean commander)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_is_channel_commander=" + TeamSpeak.booleanToInteger(commander));
    }

    public static void setMetaData(String message)
    {
        TeamSpeakController.getInstance().sendMessage("clientupdate client_meta_data=" + TeamSpeak.unFix(message));
    }

    public static void sendTextMessage(int id, String msg)
    {
        if (id == -2)
        {
            messageServer(msg);
        }
        else if (id == -1)
        {
            messageChannel(msg);
        }
        else
        {
            TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(id);

            if (teamspeakuser != null)
            {
                messagePlayer(teamspeakuser, msg);
            }
            else
            {
                TeamSpeak.error("User is offline");
            }
        }
    }
}
