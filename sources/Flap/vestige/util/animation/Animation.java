package vestige.util.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import vestige.util.misc.TimerUtil;
import vestige.util.misc.VoidFunction;

public class Animation {
   private boolean rendered;
   private AnimationType animType;
   private long animDuration = 250L;
   private AnimationState state;
   private final TimerUtil timer;
   private boolean animDone;
   private boolean lastEnabled;
   private float timeElapsed;

   public Animation() {
      this.state = AnimationState.IN;
      this.timer = new TimerUtil();
   }

   public void updateState(boolean rendered) {
      this.rendered = rendered;
      if (this.timer.getTimeElapsed() >= this.animDuration) {
         this.animDone = true;
      }

      if (this.animDone) {
         this.timer.reset();
      }

      if (rendered && !this.lastEnabled) {
         this.state = AnimationState.IN;
         if (!this.animDone) {
            this.timer.setTimeElapsed(this.animDuration - this.timer.getTimeElapsed());
         }

         this.animDone = false;
      } else if (!rendered && this.lastEnabled) {
         this.state = AnimationState.OUT;
         if (!this.animDone) {
            this.timer.setTimeElapsed(this.animDuration - this.timer.getTimeElapsed());
         }

         this.animDone = false;
      }

      this.timeElapsed = (float)Math.max(this.timer.getTimeElapsed(), 1L);
      this.lastEnabled = rendered;
   }

   public void render(VoidFunction renderInstructions, float startX, float startY, float endX, float endY) {
      GL11.glPushMatrix();
      if (!this.animDone) {
         if (this.state == AnimationState.IN) {
            this.startAnimationIn(startX, startY, endX, endY);
         } else {
            this.startAnimationOut(startX, startY, endX, endY);
         }
      }

      renderInstructions.execute();
      if (!this.animDone) {
         if (this.state == AnimationState.IN) {
            this.stopAnimationIn(startX, startY, endX, endY);
         } else {
            this.stopAnimationOut(startX, startY, endX, endY);
         }
      }

      GL11.glPopMatrix();
   }

   public float getYMult() {
      this.timeElapsed = (float)Math.max(this.timer.getTimeElapsed(), 1L);
      if (!this.animDone) {
         if (this.animType == AnimationType.POP || this.animType == AnimationType.SLIDE) {
            if (this.rendered) {
               return this.timeElapsed / (float)this.animDuration;
            }

            return 1.0F - this.timeElapsed / (float)this.animDuration;
         }

         if (this.animType == AnimationType.POP2) {
            float anim1 = (float)this.animDuration * 0.9F;
            float anim2 = (float)this.animDuration * 0.1F;
            return this.rendered ? (anim1 >= this.timeElapsed ? 1.1F / anim1 * this.timeElapsed : 1.1F - 0.1F / anim2 * (this.timeElapsed - anim1)) : (this.timeElapsed >= anim2 ? 1.1F - 1.1F / anim1 * (this.timeElapsed - anim2) : 1.0F + 0.1F / anim2 * this.timeElapsed);
         }
      }

      return 1.0F;
   }

   private void startAnimationIn(float startX, float startY, float endX, float endY) {
      switch(this.animType) {
      case POP:
      case POP2:
         this.startScaling(this.getYMult(), startX, startY, endX, endY);
         break;
      case SLIDE:
         ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
         int screenWidth = sr.getScaledWidth() + 3;
         startY -= 100.0F;
         float easeInFactor = this.getYMult() * this.getYMult();
         float x;
         if (startX > (float)(sr.getScaledWidth() / 2)) {
            x = ((float)screenWidth - startX) * (1.0F - easeInFactor);
         } else {
            x = -endX * (1.0F - easeInFactor);
         }

         float y;
         if (startY > (float)(sr.getScaledHeight() / 2)) {
            y = ((float)sr.getScaledHeight() - startY) * (1.0F - easeInFactor);
         } else {
            y = -endY * (1.0F - easeInFactor);
         }

         GL11.glTranslatef(x, y, 0.0F);
      }

   }

   private void stopAnimationIn(float startX, float startY, float endX, float endY) {
      switch(this.animType) {
      case POP:
      case POP2:
         this.stopScaling(this.getYMult(), startX, startY, endX, endY);
         break;
      case SLIDE:
         ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
         int screenWidth = sr.getScaledWidth() + 3;
         float easeInFactor = (float)Math.pow((double)this.getYMult(), 0.0D);
         float progress = this.getYMult();
         float smoothFactor;
         if (progress > 0.7F) {
            smoothFactor = 1.0F - (progress - 0.7F) * 10.0F;
         }

         smoothFactor = 0.1F;
         float x;
         if (startX > (float)(sr.getScaledWidth() / 2)) {
            x = ((float)screenWidth - startX) * (1.0F - easeInFactor * smoothFactor);
         } else {
            x = -endX * (1.0F - easeInFactor * smoothFactor);
         }

         float y;
         if (startY > (float)(sr.getScaledHeight() / 2)) {
            y = (float)sr.getScaledHeight() - endY - startY * (1.0F - easeInFactor * smoothFactor);
         } else {
            y = -endY * (1.0F - easeInFactor * smoothFactor);
         }

         GL11.glTranslatef(x, y, 0.0F);
      }

   }

   private void startAnimationOut(float startX, float startY, float endX, float endY) {
      switch(this.animType) {
      case POP:
      case POP2:
         this.startScaling(this.getYMult(), startX, startY, endX, endY);
         break;
      case SLIDE:
         ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
         int screenWidth = sr.getScaledWidth() + 3;
         startY -= 100.0F;
         float easeOutFactor = 1.0F - (float)Math.pow((double)(1.0F - this.getYMult()), 2.0D);
         float x;
         if (startX > (float)(sr.getScaledWidth() / 2)) {
            x = ((float)screenWidth - startX) * (1.0F - easeOutFactor);
         } else {
            x = -endX * (1.0F - easeOutFactor);
         }

         float y;
         if (startY > (float)(sr.getScaledHeight() / 2)) {
            y = ((float)sr.getScaledHeight() - startY) * (1.0F - easeOutFactor);
         } else {
            y = -endY * (1.0F - easeOutFactor);
         }

         GL11.glTranslatef(x, y, 0.0F);
      }

   }

   private void stopAnimationOut(float startX, float startY, float endX, float endY) {
      switch(this.animType) {
      case POP:
      case POP2:
         this.stopScaling(this.getYMult(), startX, startY, endX, endY);
         break;
      case SLIDE:
         ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
         int screenWidth = sr.getScaledWidth() + 3;
         float x;
         if (startX > (float)(sr.getScaledWidth() / 2)) {
            x = ((float)screenWidth - startX) * (1.0F - this.getYMult());
         } else {
            x = -endX * (1.0F - this.getYMult());
         }

         GL11.glTranslatef(-x, 0.0F, 0.0F);
      }

   }

   private void startScaling(float mult, float startX, float startY, float endX, float endY) {
      float middleX = startX + (endX - startX) * 0.5F;
      float middleY = startY + (endY - startY) * 0.5F;
      float translateX = middleX - middleX * mult;
      float translateY = middleY - middleY * mult;
      GL11.glTranslatef(translateX, translateY, 1.0F);
      GL11.glScalef(mult, mult, 1.0F);
   }

   private void stopScaling(float mult, float startX, float startY, float endX, float endY) {
      float middleX = startX + (endX - startX) * 0.5F;
      float middleY = startY + (endY - startY) * 0.5F;
      float translateX = middleX - middleX * mult;
      float translateY = middleY - middleY * mult;
      GL11.glScalef(1.0F / mult, 1.0F / mult, 1.0F);
      GL11.glTranslatef(-translateX, -translateY, 1.0F);
   }

   public boolean isRendered() {
      return this.rendered;
   }

   public AnimationType getAnimType() {
      return this.animType;
   }

   public long getAnimDuration() {
      return this.animDuration;
   }

   public AnimationState getState() {
      return this.state;
   }

   public TimerUtil getTimer() {
      return this.timer;
   }

   public boolean isAnimDone() {
      return this.animDone;
   }

   public boolean isLastEnabled() {
      return this.lastEnabled;
   }

   public float getTimeElapsed() {
      return this.timeElapsed;
   }

   public void setAnimType(AnimationType animType) {
      this.animType = animType;
   }

   public void setAnimDuration(long animDuration) {
      this.animDuration = animDuration;
   }
}
