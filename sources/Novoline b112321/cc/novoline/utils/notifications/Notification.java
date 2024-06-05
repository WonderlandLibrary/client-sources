package cc.novoline.utils.notifications;

import cc.novoline.utils.Timer;
import cc.novoline.utils.notifications.NotificationType;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.MathHelper;

public final class Notification {
   private final NotificationType notificationType;
   private final String message;
   final Timer timer = new Timer();
   boolean extending;
   public final int delay;
   private String callReason;
   public double x;
   public double y;
   public int e;

   Notification(String var1, int var2, NotificationType var3) {
      this.message = var1;
      this.notificationType = var3;
      this.x = 0.0D;
      this.y = 50.0D;
      this.delay = var2;
      this.extending = false;
      this.e = ThreadLocalRandom.current().nextInt(0, 360);
   }

   Notification(String var1, String var2, int var3, NotificationType var4) {
      this.message = var2;
      this.notificationType = var4;
      this.x = 0.0D;
      NotificationType.b();
      this.y = 50.0D;
      this.delay = var3;
      this.extending = false;
      this.callReason = var1;
      this.e = ThreadLocalRandom.current().nextInt(0, 360);
   }

   public NotificationType getType() {
      return this.notificationType;
   }

   public String getMessage() {
      return this.message;
   }

   public Timer getTimer() {
      return this.timer;
   }

   public int getDelay() {
      return this.delay;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public NotificationType getNotificationType() {
      return this.notificationType;
   }

   public boolean isExtending() {
      return this.extending;
   }

   public void setExtending(boolean var1) {
      this.extending = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public double getCount() {
      return (double)MathHelper.clamp_float((float)(this.getTimer().getCurrentMS() - this.getTimer().getLastMS()), 0.0F, (float)this.getDelay());
   }

   public String getCallReason() {
      return this.callReason;
   }

   public int i() {
      return this.e;
   }
}
