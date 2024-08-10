package cc.slack.ui.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import java.util.Random;

public class Particle {
    private float x, y, speedX, speedY;

    private int size;
    private int screenWidth, screenHeight;
    private static final Random random = new Random();

    public Particle(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = random.nextFloat() * screenWidth;
        this.y = random.nextFloat() * screenHeight;
        this.speedX = (random.nextFloat() - 0.8f) * 2;
        this.speedY = (random.nextFloat() - 1.2f) * 2;
        this.size = random.nextInt(2) + 1;
    }

    public void update() {
        this.x += speedX;
        this.y += speedY;

        if (x < 0 || x > screenWidth) {
            speedX = -speedX;


        }
        if (y < 0 || y > screenHeight) {
            speedY = -speedY;
        }
    }

    public void render(Minecraft mc) {
        drawCircle(x, y, size, 0xFFFFFFFF);
    }

    private void drawCircle(float cx, float cy, float r, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        for(int i = 0; i <= 360; i++) {
            double angle = Math.toRadians(i);
            GL11.glVertex2d(cx + Math.sin(angle) * r, cy + Math.cos(angle) * r);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);


        GL11.glPopMatrix();
    }
}
