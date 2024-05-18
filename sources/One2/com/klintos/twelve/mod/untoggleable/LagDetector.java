// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.untoggleable;

import com.klintos.twelve.handlers.notifications.Notification;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPacketRecieve;
import com.darkmagician6.eventapi.EventManager;
import com.klintos.twelve.utils.TimerUtil;

public class LagDetector
{
    private TimerUtil timer;
    private boolean notif;
    
    public LagDetector() {
        this.timer = new TimerUtil();
        EventManager.register((Object)this);
    }
    
    @EventTarget
    public void onPacketRecieve(final EventPacketRecieve event) {
        this.timer.reset();
        this.notif = false;
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (this.timer.getTime() - this.timer.getPrevMS() > 1000L && !this.notif) {
            Twelve.getInstance().getNotificationHandler().addNotification(new Notification("Host is not responding...", -43691));
            this.notif = true;
        }
    }
}
