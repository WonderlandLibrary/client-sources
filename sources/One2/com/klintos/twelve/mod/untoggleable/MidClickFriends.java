// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.untoggleable;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import com.klintos.twelve.handlers.friend.Friend;
import com.klintos.twelve.utils.FileUtils;
import com.klintos.twelve.handlers.notifications.Notification;
import com.klintos.twelve.Twelve;
import org.lwjgl.input.Mouse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventManager;
import com.klintos.twelve.utils.TimerUtil;

public class MidClickFriends
{
    private TimerUtil timer;
    
    public MidClickFriends() {
        this.timer = new TimerUtil();
        EventManager.register((Object)this);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        final Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
        if (entity instanceof EntityPlayer && Mouse.isButtonDown(2) && this.timer.delay(1000.0)) {
            if (Twelve.getInstance().getFriendHandler().isFriend(entity.getName())) {
                Twelve.getInstance().getFriendHandler().delFriend(entity.getName());
                Twelve.getInstance().getNotificationHandler().addNotification(new Notification(String.valueOf(entity.getName()) + " is no longer your friend.", -43691));
                FileUtils.saveFriends();
            }
            else {
                Twelve.getInstance().getFriendHandler().addFriend(new Friend(entity.getName(), ""));
                Twelve.getInstance().getNotificationHandler().addNotification(new Notification(String.valueOf(entity.getName()) + " is now your friend.", -43691));
                FileUtils.saveFriends();
            }
        }
    }
}
