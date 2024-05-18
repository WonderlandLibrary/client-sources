// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.untoggleable;

import com.darkmagician6.eventapi.EventTarget;
import java.util.Iterator;
import com.klintos.twelve.handlers.notifications.Notification;
import com.klintos.twelve.Twelve;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventManager;

public class KillFeed
{
    public KillFeed() {
        EventManager.register((Object)this);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        for (final Object o : Minecraft.getMinecraft().theWorld.playerEntities) {
            if (o instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)o;
                if (player.equals((Object)Minecraft.getMinecraft().thePlayer)) {
                    continue;
                }
                if (player.deathTime != 1) {
                    continue;
                }
                Twelve.getInstance().getNotificationHandler().addNotification(new Notification(String.valueOf(player.getName()) + " has just died.", -43691));
            }
        }
    }
}
