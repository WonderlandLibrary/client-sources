package exhibition.management.animate;

public class Rotate {
   private float angle;

   public Rotate(float angle) {
      this.angle = angle;
   }

   public void interpolate(float targetAngle) {
      float diffA = (this.angle - targetAngle) * 0.6F;
      float tempAngle = this.angle + diffA;
      tempAngle %= 360.0F;
      if (tempAngle >= 180.0F) {
         tempAngle -= 360.0F;
      }

      if (tempAngle < -180.0F) {
         tempAngle += 360.0F;
      }

      this.angle = tempAngle;
   }

   public float getAngle() {
      return this.angle;
   }
}
