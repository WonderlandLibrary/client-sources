package exhibition.management.notifications.user;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Notifications {
   private static Notifications instance = new Notifications();
   private List notifications = new CopyOnWriteArrayList();
   private NotificationRenderer renderer = new NotificationRenderer();

   public List getNotifications() {
      return this.notifications;
   }

   private Notifications() {
      instance = this;
   }

   public static Notifications getManager() {
      return instance;
   }

   public void post(String header, String subtext) {
      this.post(header, subtext, 2500L);
   }

   public void post(String header, String subtext, Notifications.Type type) {
      this.post(header, subtext, 2500L, type);
   }

   public void post(String header, String subtext, long displayTime) {
      this.post(header, subtext, displayTime, Notifications.Type.INFO);
   }

   public void post(String header, String subtext, long displayTime, Notifications.Type type) {
      this.notifications.add(new Notification(header, subtext, displayTime, type));
   }

   public void updateAndRender() {
      if (!this.notifications.isEmpty()) {
         this.renderer.draw(this.notifications);
      }
   }

   public static enum Type {
      NOTIFY,
      WARNING,
      INFO,
      SPOTIFY;
   }
}
