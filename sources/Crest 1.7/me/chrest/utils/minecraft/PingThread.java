// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils.minecraft;

import java.net.UnknownHostException;
import net.minecraft.client.gui.GuiMultiplayer;
import me.chrest.utils.ClientUtils;

public class PingThread extends Thread
{
    @Override
    public void run() {
        while (true) {
            if (ClientUtils.mc().getCurrentServerData() != null) {
                try {
                    if (!(ClientUtils.mc().currentScreen instanceof GuiMultiplayer)) {
                        NetworkUtils.pinger.ping(ClientUtils.mc().getCurrentServerData());
                    }
                }
                catch (UnknownHostException ex) {}
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
