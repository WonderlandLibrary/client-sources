package xyz.cucumber.base.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import xyz.cucumber.base.utils.RenderUtils;

public class Particle {
   private double x;
   private double y;
   private double speed;
   private int size;
   private int color;
   private float yaw;
   private float time;
   private int timeFinal;

   public Particle(double x, double y, double speed, int size, int color, float yaw, float time) {
      this.x = x;
      this.y = y;
      this.speed = speed;
      this.size = size;
      this.color = color;
      this.yaw = yaw;
      this.time = (float)System.nanoTime() / 1000000.0F + time;
      this.timeFinal = (int)time;
   }

   public boolean draw() {
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
      if (!(this.time < (float)System.nanoTime() / 1000000.0F)
         && !(this.x < 0.0)
         && !(this.y < 0.0)
         && !(this.x > (double)sr.getScaledWidth())
         && !(this.y > (double)sr.getScaledHeight())) {
         Color c = new Color(this.color);
         int alpha = (int)(255.0F / (float)this.timeFinal * (float)((int)(this.time - (float)System.nanoTime() / 1000000.0F)));
         if (alpha < 0) {
            return true;
         } else {
            if (alpha > 255) {
               alpha = 255;
            }

            int finalc = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha).getRGB();
            this.update();
            RenderUtils.drawPoint(this.x, this.y, finalc, (float)this.size);
            return false;
         }
      } else {
         return true;
      }
   }

   public void update() {
      this.x = this.x + Math.sin(Math.toRadians((double)this.yaw)) * this.speed;
      this.y = this.y + Math.cos(Math.toRadians((double)this.yaw)) * this.speed;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getSpeed() {
      return this.speed;
   }

   public void setSpeed(double speed) {
      this.speed = speed;
   }

   public int getSize() {
      return this.size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(int color) {
      this.color = color;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }
}
