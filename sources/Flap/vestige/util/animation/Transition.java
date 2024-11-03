package vestige.util.animation;

public class Transition {
   private float startValue;
   private float targetValue;
   private float currentValue;
   private long startTime;
   private long transitionDuration;
   private boolean transitioning;
   private Transition.EaseType easeType;

   public Transition(float initialValue, long duration, Transition.EaseType easeType) {
      this.startValue = initialValue;
      this.currentValue = initialValue;
      this.transitionDuration = duration;
      this.easeType = easeType;
      this.transitioning = false;
   }

   public void startTransition(float targetValue) {
      this.startValue = this.currentValue;
      this.targetValue = targetValue;
      this.startTime = System.currentTimeMillis();
      this.transitioning = true;
   }

   public float updateValue() {
      if (!this.transitioning) {
         return this.currentValue;
      } else {
         long currentTime = System.currentTimeMillis();
         float elapsedTime = (float)(currentTime - this.startTime) / (float)this.transitionDuration;
         if (elapsedTime >= 1.0F) {
            this.transitioning = false;
            this.currentValue = this.targetValue;
            return this.currentValue;
         } else {
            float easingFactor = this.applyEasing(elapsedTime, this.easeType);
            this.currentValue = this.interpolate(this.startValue, this.targetValue, easingFactor);
            return this.currentValue;
         }
      }
   }

   private float applyEasing(float time, Transition.EaseType easeType) {
      switch(easeType.ordinal()) {
      case 0:
      default:
         return time;
      case 1:
         return time * time;
      case 2:
         return 1.0F - (1.0F - time) * (1.0F - time);
      case 3:
         return (double)time < 0.5D ? 2.0F * time * time : (float)(1.0D - Math.pow((double)(-2.0F * time + 2.0F), 2.0D) / 2.0D);
      }
   }

   private float interpolate(float start, float end, float fraction) {
      return start + (end - start) * fraction;
   }

   public void adjustStartValue(float newStartValue) {
      if (this.transitioning) {
         this.startValue = newStartValue;
         this.startTime = System.currentTimeMillis();
      }

   }

   public boolean isTransitioning() {
      return this.transitioning;
   }

   public float getCurrentValue() {
      return this.currentValue;
   }

   public static enum EaseType {
      LINEAR,
      EASE_IN,
      EASE_OUT,
      EASE_IN_OUT;

      // $FF: synthetic method
      private static Transition.EaseType[] $values() {
         return new Transition.EaseType[]{LINEAR, EASE_IN, EASE_OUT, EASE_IN_OUT};
      }
   }
}
