 package me.vene.skilled.utilities;
 
 import javax.vecmath.Vector2f;
 import javax.vecmath.Vector4f;
 import net.minecraft.client.renderer.GlStateManager;
 import org.lwjgl.opengl.GL11;
 
public class DrawUtil
{
    public static void drawCircleFilled(final float radius, final Vector2f center, final Vector4f color) {
        prepare();
        GL11.glBegin(6);
        GL11.glColor4f(color.x, color.y, color.z, color.w);
        GL11.glVertex2f(center.x, center.y);
        for (int i = 0; i < 720; ++i) {
            final double th = (float)(-i) * 3.141592653589793 / 360.0;
            final float x = (float)(Math.cos(th) * radius);
            final float y = (float)(Math.sin(th) * radius);
            GL11.glVertex2f(center.x + x, center.y + y);
        }
        GL11.glEnd();
        end();
    }
    
    public static void drawCircleEmpty(final float radius, final Vector2f center, final Vector4f color) {
        prepare();
        GL11.glBegin(2);
        GL11.glColor4f(color.x, color.y, color.z, color.w);
        for (int i = 0; i < 720; ++i) {
            final double th = (float)(-i) * 3.141592653589793 / 360.0;
            final float x = (float)(Math.cos(th) * radius);
            final float y = (float)(Math.sin(th) * radius);
            GL11.glVertex2f(center.x + x, center.y + y);
        }
        GL11.glEnd();
        end();
    }
    
    public static void drawSector(final float radius, final Vector2f center, final Vector4f color, final double startAngle, final double endAngle) {
        prepare();
        GL11.glBegin(6);
        GL11.glColor4f(color.x, color.y, color.z, color.w);
        GL11.glVertex2f(center.x, center.y);
        final float dtheta = (float)(endAngle - startAngle);
        for (int i = 0; i < 720; ++i) {
            final double th = -i * dtheta / 720.0f - startAngle;
            final float x = (float)(Math.cos(th) * radius);
            final float y = (float)(Math.sin(th) * radius);
            GL11.glVertex2f(center.x + x, center.y + y);
        }
        GL11.glEnd();
        end();
    }
    
    public static void drawArc(final float radius, final Vector2f center, final Vector4f color, final double startAngle, final double endAngle) {
        prepare();
        GL11.glBegin(3);
        GL11.glColor4f(color.x, color.y, color.z, color.w);
        final float dtheta = (float)(endAngle - startAngle);
        for (int i = 0; i < 720; ++i) {
            final double th = -i * dtheta / 720.0f - startAngle;
            final float x = (float)(Math.cos(th) * radius);
            final float y = (float)(Math.sin(th) * radius);
            GL11.glVertex2f(center.x + x, center.y + y);
        }
        GL11.glEnd();
        end();
    }
    
    public static void drawRect(final Vector2f pos, final float width, final float height, final Vector4f color) {
        prepare();
        GL11.glBegin(7);
        GL11.glColor4f(color.x, color.y, color.z, color.w);
        GL11.glVertex2f(pos.x + width, pos.y + height);
        GL11.glVertex2f(pos.x + width, pos.y);
        GL11.glVertex2f(pos.x, pos.y);
        GL11.glVertex2f(pos.x, pos.y + height);
        GL11.glEnd();
        end();
    }
    
    public static void drawRoundedRect(final Vector2f pos, final float width, final float height, final float radius, final Vector4f color) {
        drawRect(new Vector2f(pos.x + radius, pos.y), width - 2.0f * radius, height, color);
        drawRect(new Vector2f(pos.x, pos.y + radius), width, height - 2.0f * radius, color);
        drawSector(radius, new Vector2f(pos.x + radius, pos.y + radius), color, 1.5707963267948966, 3.141592653589793);
        drawSector(radius, new Vector2f(pos.x - radius + width, pos.y + radius), color, 0.0, 1.5707963267948966);
        drawSector(radius, new Vector2f(pos.x - radius + width, pos.y - radius + height), color, 4.71238898038469, 6.283185307179586);
        drawSector(radius, new Vector2f(pos.x + radius, pos.y - radius + height), color, 3.141592653589793, 4.71238898038469);
    }
    
    private static void prepare() {
        GlStateManager.disableTexture2D();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }
    
    private static void end() {
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
        GlStateManager.enableTexture2D();
    }
}
