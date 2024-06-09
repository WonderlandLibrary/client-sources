package exhibition.management.notifications.dev;

import exhibition.management.animate.Opacity;
import exhibition.management.animate.Translate;

public class DevNotification implements IDevNotification {
   private String text;
   private long start;
   private long displayTime = 500L;
   public int targetOpacity;
   public Translate translate;
   public Opacity opacity;

   public DevNotification(String text) {
      this.text = text;
      this.start = System.currentTimeMillis();
      this.translate = new Translate(2.0F, 0.0F);
      this.opacity = new Opacity(255);
      this.targetOpacity = 255;
   }

   public long checkTime() {
      return System.currentTimeMillis() - this.getDisplayTime();
   }

   public String getMessage() {
      return this.text;
   }

   public long getInitializeTime() {
      return this.start;
   }

   public long getDisplayTime() {
      return this.displayTime;
   }
}
