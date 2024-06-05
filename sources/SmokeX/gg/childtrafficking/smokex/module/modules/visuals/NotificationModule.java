// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import java.util.ArrayList;
import gg.childtrafficking.smokex.notification.Notification;
import java.util.List;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Notifications", renderName = "Notifications", description = "Displays notifications", category = ModuleCategory.VISUALS)
public final class NotificationModule extends Module
{
    private final List<Notification> notificationList;
    
    public NotificationModule() {
        this.notificationList = new ArrayList<Notification>();
    }
    
    @Override
    public void init() {
        super.init();
    }
}
