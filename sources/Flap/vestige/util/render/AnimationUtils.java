package vestige.util.render;

public class AnimationUtils {
   private double value;
   private long lastMS;

   public AnimationUtils(double value) {
      this.value = value;
      this.lastMS = System.currentTimeMillis();
   }

   public static double calculateCompensation(double target, double current, double speed, long delta) {
      double diff = current - target;
      double add = (double)delta * (speed / 50.0D);
      if (diff > speed) {
         if (current - add > target) {
            current -= add;
         } else {
            current = target;
         }
      } else if (diff < -speed) {
         if (current + add < target) {
            current += add;
         } else {
            current = target;
         }
      } else {
         current = target;
      }

      return current;
   }

   public void setAnimation(double value, double speed) {
      long currentMS = System.currentTimeMillis();
      long delta = currentMS - this.lastMS;
      this.lastMS = currentMS;
      double deltaValue = 0.0D;
      if (speed > 28.0D) {
         speed = 28.0D;
      }

      if (speed != 0.0D) {
         deltaValue = Math.abs(value - this.value) * 0.3499999940395355D / (10.0D / speed);
      }

      this.value = calculateCompensation(value, this.value, deltaValue, delta);
   }

   public double getValue() {
      return this.value;
   }
}
