package de.labystudio.utils;

public class ServerData
{
    private String version;
    private String favicon;
    private String latency;
    private String online;
    private String max;
    private String motd;
    private String strippedmotd;
    private String protocol;
    private String server;
    private String address;
    private int ping;

    public String getVersion()
    {
        return this.version;
    }

    public void setVersion(String s)
    {
        this.motd = s;
    }

    public String getFavicon()
    {
        return this.favicon;
    }

    public void setFavicon(String s)
    {
        this.favicon = s;
    }

    public String getLatency()
    {
        return this.latency;
    }

    public void setLatency(String s)
    {
        this.latency = s;
    }

    public String getOnline()
    {
        return this.online;
    }

    public void setOnline(String s)
    {
        this.online = s;
    }

    public String getMax()
    {
        return this.max;
    }

    public void setMax(String s)
    {
        this.max = s;
    }

    public String getMotd()
    {
        return this.motd;
    }

    public void setMotd(String s)
    {
        this.motd = s;
    }

    public String getStrippedMotd()
    {
        return this.strippedmotd;
    }

    public void setStrippedMotd(String s)
    {
        this.strippedmotd = s;
    }

    public String getProtocol()
    {
        return this.protocol;
    }

    public void setProtocol(String s)
    {
        this.protocol = s;
    }

    public String getServer()
    {
        return this.server;
    }

    public void setServer(String s)
    {
        this.server = s;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String s)
    {
        this.address = s;
    }

    public void setPing(int ping)
    {
        this.ping = ping;
    }

    public int getPing()
    {
        return this.ping;
    }
}
