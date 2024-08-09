package dev.excellent.impl.util.render;

import lombok.experimental.UtilityClass;
import net.mojang.blaze3d.platform.GlStateManager;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

@UtilityClass
public class GLUtils {

    public void enableDepth() {
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(true);
    }

    public void disableDepth() {
        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
    }

    public void startBlend() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void endBlend() {
        GlStateManager.disableBlend();
    }

    public void setup2DRendering(boolean blend) {
        if (blend) {
            startBlend();
        }
        GlStateManager.disableTexture();
    }

    public void setup2DRendering() {
        setup2DRendering(true);
    }

    public void end2DRendering() {
        GlStateManager.enableTexture();
        endBlend();
    }

    public void startRotate(float x, float y, float rotate) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0);
        GlStateManager.rotatef(rotate, 0, 0, -1);
        GlStateManager.translatef(-x, -y, 0);
    }

    public void endRotate() {
        GlStateManager.popMatrix();
    }

    public void scaleStart(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, 0);
        GlStateManager.scaled(scale, scale, 1);
        GlStateManager.translated(-x, -y, 0);
    }

    public void scaleEnd() {
        GlStateManager.popMatrix();
    }

    public void rotate(float x, float y, float rotate, Runnable runnable) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0);
        GlStateManager.rotatef(rotate, 0, 0, -1);
        GlStateManager.translatef(-x, -y, 0);
        runnable.run();
        GlStateManager.popMatrix();
    }

}