package vestige.util.animation;

public class Animationn {
   private Easing easing;
   private long duration;
   private long millis;
   private long startTime;
   private double startValue;
   private double destinationValue;
   private double value;
   private boolean finished;

   public Animationn(Easing easing, long duration) {
      this.easing = easing;
      this.startTime = System.currentTimeMillis();
      this.duration = duration;
   }

   public void run(double destinationValue) {
      this.millis = System.currentTimeMillis();
      if (this.destinationValue != destinationValue) {
         this.destinationValue = destinationValue;
         this.reset();
      } else {
         this.finished = this.millis - this.duration > this.startTime;
         if (this.finished) {
            this.value = destinationValue;
            return;
         }
      }

      double result = (Double)this.easing.getFunction().apply(this.getProgress());
      if (this.value > destinationValue) {
         this.value = this.startValue - (this.startValue - destinationValue) * result;
      } else {
         this.value = this.startValue + (destinationValue - this.startValue) * result;
      }

   }

   public double getProgress() {
      return (double)(System.currentTimeMillis() - this.startTime) / (double)this.duration;
   }

   public void reset() {
      this.startTime = System.currentTimeMillis();
      this.startValue = this.value;
      this.finished = false;
   }

   public Easing getEasing() {
      return this.easing;
   }

   public long getDuration() {
      return this.duration;
   }

   public long getMillis() {
      return this.millis;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public double getStartValue() {
      return this.startValue;
   }

   public double getDestinationValue() {
      return this.destinationValue;
   }

   public double getValue() {
      return this.value;
   }

   public boolean isFinished() {
      return this.finished;
   }

   public void setEasing(Easing easing) {
      this.easing = easing;
   }

   public void setDuration(long duration) {
      this.duration = duration;
   }

   public void setMillis(long millis) {
      this.millis = millis;
   }

   public void setStartTime(long startTime) {
      this.startTime = startTime;
   }

   public void setStartValue(double startValue) {
      this.startValue = startValue;
   }

   public void setDestinationValue(double destinationValue) {
      this.destinationValue = destinationValue;
   }

   public void setValue(double value) {
      this.value = value;
   }

   public void setFinished(boolean finished) {
      this.finished = finished;
   }
}
