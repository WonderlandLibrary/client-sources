package Reality.Realii.utils.particlesystem.particle;

import Reality.Realii.utils.render.RenderUtil;

import java.awt.*;
import java.util.Random;

public class Particle {
   private Random random = new Random();
   public float x;
   public float y;
   public float radius;
   private boolean grow;
   private double max;
   private double min;
   public State state;
   public int[] pos;

   public Particle(int x, int y) {
      this.radius = (float)(this.random.nextInt(2) + 1);
      this.max = 0.5D;
      this.min = 1.0D;
      this.state = State.ONE;
      this.pos = new int[2];
      this.x = (float)x;
      this.y = (float)y;
      this.grow = this.radius < 2.0F;
      this.pos[0] = x;
      this.pos[1] = y;
   }

   public void drawScreen(int mouseX, int mouseY, int height) {
      Color white = new Color(255, 255, 255,120);
      this.changeRadius();
      this.changePos(height);
      RenderUtil.smoothCircle(this.x, this.y, this.radius, white);
      RenderUtil.drawLine(this.x, this.y - 1.8F, this.x, this.y + 1.8F, 0.3f);
      RenderUtil.drawLine(this.x - 0.2F, this.y - 1.8F, this.x, this.y - 1.8F,0.3f);
      RenderUtil.drawLine(this.x - 1.8F, this.y - 0.8F, this.x + 1.8F, this.y + 0.8F, 0.3f);
      RenderUtil.drawLine(this.x - 1.8F, this.y + 0.8F, this.x + 1.8F, this.y - 0.8F, 0.3f);
   }

   private void changePos(int height) {
//      this.x += this.random.nextFloat() - 0.5F;
//      this.y = (float)((double)this.y + 1.2D);
//      if (this.y > (float)height) {
//         this.y = 0.0F;
//      }

   }

   private void changeRadius() {
      if (this.grow) {
         this.radius = (float)((double)this.radius + 0.1D);
         if ((double)this.radius > this.max) {
            this.grow = !this.grow;
         }
      } else {
         this.radius = (float)((double)this.radius - 0.1D);
         if ((double)this.radius < this.min) {
            this.grow = !this.grow;
         }
      }

   }

   private static enum State {
      ONE,
      TWO,
      THREE;
   }
}
