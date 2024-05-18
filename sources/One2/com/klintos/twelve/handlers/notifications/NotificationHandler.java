// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers.notifications;

import java.util.Iterator;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.Twelve;
import java.util.ArrayList;

public class NotificationHandler
{
    private ArrayList<Notification> notifications;
    
    public NotificationHandler() {
        this.notifications = new ArrayList<Notification>();
    }
    
    public ArrayList<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void addNotification(final Notification notification) {
        this.notifications.add(notification);
    }
    
    public void drawNotifications(final int x, final int y) {
        int i = 0;
        final Iterator<Notification> ilerator = this.getNotifications().iterator();
        while (ilerator.hasNext()) {
            final Notification n = ilerator.next();
            final int width = Twelve.getMinecraft().fontRendererObj.getStringWidth(n.getMessage()) / 2;
            GuiUtils.drawFineBorderedRect(x - width - 5, y + i, x + width + 5, y + 13 + i, -43691, Integer.MIN_VALUE);
            Twelve.getMinecraft().fontRendererObj.drawStringWithShadow(n.getMessage(), x - width, y + i + 3, -1);
            i += 15;
            if (n.shouldClose()) {
                ilerator.remove();
            }
        }
    }
}
