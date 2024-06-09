// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.notification.NotificationType;
import net.andrewsnetwork.icarus.Icarus;
import java.util.Iterator;
import net.andrewsnetwork.icarus.values.Value;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Stack;
import net.andrewsnetwork.icarus.module.Module;

public class Detector extends Module
{
    private Stack<EntityPlayer> allPlayersWithin100;
    private final Value<Boolean> vanish;
    
    public Detector() {
        super("Detector", -8739, Category.MISC);
        this.allPlayersWithin100 = new Stack<EntityPlayer>();
        this.vanish = new Value<Boolean>("detector_Vanish Only", "vanishonly", false, this);
    }
    
    public boolean checkPlayer(final EntityPlayer player) {
        for (final EntityPlayer p : this.allPlayersWithin100) {
            if (p.equals(player)) {
                return true;
            }
        }
        return false;
    }
    
    public void addPlayer(final EntityPlayer player) {
        this.allPlayersWithin100.add(player);
    }
    
    public void removePlayer(final EntityPlayer player) {
        final Stack<EntityPlayer> purge = new Stack<EntityPlayer>();
        for (final EntityPlayer p : this.allPlayersWithin100) {
            if (p.equals(player) && p.getName() != Detector.mc.thePlayer.getName()) {
                if (this.vanish.getValue()) {
                    continue;
                }
                Icarus.getNotificationManager().addNotification(String.valueOf(Icarus.getFriendManager().isFriend(player.getName()) ? "Friend " : "Player ") + "Left: §7" + Icarus.getFriendManager().replaceNames(player.getName(), true), NotificationType.INFO);
            }
            else {
                purge.add(p);
            }
        }
        this.allPlayersWithin100 = purge;
    }
    
    public boolean isPlayerWithin100(final EntityPlayer player) {
        return Math.sqrt(Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(player)) <= 100.0;
    }
    
    public boolean isInLoadedEntityList(final EntityPlayer player) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (final Object r : mc.theWorld.getLoadedEntityList()) {
            final Entity REEEEEEEEEEEEEEEEEE = (Entity)r;
            if (REEEEEEEEEEEEEEEEEE instanceof EntityPlayer && REEEEEEEEEEEEEEEEEE.equals(player)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (this.isEnabled()) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (e instanceof PreMotion) {
                for (final Object r : mc.theWorld.getLoadedEntityList()) {
                    final Entity REEEEEEEEEEEEEEEEEE = (Entity)r;
                    if (REEEEEEEEEEEEEEEEEE instanceof EntityPlayer) {
                        if (this.isPlayerWithin100((EntityPlayer)REEEEEEEEEEEEEEEEEE)) {
                            if (this.checkPlayer((EntityPlayer)REEEEEEEEEEEEEEEEEE) || REEEEEEEEEEEEEEEEEE.getName() == mc.thePlayer.getName()) {
                                continue;
                            }
                            if (this.vanish.getValue() && !this.doesTabContain((EntityPlayer)REEEEEEEEEEEEEEEEEE)) {
                                Icarus.getNotificationManager().addNotification(String.valueOf(Icarus.getFriendManager().isFriend(REEEEEEEEEEEEEEEEEE.getName()) ? "Friend " : "Player ") + "Detected: §7" + Icarus.getFriendManager().replaceNames(REEEEEEEEEEEEEEEEEE.getName(), true) + " (vanished)", NotificationType.WARNING);
                            }
                            else if (!this.vanish.getValue() && !this.doesTabContain((EntityPlayer)REEEEEEEEEEEEEEEEEE)) {
                                Icarus.getNotificationManager().addNotification(String.valueOf(Icarus.getFriendManager().isFriend(REEEEEEEEEEEEEEEEEE.getName()) ? "Friend " : "Player ") + "Detected: §7" + Icarus.getFriendManager().replaceNames(REEEEEEEEEEEEEEEEEE.getName(), true) + " (vanished)", NotificationType.WARNING);
                            }
                            else if (!this.vanish.getValue()) {
                                Icarus.getNotificationManager().addNotification(String.valueOf(Icarus.getFriendManager().isFriend(REEEEEEEEEEEEEEEEEE.getName()) ? "Friend " : "Player ") + "Detected: §7" + Icarus.getFriendManager().replaceNames(REEEEEEEEEEEEEEEEEE.getName(), true), NotificationType.WARNING);
                            }
                            this.addPlayer((EntityPlayer)REEEEEEEEEEEEEEEEEE);
                        }
                        else {
                            this.removePlayer((EntityPlayer)REEEEEEEEEEEEEEEEEE);
                        }
                    }
                }
                for (final EntityPlayer player : this.allPlayersWithin100) {
                    if (!this.isInLoadedEntityList(player)) {
                        this.removePlayer(player);
                    }
                }
            }
        }
    }
    
    public boolean doesTabContain(final EntityPlayer player) {
        boolean doesIt = false;
        for (final Object o : Detector.mc.ingameGUI.getTabList().getPlayerList()) {
            final NetworkPlayerInfo playerInfo = (NetworkPlayerInfo)o;
            final String mcName = Detector.mc.ingameGUI.getTabList().func_175243_a(playerInfo);
            if (mcName.contains(player.getName())) {
                doesIt = true;
            }
        }
        return doesIt;
    }
}
