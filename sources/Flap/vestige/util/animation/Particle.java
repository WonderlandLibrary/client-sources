package vestige.util.animation;

import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class Particle {
   private float x;
   private float y;
   private float speedX;
   private float speedY;
   private int size;
   private int screenWidth;
   private int screenHeight;
   private static final Random random = new Random();

   public Particle(int screenWidth, int screenHeight) {
      this.screenWidth = screenWidth;
      this.screenHeight = screenHeight;
      this.x = random.nextFloat() * (float)screenWidth;
      this.y = random.nextFloat() * (float)screenHeight;
      this.speedX = (random.nextFloat() - 0.8F) * 2.0F;
      this.speedY = (random.nextFloat() - 1.2F) * 2.0F;
      this.size = random.nextInt(2) + 1;
   }

   public void update() {
      this.x += this.speedX;
      this.y += this.speedY;
      if (this.x < 0.0F || this.x > (float)this.screenWidth) {
         this.speedX = -this.speedX;
      }

      if (this.y < 0.0F || this.y > (float)this.screenHeight) {
         this.speedY = -this.speedY;
      }

   }

   public void render(Minecraft mc) {
      this.drawCircle(this.x, this.y, (float)this.size, -1);
   }

   private void drawCircle(float cx, float cy, float r, int color) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
      GL11.glBegin(6);

      for(int i = 0; i <= 360; ++i) {
         double angle = Math.toRadians((double)i);
         GL11.glVertex2d((double)cx + Math.sin(angle) * (double)r, (double)cy + Math.cos(angle) * (double)r);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }
}
