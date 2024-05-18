// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import me.chrest.event.EventTarget;
import me.chrest.event.EventManager;
import me.chrest.utils.ClientUtils;
import net.minecraft.network.play.server.S3APacketTabComplete;
import me.chrest.event.events.PacketReceiveEvent;
import me.chrest.utils.Timer;
import me.chrest.client.command.Com;
import me.chrest.client.command.Command;

@Com(names = { "pl", "plugin", "plugins", "pluginfinder" })
public class Plugins extends Command
{
    Timer timer;
    
    public Plugins() {
        this.timer = new Timer();
    }
    
    @EventTarget
    public void onReceivePacket(final PacketReceiveEvent event) {
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
                        message = String.valueOf(message) + "\u00c2§8, \u00c2§7" + pluginName;
                    }
                }
            }
            if (!message.isEmpty()) {
                ClientUtils.sendMessage("Plugins (" + size + "): §7" + message + "§8.");
            }
            else {
                ClientUtils.sendMessage("Plugins: None Found!");
            }
            event.setCancelled(true);
            EventManager.unregister(this);
        }
        if (this.timer.delay(20000.0f)) {
            EventManager.unregister(this);
            ClientUtils.sendMessage("Stopped listening for an S3APacketTabComplete! Took too long (20s)!");
        }
    }
    
    @Override
    public void runCommand(final String[] p0) throws Error {
        this.timer.reset();
        ClientUtils.packet(new C14PacketTabComplete("/"));
        ClientUtils.sendMessage("Listening for a S3APacketTabComplete for 20s!");
    }
}
