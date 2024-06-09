package exhibition.management.notifications.dev;

import exhibition.Client;
import exhibition.module.Module;
import exhibition.module.impl.movement.Debug;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DevNotifications {
   private static DevNotifications instance = new DevNotifications();
   private List notifications = new CopyOnWriteArrayList();
   private DevNotificationRenderer renderer = new DevNotificationRenderer();

   private DevNotifications() {
      instance = this;
   }

   public static DevNotifications getManager() {
      return instance;
   }

   public void post(String text) {
      System.out.println(text);
      if (((Module)Client.getModuleManager().get(Debug.class)).isEnabled()) {
         this.notifications.add(new DevNotification(text));
         Client.getSourceConsoleGUI().sourceConsole.addStringList(text);
      }
   }

   public void updateAndRender() {
      if (!this.notifications.isEmpty()) {
         this.renderer.draw(this.notifications);
      }
   }
}
