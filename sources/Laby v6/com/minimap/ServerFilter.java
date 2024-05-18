package com.minimap;

import net.minecraft.client.*;
import java.util.*;

public class ServerFilter
{
    private static Map<String, FilterType> serverFilter;
    
    public static FilterType getCurrentFilterType() {
        if (Minecraft.getMinecraft().getCurrentServerData() == null || Minecraft.getMinecraft().getCurrentServerData().serverIP == null) {
            return FilterType.ALLOWED;
        }
        final String ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        return getFilterTypeForServer(ip);
    }
    
    public static FilterType getFilterTypeForServer(final String ip) {
        for (final Map.Entry<String, FilterType> filters : ServerFilter.serverFilter.entrySet()) {
            if (!ip.toLowerCase().contains(filters.getKey())) {
                continue;
            }
            return filters.getValue();
        }
        return FilterType.ALLOWED;
    }
    
    static {
        (ServerFilter.serverFilter = new HashMap<String, FilterType>()).put("playminity.com", FilterType.NO_RADAR);
        ServerFilter.serverFilter.put("gommehd.net", FilterType.NO_PLAYER_RADAR);
        ServerFilter.serverFilter.put("timolia.de", FilterType.DISALLOWED);
        ServerFilter.serverFilter.put("mineplex.com", FilterType.DISALLOWED);
    }
    
    public enum FilterType
    {
        ALLOWED, 
        NO_RADAR, 
        NO_PLAYER_RADAR, 
        DISALLOWED;
    }
}
