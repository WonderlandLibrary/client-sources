package de.labystudio.chat;

public class ServerInfo
{
    private String serverIp;
    private int serverPort;
    private String specifiedServerName;

    public ServerInfo(String serverIp, int serverPort, String specifiedServerName)
    {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.specifiedServerName = specifiedServerName;
    }

    public ServerInfo(String serverIp, int serverPort)
    {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.specifiedServerName = null;
    }

    public ServerInfo()
    {
        this.serverIp = "";
    }

    public String getServerIp()
    {
        return this.serverIp;
    }

    public int getServerPort()
    {
        return this.serverPort;
    }

    public String getSpecifiedServerName()
    {
        return this.specifiedServerName;
    }
}
