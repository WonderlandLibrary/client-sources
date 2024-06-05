package com.mojang.realmsclient.dto;

import java.util.*;

public class PingResult
{
    public List<RegionPingResult> pingResults;
    public List<Long> worldIds;
    
    public PingResult() {
        this.pingResults = new ArrayList<RegionPingResult>();
        this.worldIds = new ArrayList<Long>();
    }
}
