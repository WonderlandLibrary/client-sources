package net.augustus.utils;

import net.augustus.utils.interfaces.MC;

public class AnimationUtil implements MC {
   private float x;
   private float minX;
   private float maxX;
   private float speed;
   private long lastTime;
   private int side;

   public AnimationUtil(float xy, float minXY, float maxXY, float speed) {
      this.x = xy;
      this.minX = minXY;
      this.maxX = maxXY;
      this.speed = speed;
   }

   public float updateAnimation(int side) {
      long deltaTime = System.currentTimeMillis() - this.lastTime;
      float sx = 0.0F;
      if (this.speed != 0.0F) {
         float var1 = this.speed / (float)deltaTime;
         sx = (this.maxX - this.minX) / var1;
      }

      if (this.side == 0) {
         this.lastTime = System.currentTimeMillis();
         return this.x;
      } else {
         if (this.side > 0) {
            this.side = 1;
         } else {
            this.side = -1;
         }

         float cxy = this.x + sx * (float)side;
         if (cxy < this.minX) {
            cxy = this.minX;
         } else if (cxy > this.maxX) {
            cxy = this.maxX;
         }

         this.x = cxy;
         this.lastTime = System.currentTimeMillis();
         return this.x;
      }
   }

   public float updateAnimation(float minXY, float maxXY) {
      this.minX = minXY;
      this.maxX = maxXY;
      long deltaTime = System.currentTimeMillis() - this.lastTime;
      if (deltaTime > 60L) {
         deltaTime = 60L;
      }

      float sx = 0.0F;
      if (this.speed != 0.0F) {
         float var1 = this.speed / (float)deltaTime;
         sx = (this.maxX - this.minX) / var1;
         if (this.side == 0) {
            this.lastTime = System.currentTimeMillis();
            return this.x;
         } else {
            if (this.side > 0) {
               this.side = 1;
            } else {
               this.side = -1;
            }

            var1 = this.x + sx * (float)this.side;
            if (var1 < minXY) {
               var1 = minXY;
            } else if (var1 > maxXY) {
               var1 = maxXY;
            }

            this.x = var1;
            this.lastTime = System.currentTimeMillis();
            return this.x;
         }
      } else {
         return this.x;
      }
   }

   public void setSide(int side) {
      this.side = side;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }
}
