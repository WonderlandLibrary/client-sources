// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils.minecraft;

import me.chrest.event.EventTarget;
import me.chrest.event.events.Render2DEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.chrest.event.EventManager;
import me.chrest.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.OldServerPinger;

public class NetworkUtils
{
    static OldServerPinger pinger;
    private Minecraft mc;
    private Timer timer;
    private static long ping;
    private long lastTime;
    private int prevDebugFPS;
    public long updatedPing;
    
    static {
        NetworkUtils.pinger = new OldServerPinger();
    }
    
    public NetworkUtils() {
        this.mc = Minecraft.getMinecraft();
        this.timer = new Timer();
        EventManager.register(this);
        final PingThread pingThread = new PingThread();
        pingThread.start();
    }
    
    public static long getPing() {
        return NetworkUtils.ping;
    }
    
    public int getPlayerPing(final String name) {
        final EntityPlayer player = this.mc.theWorld.getPlayerEntityByName(name);
        if (player instanceof EntityOtherPlayerMP) {
            return ((EntityOtherPlayerMP)player).field_175157_a.getResponseTime();
        }
        return 0;
    }
    
    @EventTarget
    private void on2DRender(final Render2DEvent event) {
        if (Minecraft.debugFPS != this.prevDebugFPS) {
            this.prevDebugFPS = Minecraft.debugFPS;
            NetworkUtils.ping = this.updatedPing;
        }
    }
}
