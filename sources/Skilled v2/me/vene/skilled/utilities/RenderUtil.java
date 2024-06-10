 package me.vene.skilled.utilities;

 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.util.AxisAlignedBB;
 import net.minecraft.util.ResourceLocation;
 import org.lwjgl.opengl.GL11;

public class RenderUtil
{
    public static void drawCheckmark(final float x, final float y, final int hexColor) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        hexColor(hexColor);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(1);
        GL11.glVertex2d((double)(x + 1.0f), (double)(y + 1.0f));
        GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
        GL11.glVertex2d((double)(x + 6.0f), (double)(y - 2.0f));
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void hexColor(final int hexColor) {
        final float red = (hexColor >> 16 & 0xFF) / 255.0f;
        final float green = (hexColor >> 8 & 0xFF) / 255.0f;
        final float blue = (hexColor & 0xFF) / 255.0f;
        final float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void hexESP(final int hexColor) {
        final float red = (hexColor >> 16 & 0xFF) / 255.0f;
        final float green = (hexColor >> 8 & 0xFF) / 255.0f;
        final float blue = (hexColor & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, 1.0f);
    }
    
    public static void drawTexturedRectangle(final ResourceLocation resourceLocation, final double posX, final double posY, final float width, final float height, final int color) {
        final float u = 1.0f;
        final float v = 1.0f;
        final float uWidth = 1.0f;
        final float vHeight = 1.0f;
        final float textureWidth = 1.0f;
        final float textureHeight = 1.0f;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        hexColor(color);
        GL11.glBegin(7);
        GL11.glTexCoord2d((double)(u / textureWidth), (double)(v / textureHeight));
        GL11.glVertex2d(posX, posY);
        GL11.glTexCoord2d((double)(u / textureWidth), (double)((v + vHeight) / textureHeight));
        GL11.glVertex2d(posX, posY + height);
        GL11.glTexCoord2d((double)((u + uWidth) / textureWidth), (double)((v + vHeight) / textureHeight));
        GL11.glVertex2d(posX + width, posY + height);
        GL11.glTexCoord2d((double)((u + uWidth) / textureWidth), (double)(v / textureHeight));
        GL11.glVertex2d(posX + width, posY);
        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB bb) {
        GL11.glBegin(1);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
    }
}
