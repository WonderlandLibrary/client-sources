// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.server.S3APacketTabComplete;
import com.klintos.twelve.mod.events.EventPacketRecieve;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.client.Minecraft;
import com.darkmagician6.eventapi.EventManager;
import com.klintos.twelve.utils.TimerUtil;

public class Plugins extends Cmd
{
    private TimerUtil timer;
    
    public Plugins() {
        super("plugins", "Finds server plugins.", "plugins");
        this.timer = new TimerUtil();
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        EventManager.register((Object)this);
        this.timer.reset();
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue((Packet)new C14PacketTabComplete("/"));
    }
    
    @EventTarget
    public void onReceivePacket(final EventPacketRecieve event) {
        if (event.getPacket() instanceof S3APacketTabComplete) {
            final S3APacketTabComplete packet = (S3APacketTabComplete)event.getPacket();
            final String[] commands = packet.func_149630_c();
            String message = "";
            int size = 0;
            String[] array;
            for (int length = (array = commands).length, i = 0; i < length; ++i) {
                final String command = array[i];
                final String pluginName = command.split(":")[0].substring(1);
                if (!message.contains(pluginName) && command.contains(":") && !pluginName.equalsIgnoreCase("minecraft") && !pluginName.equalsIgnoreCase("bukkit")) {
                    ++size;
                    if (message.isEmpty()) {
                        message = String.valueOf(message) + pluginName;
                    }
                    else {
                        message = String.valueOf(message) + "§c, §f" + pluginName;
                    }
                }
            }
            if (!message.isEmpty()) {
                this.addMessage("Plugins Found §8(§c" + size + "§8)§f: " + message + "§c.");
            }
            else {
                this.addMessage("Plugins: none.");
            }
            event.setCancelled(true);
            EventManager.unregister((Object)this);
        }
        if (this.timer.delay(20000.0)) {
            EventManager.unregister((Object)this);
            this.addMessage("Server took too long to respond! (20s)");
        }
    }
}
