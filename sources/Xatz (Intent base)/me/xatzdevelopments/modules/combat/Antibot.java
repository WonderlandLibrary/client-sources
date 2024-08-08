package me.xatzdevelopments.modules.combat;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.notifications.Notification;
import me.xatzdevelopments.notifications.NotificationManager;
import me.xatzdevelopments.settings.ModeSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Antibot extends Module
{
    public ModeSetting Mode;
    
    public Antibot() {
		super("Antibot", Keyboard.KEY_NONE, Category.COMBAT, "Gets rid of the Anti-Killaura bot that some servers have.");
        this.Mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Mineplex", "Simple", "BadEID", "InvalidMove" });
        this.addSettings(this.Mode);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            final String mode;
            switch (mode = this.Mode.getMode()) {
                case "Simple": {
                    for (final Object entity : this.mc.theWorld.loadedEntityList) {
                        final EntityLivingBase e2 = (EntityLivingBase)entity;
                        if (e2 instanceof EntityPlayer) {
                            return;
                        }
                        if (e2.ticksExisted <= 100) {
                            e2.posY = this.mc.thePlayer.posY + 999.0;
                        }
                        if (e2.getHealth() > 0.0f && e2.hurtTime >= 0 && e2.hurtTime <= 10) {
                            continue;
                        }
                        this.mc.theWorld.removeEntity(e2);
                    }
                    break;
                }
                case "Mineplex": {
                    for (final Object entity : this.mc.theWorld.loadedEntityList) {
                        if (entity != null && entity != this.mc.thePlayer && entity instanceof EntityPlayer && ((Entity)entity).getDisplayName().getFormattedText().replace("§r", "").contains("§") && !((Entity)entity).getDisplayName().getFormattedText().contains(this.mc.thePlayer.getDisplayName().getFormattedText()) && ((EntityLivingBase)entity).getHealth() >= 0.0f && ((Entity)entity).getCustomNameTag() == "") {
                            this.mc.theWorld.removeEntity((Entity)entity);
                        }
                    }
                    break;
                }
                case "Hypixel": {
                    for (final Object entity : this.mc.theWorld.loadedEntityList) {
                        if ((((Entity)entity).isInvisible() && entity != this.mc.thePlayer) || ((Entity)entity).getName().contains(" ")) {
                            this.mc.theWorld.removeEntity((Entity)entity);
                        }
                    }
                    break;
                }
                case "InvalidMove": {
                    for (final Object entity : this.mc.theWorld.loadedEntityList) {
                        final Entity e3 = (Entity)entity;
                        if (entity != this.mc.thePlayer) {
                            final double moveDistance = e3.getDistance(e3.lastTickPosX, e3.posY, e3.lastTickPosZ);
                            if (moveDistance <= 4.0 || this.mc.thePlayer.ticksExisted <= 2 || e3.ticksExisted <= 2 || !(e3 instanceof EntityPlayer)) {
                                continue;
                            }
                            this.mc.theWorld.removeEntity(e3);
                            //pendingNotifications.ad("Antibot", "Removed bot " + e3.getName());
                        }
                    }
                    break;
                }
                case "BadEID": {
                    for (final Object entity : this.mc.theWorld.loadedEntityList) {
                        final EntityLivingBase e2 = (EntityLivingBase)entity;
                        if (e2 instanceof EntityPlayer) {
                            return;
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }
}
