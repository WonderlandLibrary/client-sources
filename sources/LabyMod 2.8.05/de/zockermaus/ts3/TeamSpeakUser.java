package de.zockermaus.ts3;

import java.util.ArrayList;
import java.util.List;

public class TeamSpeakUser
{
    private static List<TeamSpeakUser> users = new ArrayList();
    private String nickName;
    private int channelId;
    private final int clientId;
    private boolean talkStatus;
    private int databaseId;
    private String uid;
    private boolean client_input_muted;
    private boolean client_output_muted;
    private boolean client_input_hardware;
    private boolean client_output_hardware;
    private boolean typing;
    private boolean away;
    private int talkPower;
    private String awayMessage;
    private boolean talker;
    private boolean prioritySpeaker;
    private boolean recording;
    private boolean channelCommander;
    private boolean muted;
    private ArrayList<Integer> serverGroups;
    private int channelGroupId;
    private int iconId;
    private String country;

    public TeamSpeakUser(int clientId)
    {
        this.clientId = clientId;
        users.add(this);
    }

    public int getChannelId()
    {
        return this.channelId;
    }

    protected void updateChannelId(int channelId)
    {
        this.channelId = channelId;
    }

    public int getClientId()
    {
        return this.clientId;
    }

    public String getNickName()
    {
        return this.nickName.replace("\u00a7", "&");
    }

    protected void updateNickname(String nickname)
    {
        this.nickName = nickname;
    }

    public boolean isTalking()
    {
        return this.talkStatus;
    }

    public void updateTalkPower(int talkPower)
    {
        this.talkPower = talkPower;
    }

    public void updateTalkStatus(boolean talkStatus)
    {
        this.talkStatus = talkStatus;
    }

    protected static List<TeamSpeakUser> getUsers()
    {
        return users;
    }

    public int getDatabaseId()
    {
        return this.databaseId;
    }

    public String getUid()
    {
        return this.uid;
    }

    public boolean isTyping()
    {
        return this.typing;
    }

    public boolean hasClientInputHardware()
    {
        return this.client_input_hardware;
    }

    public boolean hasClientOutputHardware()
    {
        return this.client_output_hardware;
    }

    public boolean hasClientInputMuted()
    {
        return this.client_input_muted;
    }

    public boolean hasClientOutputMuted()
    {
        return this.client_output_muted;
    }

    public void updateClientInput(boolean muted)
    {
        this.client_input_muted = muted;
    }

    public void updateClientOutput(boolean muted)
    {
        this.client_output_muted = muted;
    }

    public void updateTyping(boolean typing)
    {
        this.typing = typing;
    }

    public void updateAway(boolean away, String awayMessage)
    {
        this.away = away;
        this.awayMessage = awayMessage;
    }

    public void updateDatabaseId(int databaseId)
    {
        this.databaseId = databaseId;
    }

    public void updateAway(boolean away)
    {
        this.away = away;
        this.awayMessage = "";
    }

    public String getAwayMessage()
    {
        return this.awayMessage;
    }

    public boolean isAway()
    {
        return this.away;
    }

    public void updateClientInputHardware(boolean muted)
    {
        this.client_input_hardware = muted;
    }

    public void updateClientOutputHardware(boolean muted)
    {
        this.client_output_hardware = muted;
    }

    public static void reset()
    {
        users.clear();
    }

    public int getTalkPower()
    {
        return this.talkPower;
    }

    public int getChannelGroupId()
    {
        return this.channelGroupId;
    }

    public String getCountry()
    {
        return this.country;
    }

    public ArrayList<Integer> getServerGroups()
    {
        return this.serverGroups;
    }

    public int getIconId()
    {
        return this.iconId;
    }

    public void updateTalker(boolean talker)
    {
        this.talker = talker;
    }

    public void updatePrioritySpeaker(boolean prioritySpeaker)
    {
        this.prioritySpeaker = prioritySpeaker;
    }

    public void updateRecording(boolean recording)
    {
        this.recording = recording;
    }

    public void updateChannelCommander(boolean channelCommander)
    {
        this.channelCommander = channelCommander;
    }

    public void updateMuted(boolean muted)
    {
        this.muted = muted;
    }

    public void updateServerGroups(ArrayList<Integer> serverGroups)
    {
        this.serverGroups = serverGroups;
    }

    public void updateChannelGroupId(int channelGroupId)
    {
        this.channelGroupId = channelGroupId;
    }

    public void updateIconId(int iconId)
    {
        this.iconId = iconId;
    }

    public void updateCountry(String country)
    {
        this.country = country;
    }

    public void updateUid(String uid)
    {
        this.uid = uid;
    }

    public boolean isChannelCommander()
    {
        return this.channelCommander;
    }

    public static boolean contains(int id)
    {
        for (TeamSpeakUser teamspeakuser : users)
        {
            if (teamspeakuser.getClientId() == id)
            {
                return true;
            }
        }

        return false;
    }

    public static void unregisterUser(TeamSpeakUser user)
    {
        users.remove(user);
    }

    public static int amount()
    {
        return users.size();
    }
}
