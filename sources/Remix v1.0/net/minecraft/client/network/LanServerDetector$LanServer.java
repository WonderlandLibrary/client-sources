package net.minecraft.client.network;

import net.minecraft.client.*;

public static class LanServer
{
    private String lanServerMotd;
    private String lanServerIpPort;
    private long timeLastSeen;
    
    public LanServer(final String p_i1319_1_, final String p_i1319_2_) {
        this.lanServerMotd = p_i1319_1_;
        this.lanServerIpPort = p_i1319_2_;
        this.timeLastSeen = Minecraft.getSystemTime();
    }
    
    public String getServerMotd() {
        return this.lanServerMotd;
    }
    
    public String getServerIpPort() {
        return this.lanServerIpPort;
    }
    
    public void updateLastSeen() {
        this.timeLastSeen = Minecraft.getSystemTime();
    }
}
