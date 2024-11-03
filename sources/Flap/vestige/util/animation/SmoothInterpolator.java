package vestige.util.animation;

public class SmoothInterpolator {
   private float startValue;
   private float endValue;
   private long startTime;
   private long duration;
   private boolean isRunning;

   public SmoothInterpolator(float startValue, float endValue, long duration) {
      this.startValue = startValue;
      this.endValue = endValue;
      this.duration = duration;
      this.startTime = System.currentTimeMillis();
      this.isRunning = true;
   }

   private float easeInOutCubic(float t) {
      return (double)t < 0.5D ? 4.0F * t * t * t : 1.0F - (float)Math.pow((double)(-2.0F * t + 2.0F), 3.0D) / 2.0F;
   }

   public float getInterpolatedValue() {
      if (!this.isRunning) {
         return this.endValue;
      } else {
         long elapsed = System.currentTimeMillis() - this.startTime;
         if (elapsed >= this.duration) {
            this.isRunning = false;
            return this.endValue;
         } else {
            float progress = (float)elapsed / (float)this.duration;
            float easedProgress = this.easeInOutCubic(progress);
            return this.startValue + easedProgress * (this.endValue - this.startValue);
         }
      }
   }

   public boolean isRunning() {
      return this.isRunning;
   }
}
