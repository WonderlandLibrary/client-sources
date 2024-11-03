package vestige.ui.menu.components;

import java.util.Objects;
import vestige.util.misc.TimerUtil;

public class Button {
   private String name;
   private boolean hovered;
   private TimerUtil animationTimer;
   private final long animInDuration = 0L;
   private final long animOutDuration = 0L;
   private boolean animationDone;

   public Button(String name) {
      this.name = name;
      this.hovered = false;
      this.animationTimer = new TimerUtil();
   }

   public void updateState(boolean state) {
      if (this.hovered != state) {
         this.animationTimer.reset();
         this.hovered = state;
         this.animationDone = false;
      }

      if (this.animationTimer.getTimeElapsed() >= (this.hovered ? 0L : 0L)) {
         this.animationDone = true;
      }

   }

   public double getMult() {
      double time = (double)this.animationTimer.getTimeElapsed();
      return Math.min((this.hovered ? time : 0.0D - time) / (double)(this.hovered ? 0L : 0L), 1.0D);
   }

   public String getName() {
      return this.name;
   }

   public boolean isHovered() {
      return this.hovered;
   }

   public TimerUtil getAnimationTimer() {
      return this.animationTimer;
   }

   public long getAnimInDuration() {
      Objects.requireNonNull(this);
      return 0L;
   }

   public long getAnimOutDuration() {
      Objects.requireNonNull(this);
      return 0L;
   }

   public boolean isAnimationDone() {
      return this.animationDone;
   }

   public void setAnimationDone(boolean animationDone) {
      this.animationDone = animationDone;
   }
}
