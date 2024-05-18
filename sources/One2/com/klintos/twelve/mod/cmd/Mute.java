// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.server.S02PacketChat;
import com.klintos.twelve.mod.events.EventPacketRecieve;
import com.darkmagician6.eventapi.EventManager;

public class Mute extends Cmd
{
    private String mute;
    
    public Mute() {
        super("mute", "Mute a single player or all players.", "mute <Username/All/Unmute/Who>");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            if (args[1].equalsIgnoreCase("unmute")) {
                if (this.mute.equalsIgnoreCase("")) {
                    this.addMessage("No messages are being muted.");
                }
                else {
                    this.addMessage(String.valueOf(this.mute.equalsIgnoreCase("all") ? "All §cplayers§f are" : new StringBuilder("§c").append(this.mute).append("§f is").toString()) + "§f no longer muted.");
                }
                this.mute = "";
                EventManager.unregister((Object)this);
            }
            else if (args[1].equalsIgnoreCase("who")) {
                this.addMessage(String.valueOf(this.mute.equalsIgnoreCase("all") ? "§cAll §fplayers are " : (this.mute.equalsIgnoreCase("") ? "No player is " : new StringBuilder("§c").append(this.mute).append("§f is ").toString())) + "currently muted.");
            }
            else {
                this.mute = args[1];
                this.addMessage("Now muted §c" + (this.mute.equalsIgnoreCase("all") ? "all §fplayers" : this.mute) + "§f.");
                EventManager.register((Object)this);
            }
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
    
    @EventTarget
    public void onPacketRecieve(final EventPacketRecieve event) {
        if (event.getPacket() instanceof S02PacketChat) {
            final S02PacketChat packet = (S02PacketChat)event.getPacket();
            final String msg = packet.func_148915_c().getFormattedText();
            if (!this.mute.equalsIgnoreCase("")) {
                if (this.mute.equalsIgnoreCase("all")) {
                    event.setCancelled(true);
                }
                else if (msg.contains(this.mute)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
