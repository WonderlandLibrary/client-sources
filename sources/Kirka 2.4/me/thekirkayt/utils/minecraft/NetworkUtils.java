/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils.minecraft;

import me.thekirkayt.event.EventManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.Timer;
import me.thekirkayt.utils.minecraft.PingThread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.entity.player.EntityPlayer;

public class NetworkUtils {
    static OldServerPinger pinger = new OldServerPinger();
    private Minecraft mc = Minecraft.getMinecraft();
    private Timer timer = new Timer();
    private static long ping;
    private long lastTime;
    private int prevDebugFPS;
    public long updatedPing;

    public NetworkUtils() {
        EventManager.register(this);
        PingThread pingThread = new PingThread();
        pingThread.start();
    }

    public static long getPing() {
        return ping;
    }

    public static int getPlayerPing(String name) {
        EntityPlayer player = ClientUtils.mc().theWorld.getPlayerEntityByName(name);
        if (player instanceof EntityOtherPlayerMP) {
            return ((EntityOtherPlayerMP)player).field_175157_a.getResponseTime();
        }
        return 0;
    }

    @EventTarget
    private void on2DRender(Render2DEvent event) {
        if (Minecraft.debugFPS != this.prevDebugFPS) {
            this.prevDebugFPS = Minecraft.debugFPS;
            ping = this.updatedPing;
        }
    }
}

