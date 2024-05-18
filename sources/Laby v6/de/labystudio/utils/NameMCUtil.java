package de.labystudio.utils;

import java.util.HashMap;
import java.util.UUID;

public class NameMCUtil
{
    private static HashMap<String, NameHistory> cacheHistory = new HashMap();

    public static NameHistory getNameHistory(String name)
    {
        if (cacheHistory.containsKey(name))
        {
            return (NameHistory)cacheHistory.get(name);
        }
        else
        {
            NameHistory namehistory = requestHistory(name);
            cacheHistory.put(name, namehistory);
            return namehistory;
        }
    }

    public static boolean isInCache(String name)
    {
        return cacheHistory.containsKey(name);
    }

    private static NameHistory requestHistory(String name)
    {
        UUID uuid = UUIDFetcher.getUUID(name);
        return uuid == null ? null : UUIDFetcher.getHistory(uuid);
    }
}
