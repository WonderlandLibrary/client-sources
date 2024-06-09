// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.notifications;

import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import java.util.List;

public class NotificationManager
{
    private static List<Notification> notis;
    
    public static void addNotificationToQueue(final Notification noti) {
        NotificationManager.notis.add(noti);
    }
    
    public static void render() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final List<Notification> toRemove = new ArrayList<Notification>();
        int notiY = sr.getScaledHeight() / 4 - 150;
        try {
            for (final Notification noti : NotificationManager.notis) {
                notiY += 35;
                noti.draw(notiY);
                if (!noti.isShowing()) {
                    toRemove.add(noti);
                }
            }
            toRemove.forEach(notification -> NotificationManager.notis.remove(notification));
        }
        catch (Exception ex) {}
    }
    
    public static void render2() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final List<Notification> toRemove = new ArrayList<Notification>();
        int notiY = sr.getScaledHeight() / 4 - 150;
        try {
            for (final Notification noti : NotificationManager.notis) {
                notiY += 35;
                noti.draw2(notiY);
                if (!noti.isShowing()) {
                    toRemove.add(noti);
                }
            }
        }
        catch (Exception ex) {}
        toRemove.forEach(notification -> NotificationManager.notis.remove(notification));
    }
    
    static {
        NotificationManager.notis = new ArrayList<Notification>();
    }
}
