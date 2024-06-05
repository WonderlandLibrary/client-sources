package net.minecraft.client.network;

import com.google.common.collect.*;
import java.net.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;

public static class LanServerList
{
    boolean wasUpdated;
    private List listOfLanServers;
    
    public LanServerList() {
        this.listOfLanServers = Lists.newArrayList();
    }
    
    public synchronized boolean getWasUpdated() {
        return this.wasUpdated;
    }
    
    public synchronized void setWasNotUpdated() {
        this.wasUpdated = false;
    }
    
    public synchronized List getLanServers() {
        return Collections.unmodifiableList((List<?>)this.listOfLanServers);
    }
    
    public synchronized void func_77551_a(final String p_77551_1_, final InetAddress p_77551_2_) {
        final String var3 = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
        String var4 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);
        if (var4 != null) {
            var4 = p_77551_2_.getHostAddress() + ":" + var4;
            boolean var5 = false;
            for (final LanServer var7 : this.listOfLanServers) {
                if (var7.getServerIpPort().equals(var4)) {
                    var7.updateLastSeen();
                    var5 = true;
                    break;
                }
            }
            if (!var5) {
                this.listOfLanServers.add(new LanServer(var3, var4));
                this.wasUpdated = true;
            }
        }
    }
}
