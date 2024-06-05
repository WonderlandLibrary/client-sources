/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.render.notification;

import cc.diablo.Main;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.TimerUtil;
import cc.diablo.render.notification.NotificationManager;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Notification {
    public TimerUtil timer = new TimerUtil();
    public String text;
    public long delay;
    public double x;
    public double y;
    public double x2;
    public double y2;
    public double width;
    public double height;

    public Notification(String text, long delay) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.text = text;
        this.delay = delay;
        this.width = 200.0;
        this.height = 27.0;
        this.x = (double)(sr.getScaledWidth() - 3) - this.width;
        this.y = (double)(sr.getScaledHeight() - 7) - this.height;
        this.x2 = sr.getScaledWidth() - 3;
        this.y2 = sr.getScaledHeight() - 7;
        Main.getInstance().getEventBus().register((Object)this);
        this.timer.reset();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (this.timer.reach(this.delay)) {
            NotificationManager.notifications.remove(this);
            this.timer.reset();
        }
    }
}

