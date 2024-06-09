// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.notifications.NotificationType;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.utils.other.ServerUtils;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.TickEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AutoHypixel", description = "Allows you to automatically do various things on Hypixel", cat = Category.MISC)
public class AutoHypixel extends Module
{
    private final EnumProperty<Mode> mode;
    private final DoubleProperty delay;
    private final Property<Boolean> autoLeaveIfBan;
    private final Property<Boolean> autoGG;
    @EventLink
    private final Listener<TickEvent> tickEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public AutoHypixel() {
        this.mode = new EnumProperty<Mode>("Auto Play Mode", Mode.SoloInsane);
        this.delay = new DoubleProperty("Delay ", 1000.0, 100.0, 2000.0, 50.0, Representation.MILLISECONDS);
        this.autoLeaveIfBan = new Property<Boolean>("Auto Leave If Ban", false);
        this.autoGG = new Property<Boolean>("Auto GG", false);
        this.tickEventListener = (e -> {
            if (!ServerUtils.isOnHypixel()) {
                Client.getInstance().getNotificationManager().add(new Notification("Auto Hypixel", "You need to be on hypixel to use this module!", 5000L, NotificationType.ERROR));
                this.toggle();
            }
            return;
        });
        S02PacketChat packet;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE) {
                try {
                    if (e.getPacket() instanceof S02PacketChat) {
                        packet = (S02PacketChat)e.getPacket();
                        if (this.autoLeaveIfBan.getValue() && packet.getChatComponent().getUnformattedText().contains("A player has been removed from your game.")) {
                            Client.getInstance().getNotificationManager().add(new Notification("Auto Hypixel", "A player has been banned, auto leaving..", 6000L, NotificationType.INFO));
                            this.startThread(0.0);
                        }
                        if (packet.getChatComponent().getUnformattedText().contains("You died!") || packet.getChatComponent().getUnformattedText().contains("You won!") || packet.getChatComponent().getUnformattedText().contains("Winners - ")) {
                            Client.getInstance().getNotificationManager().add(new Notification("Auto Hypixel", "Sending you to a new game!", this.delay.getValue().longValue(), NotificationType.INFO));
                            this.startThread(this.delay.getValue());
                        }
                        if (packet.getChatComponent().getUnformattedText().contains("You won!") && this.autoGG.getValue()) {
                            PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("GG"));
                        }
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void startThread(final double delay) {
        new Thread(() -> {
            try {
                Thread.sleep((long)delay);
                switch (this.mode.getValue()) {
                    case SoloNormal: {
                        PacketUtil.sendPacket(new C01PacketChatMessage("/play solo_normal"));
                        break;
                    }
                    case SoloInsane: {
                        PacketUtil.sendPacket(new C01PacketChatMessage("/play solo_insane"));
                        break;
                    }
                    case TeamsNormal: {
                        PacketUtil.sendPacket(new C01PacketChatMessage("/play teams_normal"));
                        break;
                    }
                    case TeamsInsane: {
                        PacketUtil.sendPacket(new C01PacketChatMessage("/play teams_insane"));
                        break;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public enum Mode
    {
        SoloNormal, 
        SoloInsane, 
        TeamsNormal, 
        TeamsInsane;
    }
}
