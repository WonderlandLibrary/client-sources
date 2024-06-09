package exhibition.management.notifications.dev;

import exhibition.Client;
import exhibition.util.render.Colors;
import java.util.Iterator;
import java.util.List;

public class DevNotificationRenderer implements IDevNotificationRenderer {
   public void draw(List notifications) {
      int y = 3;
      Iterator var3 = notifications.iterator();

      while(var3.hasNext()) {
         DevNotification notification = (DevNotification)var3.next();
         notification.opacity.interpolate((float)notification.targetOpacity);
         notification.translate.interpolate(60.0F, (float)y, 0.35F);
         Client.verdana16.drawStringWithShadow(notification.getMessage(), 60.0F, notification.translate.getY(), Colors.getColor(255, (int)notification.opacity.getOpacity()));
         y += 5;
         if (notification.checkTime() >= notification.getDisplayTime() + notification.getInitializeTime()) {
            notification.targetOpacity = 0;
            if (notification.opacity.getOpacity() <= 0.0F) {
               notifications.remove(notification);
            }
         }
      }

   }
}
