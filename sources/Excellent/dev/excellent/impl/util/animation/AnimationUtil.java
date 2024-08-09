package dev.excellent.impl.util.animation;

public class AnimationUtil {
   long mc;
   float anim;
   public float to;
   public float speed;

   public AnimationUtil(float anim, float to, float speed) {
      this.anim = anim;
      this.to = to;
      this.speed = speed;
      mc = System.currentTimeMillis();
   }

   public static AnimationUtil create(float anim, float to, float speed) {
      return new AnimationUtil(anim, to, speed);
   }

   public float getAnim() {
      int count = (int) ((System.currentTimeMillis() - mc) / 5);
      if (count > 0) mc = System.currentTimeMillis();
      for (int i = 0; i < count; i++)
         anim = anim + (to - anim) * speed;
      return anim;
   }

   public void reset() {
      mc = System.currentTimeMillis();
   }

   public void setAnim(float anim) {
      this.anim = anim;
      mc = System.currentTimeMillis();
   }

   public void setTo(float to) {
      this.to = to;
   }

   public float getTo() {
      return this.to;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }
}
