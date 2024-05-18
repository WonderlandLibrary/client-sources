package de.zockermaus.ts3;

import java.util.ArrayList;
import java.util.List;

public class TeamSpeakChannel
{
    private static final List<TeamSpeakChannel> channels = new ArrayList();
    private final int channelId;
    private String channel_name;
    private int pid;
    private int channel_order;
    private int total_clients;
    private int channel_codec;
    private int channel_codec_quality;
    private int talk_power;
    private int max_clients;
    private int max_family_clients;
    private boolean flag_are_subscribed;
    private String topic;
    private boolean flagDefault;
    private boolean password;
    private boolean permanent;
    private boolean semiPermanent;
    private int iconID;

    public TeamSpeakChannel(int channel_id)
    {
        this.channelId = channel_id;
        channels.add(this);
    }

    protected void updatePID(int pid)
    {
        this.pid = pid;
    }

    protected void updateChannelOrder(int channel_order)
    {
        this.channel_order = channel_order;
    }

    protected void updateTotalClients(int total_clients)
    {
        this.total_clients = total_clients;
    }

    public String getChannelName()
    {
        return this.channel_name;
    }

    public int getChannelOrder()
    {
        return this.channel_order;
    }

    public int getChannelId()
    {
        return this.channelId;
    }

    public int getPid()
    {
        return this.pid;
    }

    public int getTotalClients()
    {
        return this.total_clients;
    }

    public static void reset()
    {
        channels.clear();
    }

    protected static List<TeamSpeakChannel> getChannels()
    {
        List<TeamSpeakChannel> list = new ArrayList();
        list.addAll(channels);
        return list;
    }

    public int getChannelCodec()
    {
        return this.channel_codec;
    }

    public String getChannelCodecName()
    {
        return (this.channel_codec + "").replace("0", "Speex Narrowband").replace("1", "Speex Wideband").replace("2", "Speex Ultra-Wideband").replace("3", "CELT Mono").replace("4", "Opus Voice").replace("5", "Opus Music");
    }

    public int getChannelCodecQuality()
    {
        return this.channel_codec_quality;
    }

    public int getMaxClients()
    {
        return this.max_clients;
    }

    public int getTalkPower()
    {
        return this.talk_power;
    }

    public void updateChannelName(String name)
    {
        this.channel_name = name;
    }

    public int getMaxFamilyClients()
    {
        return this.max_family_clients;
    }

    public boolean getSubscription()
    {
        return this.flag_are_subscribed;
    }

    public void updateChannelCodec(int channel_codec)
    {
        this.channel_codec = channel_codec;
    }

    public void updateChannelCodecQuality(int channel_codec_quality)
    {
        this.channel_codec_quality = channel_codec_quality;
    }

    public void updateFlagAreSubscribed(boolean flag_are_subscribed)
    {
        this.flag_are_subscribed = flag_are_subscribed;
    }

    public void updateMaxClients(int max_clients)
    {
        this.max_clients = max_clients;
    }

    public void updateTalkPower(int talk_power)
    {
        this.talk_power = talk_power;
    }

    public void updateMaxFamilyClients(int max_family_clients)
    {
        this.max_family_clients = max_family_clients;
    }

    public int getIconID()
    {
        return this.iconID;
    }

    public String getTopic()
    {
        return this.topic;
    }

    public boolean getFlagDefault()
    {
        return this.flagDefault;
    }

    public boolean getIsPassword()
    {
        return this.password;
    }

    public boolean getIsPermanent()
    {
        return this.permanent;
    }

    public boolean getIsSemiPermanent()
    {
        return this.semiPermanent;
    }

    public void updateIsPassword(boolean password)
    {
        this.password = password;
    }

    public void updatePermanent(boolean permanent)
    {
        this.permanent = permanent;
    }

    public void updateSemiPermanent(boolean semiPermanent)
    {
        this.semiPermanent = semiPermanent;
    }

    public void updateTopic(String topic)
    {
        this.topic = topic;
    }

    public void updateIconID(int iconID)
    {
        this.iconID = iconID;
    }

    public void updateFlagDefault(boolean flagDefault)
    {
        this.flagDefault = flagDefault;
    }

    public static boolean contains(int id)
    {
        for (TeamSpeakChannel teamspeakchannel : channels)
        {
            if (teamspeakchannel.getChannelId() == id)
            {
                return true;
            }
        }

        return false;
    }

    public static void deleteChannel(TeamSpeakChannel c)
    {
        channels.remove(c);
    }

    public static int amount()
    {
        return channels.size();
    }
}
