/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils.minecraft;

import java.net.UnknownHostException;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.minecraft.NetworkUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

public class PingThread
extends Thread {
    @Override
    public void run() {
        do {
            if (ClientUtils.mc().getCurrentServerData() != null) {
                try {
                    if (!(ClientUtils.mc().currentScreen instanceof GuiMultiplayer)) {
                        NetworkUtils.pinger.ping(ClientUtils.mc().getCurrentServerData());
                    }
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            try {
                Thread.sleep(1000L);
                continue;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            break;
        } while (true);
    }
}

