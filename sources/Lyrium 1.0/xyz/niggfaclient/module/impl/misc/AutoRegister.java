// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.notifications.NotificationType;
import xyz.niggfaclient.Client;
import net.minecraft.network.play.server.S02PacketChat;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AutoRegister", description = "Automatically authenticates you", cat = Category.MISC)
public class AutoRegister extends Module
{
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public AutoRegister() {
        S02PacketChat s02;
        String text;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE) {
                if (this.mc.thePlayer != null && this.mc.theWorld != null) {
                    if (this.mc.thePlayer.sendQueue.doneLoadingTerrain && e.getPacket() instanceof S02PacketChat) {
                        s02 = (S02PacketChat)e.getPacket();
                        text = s02.getChatComponent().getUnformattedText();
                        if (text.contains("/register")) {
                            this.mc.thePlayer.sendChatMessage("/register abc12369 abc12369");
                            Client.getInstance().getNotificationManager().add(new Notification("AutoRegister", "Registered with password 'abc12369'", 3000L, NotificationType.SUCCESS));
                        }
                        if (text.contains("/login")) {
                            this.mc.thePlayer.sendChatMessage("/login abc12369");
                            Client.getInstance().getNotificationManager().add(new Notification("AutoRegister", "Logged in with password 'abc12369'", 3000L, NotificationType.SUCCESS));
                        }
                    }
                }
            }
        });
    }
}
