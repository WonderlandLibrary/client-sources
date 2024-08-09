package dev.darkmoon.client.utility.render;

import com.jhlabs.image.GaussianFilter;
import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class GlowUtility implements Utility {

    private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();

    public static void drawGlow(float x, float y, float width, float height, int glowRadius, int color) {

        BufferedImage original = null;
        GaussianFilter op = null;
        glPushMatrix();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        width = width + glowRadius * 2;
        height = height + glowRadius * 2;
        x = x - glowRadius;
        y = y - glowRadius;
        float _X = x - 0.25f;
        float _Y = y + 0.25f;
        int identifier = String.valueOf(width * height + width + 1000000000 * glowRadius + glowRadius).hashCode();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableBlend();
        int texId = -1;


        if (shadowCache.containsKey(identifier)) {
            texId = shadowCache.get(identifier);
            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }
            if (original == null) {
                original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            }

            Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillRect(glowRadius, glowRadius, (int) (width - glowRadius * 2), (int) (height - glowRadius * 2));
            g.dispose();

            if (op == null) {
                op = new GaussianFilter(glowRadius);
            }

            BufferedImage blurred = op.filter(original, null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            shadowCache.put(identifier, texId);
        }

        ColorUtility.setColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); // top left
        GL11.glVertex2f(_X, _Y);
        GL11.glTexCoord2f(0, 1); // bottom left
        GL11.glVertex2f(_X, _Y + height);
        GL11.glTexCoord2f(1, 1); // bottom right
        GL11.glVertex2f(_X + width, _Y + height);
        GL11.glTexCoord2f(1, 0); // top right
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }
    public static void drawGlow(float x, float y, float width, float height, int glowRadius, Color color) {
        BufferedImage original = null;
        GaussianFilter op = null;
        glPushMatrix();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        width = width + glowRadius * 2;
        height = height + glowRadius * 2;
        x = x - glowRadius;
        y = y - glowRadius;
        float _X = x - 0.25f;
        float _Y = y + 0.25f;
        int identifier = String.valueOf(width * height + width + 1000000000 * glowRadius + glowRadius).hashCode();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableBlend();
        int texId = -1;


        if (shadowCache.containsKey(identifier)) {
            texId = shadowCache.get(identifier);
            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }
            original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);

            Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillRect(glowRadius, glowRadius, (int) (width - glowRadius * 2), (int) (height - glowRadius * 2));
            g.dispose();

            op = new GaussianFilter(glowRadius);

            BufferedImage blurred = op.filter(original, null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            shadowCache.put(identifier, texId);
        }

        ColorUtility.setColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); // top left
        GL11.glVertex2f(_X, _Y);
        GL11.glTexCoord2f(0, 1); // bottom left
        GL11.glVertex2f(_X, _Y + height);
        GL11.glTexCoord2f(1, 1); // bottom right
        GL11.glVertex2f(_X + width, _Y + height);
        GL11.glTexCoord2f(1, 0); // top right
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }
}