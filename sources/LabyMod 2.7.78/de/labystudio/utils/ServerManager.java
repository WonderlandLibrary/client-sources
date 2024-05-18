package de.labystudio.utils;

import java.util.ArrayList;

public class ServerManager
{
    public static ArrayList<de.zockermaus.serverpinger.ServerData> pendingServers = new ArrayList();

    public static ArrayList<de.zockermaus.serverpinger.ServerData> getPendingServers()
    {
        return pendingServers;
    }

    public static void add(de.zockermaus.serverpinger.ServerData data)
    {
        de.zockermaus.serverpinger.ServerData serverdata = null;

        for (de.zockermaus.serverpinger.ServerData serverdata1 : getPendingServers())
        {
            if (serverdata1 != null && data != null && serverdata1.serverName != null && data.serverName != null && serverdata1.serverName == data.serverName)
            {
                serverdata = serverdata1;
            }
        }

        if (serverdata != null)
        {
            getPendingServers().remove(serverdata);
        }

        getPendingServers().add(data);
    }

    public static boolean contains(String ip)
    {
        for (de.zockermaus.serverpinger.ServerData serverdata : getPendingServers())
        {
            if (serverdata != null && serverdata.serverName != null && serverdata.serverName.equals(ip))
            {
                return true;
            }
        }

        return false;
    }

    public static de.zockermaus.serverpinger.ServerData get(String ip)
    {
        for (de.zockermaus.serverpinger.ServerData serverdata : getPendingServers())
        {
            if (serverdata != null && serverdata.serverName != null && serverdata.serverName.equals(ip))
            {
                return serverdata;
            }
        }

        return null;
    }

    public static void remove(String ip)
    {
        de.zockermaus.serverpinger.ServerData serverdata = null;

        for (de.zockermaus.serverpinger.ServerData serverdata1 : getPendingServers())
        {
            if (serverdata1 != null && serverdata1.serverName == ip)
            {
                serverdata = serverdata1;
            }
        }

        getPendingServers().remove(serverdata);
    }
}
