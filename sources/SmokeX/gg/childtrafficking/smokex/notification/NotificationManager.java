// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.notification;

import java.util.LinkedList;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import net.minecraft.client.gui.ScaledResolution;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.client.Minecraft;
import java.util.List;
import gg.childtrafficking.smokex.module.Module;

public final class NotificationManager extends Module
{
    public static List<Notification> notifications;
    private static final Minecraft mc;
    private final EventListener<EventRender2D> render2DEventListener;
    
    public NotificationManager() {
        this.render2DEventListener = (event -> {
            final ScaledResolution scaledResolution = new ScaledResolution(NotificationManager.mc);
            double ydist = 5.0;
            for (int i = 0; i < NotificationManager.notifications.size(); ++i) {
                final Notification notif = NotificationManager.notifications.get(i);
                if (System.currentTimeMillis() - notif.getCreated() > notif.getTime()) {
                    NotificationManager.notifications.remove(notif);
                }
                else {
                    double height = 60.0;
                    final double width = 150.0;
                    final String[] lines = StringUtils.wrapStringToWidth(notif.getContent(), (int)width);
                    if (lines.length > 5) {
                        height += (lines.length - 5) * 12;
                    }
                    NotificationManager.mc.fontRendererObj.drawStringWithShadow(notif.getHeader(), (float)(scaledResolution.getScaledWidth() - width - 3.0), (float)(scaledResolution.getScaledHeight_double() - ydist - height + 2.0), -1);
                    float linedistance = 0.0f;
                    for (int a = 0; a < lines.length; ++a) {
                        NotificationManager.mc.fontRendererObj.drawStringWithShadow(lines[a], (float)(scaledResolution.getScaledWidth() - width - 3.0), (float)(scaledResolution.getScaledHeight_double() - ydist - height + 14.0 + linedistance), -1);
                        linedistance += 12.0f;
                    }
                    ydist += height + 5.0;
                }
            }
        });
    }
    
    static {
        NotificationManager.notifications = new LinkedList<Notification>();
        mc = Minecraft.getMinecraft();
    }
}
