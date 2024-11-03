package vestige.util.util;

import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;

public class Cold {
   private long lastMs;
   private long time;
   private boolean checkedFinish;

   public Cold(long lasts) {
      this.lastMs = lasts;
   }

   public Cold() {
      this.lastMs = System.currentTimeMillis();
   }

   public void start() {
      this.reset();
      this.checkedFinish = false;
   }

   public boolean firstFinish() {
      return this.checkAndSetFinish(() -> {
         return System.currentTimeMillis() >= this.time + this.lastMs;
      });
   }

   public void setCooldown(long time) {
      this.lastMs = time;
   }

   public boolean hasFinished() {
      return this.isElapsed(this.time + this.lastMs, System::currentTimeMillis);
   }

   public boolean finished(long delay) {
      return this.isElapsed(this.time, () -> {
         return System.currentTimeMillis() - delay;
      });
   }

   public boolean isDelayComplete(long l) {
      return this.isElapsed(this.lastMs, () -> {
         return System.currentTimeMillis() - l;
      });
   }

   public boolean reached(long currentTime) {
      return this.isElapsed(this.time, () -> {
         return Math.max(0L, System.currentTimeMillis() - currentTime);
      });
   }

   public void reset() {
      this.time = System.currentTimeMillis();
   }

   public long getTime() {
      return Math.max(0L, System.currentTimeMillis() - this.time);
   }

   public boolean getCum(long hentai) {
      return this.getTime() - this.lastMs >= hentai;
   }

   public boolean hasTimeElapsed(long owo, boolean reset) {
      if (this.getTime() >= owo) {
         if (reset) {
            this.reset();
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean checkAndSetFinish(BooleanSupplier condition) {
      if (condition.getAsBoolean() && !this.checkedFinish) {
         this.checkedFinish = true;
         return true;
      } else {
         return false;
      }
   }

   private boolean isElapsed(long targetTime, LongSupplier currentTimeSupplier) {
      return currentTimeSupplier.getAsLong() >= targetTime;
   }
}
