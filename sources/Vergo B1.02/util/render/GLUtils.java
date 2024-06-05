package xyz.vergoclient.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import static org.lwjgl.opengl.GL11.*;

public enum GLUtils {

    INSTANCE;

    public Minecraft mc = Minecraft.getMinecraft();

    public void rescale(double factor) {
        rescale(mc.displayWidth / factor, mc.displayHeight / factor);
    }

    public void rescaleMC() {
        ScaledResolution resolution = new ScaledResolution(mc);
        rescale(mc.displayWidth / resolution.getScaleFactor(),mc.displayHeight / resolution.getScaleFactor());
    }

    public void rescale(double width, double height) {
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }


    public static void render(int mode, Runnable render) {
        glBegin(mode);
        render.run();
        glEnd();
    }

    public static void setup2DRendering(Runnable f) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        f.run();
        glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public static void rotate(float x, float y, float rotate, Runnable f) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.rotate(rotate, 0, 0, -1);
        GlStateManager.translate(-x, -y, 0);
        f.run();
        GlStateManager.popMatrix();
    }
}
