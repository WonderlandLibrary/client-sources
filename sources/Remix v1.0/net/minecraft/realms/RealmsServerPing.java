package net.minecraft.realms;

public class RealmsServerPing
{
    public volatile String nrOfPlayers;
    public volatile long lastPingSnapshot;
    
    public RealmsServerPing() {
        this.nrOfPlayers = "0";
        this.lastPingSnapshot = 0L;
    }
}
