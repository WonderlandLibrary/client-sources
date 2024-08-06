package club.strifeclient.util.rendering;

import club.strifeclient.util.misc.MinecraftUtil;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

public final class RenderUtil extends MinecraftUtil {

    @Getter
    private static final Frustum frustum = new Frustum();

    public static boolean inBounds(float x, float y, float w, float h, int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= w && mouseY >= y && mouseY <= h);
    }

    public static boolean isHovered(float x, float y, float w, float h, int mouseX, int mouseY) {
        return inBounds(x, y, x + w, y + h, mouseX, mouseY);
    }

    public static Framebuffer updateFramebuffer(Framebuffer framebuffer, boolean depth) {
        if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
        }
        return framebuffer;
    }

    public static void startCropping(float x, float y, float width, float height, ScaledResolution scaledResolution) {
        glPushMatrix();
        glEnable(GL_SCISSOR_TEST);
        makeCropBox(x, y, width, height, scaledResolution);
    }

    public static void stopCropping() {
        glDisable(GL_SCISSOR_TEST);
        glPopMatrix();
    }

    public static void makeCropBox(float x, float y, float width, float height, ScaledResolution scaledResolution) {
        final int factor = scaledResolution.getScaleFactor();
        glScissor((int) x * factor, (int) (scaledResolution.getScaledHeight() - height) * factor, (int) (width - x) * factor, (int) ((height - y) * factor));
    }

    public static void makeCropBoxNoFactor(float x, float y, float width, float height, ScaledResolution scaledResolution) {
        // (0, 0) -> bottom left corner
        // glScissor does x, y, x + w, y + h, for you. we have to subtract them for width and height
        glScissor((int) x, (int) (scaledResolution.getScaledHeight() - height), (int) (width - x), (int) (height - y));
    }

    public static void scale(float x, float y, float scale, Runnable render) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        glScaled(scale, scale, 1);
        glTranslatef(-x, -y, 0);
        render.run();
        glPopMatrix();
    }

    public static void scaleCenter(float x, float y, float width, float height, float scale, Runnable render) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        glTranslatef(width / 2, height / 2, 0);
        glScaled(scale, scale, 1);
        glTranslatef(-x, -y, 0);
        glTranslatef(-width / 2, -height / 2, 0);
        render.run();
        glPopMatrix();
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        ColorUtil.doColor(color);
        glVertex2f(width, y);
        glVertex2f(x, y);
        glVertex2f(x, height);
        glVertex2f(width, height);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    // This method draws 3 points (for future reference)
    public static void drawOutlinedPoint(float x, float y, float size, float outlineWidth, int color, int backgroundColor) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_BLEND);
        glPointSize(size);
        glBegin(GL_POINTS);
        ColorUtil.doColor(color);
        glVertex2f(x, y);
        glEnd();
        glPointSize(size - outlineWidth);
        glBegin(GL_POINTS);
        ColorUtil.doColor(backgroundColor);
        glVertex2f(x, y);
        glEnd();
        glDisable(GL_BLEND);
        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    private static void drawImage(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0F).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos((x + width), y + height, 0.0F).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos((x + width), y, 0.0F).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0F).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
        drawImage(image, x, y, width, height, 255);
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height, float opacity) {
        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glDepthMask(false);
        glColor4f(1, 1, 1, opacity / 255);
        mc.getTextureManager().bindTexture(image);
        drawImage(x, y, 0, 0, width, height, width, height);
        glColor4f(1, 1, 1, 1);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

}
