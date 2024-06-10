/*    */ package nightmare.gui.notification;
/*    */ 
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ 
/*    */ public class NotificationManager {
/*  6 */   private static final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();
/*    */   
/*    */   public static void doRender(float wid, float hei) {
/*  9 */     float startY = hei - 23.0F;
/* 10 */     for (Notification notification : notifications) {
/* 11 */       if (notification == null) {
/*    */         continue;
/*    */       }
/* 14 */       notification.draw(wid, startY);
/* 15 */       startY -= notification.getHeight() + 2.0F;
/*    */     } 
/* 17 */     notifications.removeIf(Notification::shouldDelete);
/*    */   }
/*    */   
/*    */   public static void show(NotificationType notificationType, String title, String message, int delay) {
/* 21 */     notifications.add(new Notification(notificationType, title, message, delay));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\gui\notification\NotificationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */