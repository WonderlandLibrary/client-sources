// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.notifications;

import net.minecraft.client.gui.ScaledResolution;
import java.util.List;
import java.util.ArrayList;

public class NotificationManager extends Manager<Notification>
{
    public NotificationManager() {
        super(new ArrayList());
    }
    
    public void render(final ScaledResolution scaledResolution) {
        final List<Notification> notifications = this.getElements();
        Notification remove = null;
        for (int i = 0; i < notifications.size(); ++i) {
            final Notification notification = notifications.get(i);
            if (notification.isDead()) {
                remove = notification;
            }
            else {
                notification.render(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), i + 1);
            }
        }
        if (remove != null) {
            this.getElements().remove(remove);
        }
    }
    
    public void add(final Notification notification) {
        this.getElements().add(notification);
    }
}
