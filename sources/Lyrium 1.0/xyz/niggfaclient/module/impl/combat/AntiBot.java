// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.combat;

import xyz.niggfaclient.notifications.NotificationManager;
import java.util.ArrayList;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.Iterator;
import xyz.niggfaclient.notifications.NotificationType;
import xyz.niggfaclient.notifications.Notification;
import xyz.niggfaclient.Client;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.entity.Entity;
import java.util.List;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AntiBot", description = "Prevents you from attacking KillAura bots", cat = Category.COMBAT)
public class AntiBot extends Module
{
    public static EnumProperty<AntiBotMode> antibotMode;
    public static List<Entity> bots;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public AntiBot() {
        final Iterator<EntityPlayer> iterator;
        EntityPlayer loadedPlayerList;
        this.motionEventListener = (e -> {
            this.setDisplayName("Anti Bot §7" + AntiBot.antibotMode.getValue());
            AntiBot.bots.clear();
            switch (AntiBot.antibotMode.getValue()) {
                case Watchdog: {
                    this.mc.theWorld.playerEntities.iterator();
                    while (iterator.hasNext()) {
                        loadedPlayerList = iterator.next();
                        if (loadedPlayerList != this.mc.thePlayer && loadedPlayerList.isInvisible() && loadedPlayerList.onGround) {
                            if (loadedPlayerList.posY - this.mc.thePlayer.lastTickPosY > 1.5) {
                                AntiBot.bots.add(loadedPlayerList);
                            }
                            if (loadedPlayerList.posY - this.mc.thePlayer.posY > 4.0 && loadedPlayerList.posY - this.mc.thePlayer.posY < 10.0 && loadedPlayerList.posY - this.mc.thePlayer.lastTickPosY < 0.5) {
                                AntiBot.bots.add(loadedPlayerList);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    break;
                }
            }
            return;
        });
        S0CPacketSpawnPlayer packet;
        EntityPlayer entity;
        double posX;
        double posY;
        double posZ;
        double difX;
        double difY;
        double difZ;
        double dist;
        final Notification notification;
        final Object o;
        S0CPacketSpawnPlayer packet2;
        NetworkPlayerInfo info;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE) {
                switch (AntiBot.antibotMode.getValue()) {
                    case Watchdog: {
                        if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
                            packet = (S0CPacketSpawnPlayer)e.getPacket();
                            entity = (EntityPlayer)this.mc.theWorld.getEntityByID(packet.getEntityID());
                            posX = packet.getX() / 32.0;
                            posY = packet.getY() / 32.0;
                            posZ = packet.getZ() / 32.0;
                            difX = this.mc.thePlayer.posX - posX;
                            difY = this.mc.thePlayer.posY - posY;
                            difZ = this.mc.thePlayer.posZ - posZ;
                            dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);
                            if (this.mc.theWorld.playerEntities.contains(entity) && dist <= 17.0 && !entity.equals(this.mc.thePlayer) && posX != this.mc.thePlayer.posX && posY != this.mc.thePlayer.posY && posZ != this.mc.thePlayer.posZ) {
                                AntiBot.bots.add(entity);
                                Client.getInstance().getNotificationManager();
                                new Notification("Anti Bot", "Removed bot named " + entity.getName(), 4500L, NotificationType.SUCCESS);
                                ((NotificationManager)o).add(notification);
                            }
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Matrix:
                    case Ping: {
                        if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
                            packet2 = (S0CPacketSpawnPlayer)e.getPacket();
                            if (packet2.getPlayer() != this.mc.thePlayer.getUniqueID()) {
                                info = this.mc.getNetHandler().getPlayerInfo(packet2.getPlayer());
                                if (info == null || info.getResponseTime() != 1) {
                                    AntiBot.bots.add(this.mc.theWorld.getPlayerEntityByUUID(packet2.getPlayer()));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        AntiBot.bots.clear();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        AntiBot.bots.clear();
    }
    
    static {
        AntiBot.antibotMode = new EnumProperty<AntiBotMode>("AntiBot Mode", AntiBotMode.Watchdog);
        AntiBot.bots = new ArrayList<Entity>();
    }
    
    public enum AntiBotMode
    {
        Ping, 
        Matrix, 
        Watchdog;
    }
}
