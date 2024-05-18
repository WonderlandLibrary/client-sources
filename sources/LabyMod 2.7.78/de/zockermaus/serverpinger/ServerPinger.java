package de.zockermaus.serverpinger;

import de.labystudio.utils.ServerManager;

public class ServerPinger extends Thread
{
    private ServerData currentData = new ServerData();
    private int failed = 0;

    public ServerPinger(String serverName, int port)
    {
        this.currentData.port = port;
        this.currentData.serverName = serverName;
    }

    public void run()
    {
        if (this.failed == 5)
        {
            this.failed = 0;
        }

        try
        {
            PingUtils.ServerListPing pingutils$serverlistping = new PingUtils.ServerListPing();
            pingutils$serverlistping.setHost1(this.currentData.serverName);
            pingutils$serverlistping.setPort(this.currentData.port);
            pingutils$serverlistping.setTimeout(30000);
            PingUtils.StatusResponse pingutils$statusresponse = pingutils$serverlistping.fetchData();
            this.currentData.maxPlayers = pingutils$statusresponse.getPlayers().getMax();
            this.currentData.players = pingutils$statusresponse.getPlayers().getOnline();
            this.currentData.motd = pingutils$statusresponse.getDescription();
            this.currentData.ms = pingutils$statusresponse.getMs();
            ServerManager.add(this.currentData);
        }
        catch (Exception var3)
        {
            ++this.failed;
        }
    }

    public ServerData getCurrentData()
    {
        return this.currentData;
    }
}
