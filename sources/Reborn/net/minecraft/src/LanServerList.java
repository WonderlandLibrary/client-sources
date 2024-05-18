package net.minecraft.src;

import java.net.*;
import java.util.*;

public class LanServerList
{
    private ArrayList listOfLanServers;
    boolean wasUpdated;
    
    public LanServerList() {
        this.listOfLanServers = new ArrayList();
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
    
    public synchronized void func_77551_a(final String par1Str, final InetAddress par2InetAddress) {
        final String var3 = ThreadLanServerPing.getMotdFromPingResponse(par1Str);
        String var4 = ThreadLanServerPing.getAdFromPingResponse(par1Str);
        if (var4 != null) {
            final int var5 = var4.indexOf(58);
            if (var5 > 0) {
                var4 = String.valueOf(par2InetAddress.getHostAddress()) + var4.substring(var5);
            }
            boolean var6 = false;
            for (final LanServer var8 : this.listOfLanServers) {
                if (var8.getServerIpPort().equals(var4)) {
                    var8.updateLastSeen();
                    var6 = true;
                    break;
                }
            }
            if (!var6) {
                this.listOfLanServers.add(new LanServer(var3, var4));
                this.wasUpdated = true;
            }
        }
    }
}
