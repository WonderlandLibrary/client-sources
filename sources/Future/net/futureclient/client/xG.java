package net.futureclient.client;

import net.futureclient.loader.mixin.common.render.wrapper.IRenderManager;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Rectangle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import java.awt.Color;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

public class xG
{
    private static final Minecraft D;
    private static ScaledResolution k;
    
    public xG() {
        super();
    }
    
    static {
        D = Minecraft.getMinecraft();
        xG.k = new ScaledResolution(xG.D);
    }
    
    public static void B(final AxisAlignedBB axisAlignedBB) {
        if (axisAlignedBB == null) {
            return;
        }
        GL11.glBegin(3);
        final int n = 1;
        final int n2 = 3;
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n2);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static void B() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void B(final float n, final float n2, final float n3, final float n4, final int n5, final int n6) {
        b();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        M(n5);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        M(n6);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        M();
    }
    
    public static void C(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer;
        final BufferBuilder bufferBuilder3;
        final BufferBuilder bufferBuilder2;
        final BufferBuilder bufferBuilder = bufferBuilder2 = (bufferBuilder3 = (buffer = instance.getBuffer()));
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
        bufferBuilder2.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder2.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder3.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        final BufferBuilder bufferBuilder4 = bufferBuilder;
        final BufferBuilder bufferBuilder5 = bufferBuilder;
        final BufferBuilder bufferBuilder6 = bufferBuilder;
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
        bufferBuilder6.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder6.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder5.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder4.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder4.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        final BufferBuilder bufferBuilder7 = bufferBuilder;
        final BufferBuilder bufferBuilder8 = bufferBuilder;
        final BufferBuilder bufferBuilder9 = bufferBuilder;
        final BufferBuilder bufferBuilder10 = bufferBuilder;
        final BufferBuilder bufferBuilder11 = bufferBuilder;
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder11.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder11.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        bufferBuilder9.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        bufferBuilder9.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder8.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder7.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        bufferBuilder7.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    public static void C() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void b(float n, float n2, float n3, float n4, final int n5, final int n6) {
        b();
        n *= 2.0f;
        n3 *= 2.0f;
        n2 *= 2.0f;
        n4 *= 2.0f;
        final float n7 = 0.5f;
        final float n8 = 0.5f;
        GL11.glScalef(n8, n7, n8);
        M(n, n2, n4 - 1.0f, n6);
        M(n3 - 1.0f, n2, n4, n6);
        e(n, n3 - 1.0f, n2, n6);
        e(n, n3 - 2.0f, n4 - 1.0f, n6);
        M(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n5);
        final float n9 = 2.0f;
        final int n10 = 2;
        GL11.glScalef((float)n10, n9, (float)n10);
        M();
    }
    
    public static void b(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer;
        final BufferBuilder bufferBuilder = buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        bufferBuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        instance.draw();
    }
    
    public static void b() {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void e(final AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static void e(final float n, final float n2, final float n3, final float n4, final int n5, final int n6) {
        b();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        M(n5);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4);
        M(n6);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        M();
    }
    
    public static void e(final Color color) {
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), (double)(color.getAlpha() / 255.0f));
    }
    
    public static void e(final float n, final float n2, final float n3, final float n4) {
        e();
        final int scaleFactor = xG.k.getScaleFactor();
        GL11.glScissor((int)(n * scaleFactor), (int)((xG.k.getScaledHeight() - n4) * scaleFactor), (int)((n3 - n) * scaleFactor), (int)((n4 - n2) * scaleFactor));
    }
    
    public static void e(float n, float n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = n;
            n = n2;
            n2 = n5;
        }
        M(n, n3, n2 + 1.0f, n3 + 1.0f, n4);
    }
    
    public static void e() {
        xG.k = new ScaledResolution(xG.D);
    }
    
    public static void e(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        b();
        M(n6);
        M(n + n5, n2 + n5, n3 - n5, n4 - n5);
        M(n7);
        M(n + n5, n2, n3 - n5, n2 + n5);
        M(n, n2, n + n5, n4);
        M(n3 - n5, n2, n3, n4);
        M(n + n5, n4 - n5, n3 - n5, n4);
        M();
    }
    
    public static void e(float n, float n2, final float n3, final int n4, final int n5) {
        if (n2 < n) {
            final float n6 = n;
            n = n2;
            n2 = n6;
        }
        B(n, n3, n2 + 1.0f, n3 + 1.0f, n4, n5);
    }
    
    public static void M(final float n) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        xG.D.entityRenderer.enableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(n);
    }
    
    public static Vec3d M(final Entity entity, final Vec3d vec3d) {
        return M(entity, vec3d.x, vec3d.y, vec3d.z);
    }
    
    public static Color M(final Color color, final Color color2, final float n) {
        final float n2 = 1.0f - n;
        final float[] array = new float[3];
        final float[] array2 = new float[3];
        color.getColorComponents(array);
        color2.getColorComponents(array2);
        return new Color(array[0] * n + array2[0] * n2, array[1] * n + array2[1] * n2, array[2] * n + array2[2] * n2);
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7, final int n8) {
        b();
        B(n, n2, n3, n4, n8, n7);
        M(n6);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        M();
    }
    
    public static ScaledResolution M() {
        return xG.k;
    }
    
    public static void M(final float n, final int n2, final int n3, final int n4) {
        GL11.glColor4f(0.003921569f * n2, 0.003921569f * n3, 0.003921569f * n4, n);
    }
    
    public static void M(final Rectangle rectangle, final int n) {
        M((float)rectangle.x, (float)rectangle.y, (float)(rectangle.x + rectangle.width), (float)(rectangle.y + rectangle.height), n);
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int n7) {
        final float n8 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n7 & 0xFF) / 255.0f;
        final float n11 = (n7 >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        final Tessellator instance;
        final BufferBuilder buffer = (instance = Tessellator.getInstance()).getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n8, n9, n10, n11);
        buffer.begin(6, DefaultVertexFormats.POSITION_TEX_COLOR);
        Tessellator tessellator;
        if ((n2 <= n4 || n >= n5) && (n2 >= n4 || n <= n5)) {
            buffer.pos(n, n2, 0.0);
            buffer.pos(n3, n4, 0.0);
            buffer.pos(n5, n6, 0.0);
            tessellator = instance;
        }
        else {
            buffer.pos(n5, n6, 0.0);
            buffer.pos(n3, n4, 0.0);
            buffer.pos(n, n2, 0.0);
            tessellator = instance;
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        final float n12 = 1.0f;
        final int n13 = 1;
        GlStateManager.color((float)n13, (float)n13, n12, (float)n13);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7, final int n8, final int n9) {
        M(n, n2, n3, n4, n5, n6, n9);
        final float n10 = (n8 >> 24 & 0xFF) / 255.0f;
        final float n11 = (n8 >> 16 & 0xFF) / 255.0f;
        final float n12 = (n8 >> 8 & 0xFF) / 255.0f;
        final float n13 = (n8 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.pushMatrix();
        GlStateManager.color(n11, n12, n13, n10);
        GL11.glLineWidth(n7);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n5, n6);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d(n5, n6);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4, final float n5) {
        GL11.glDisable(3553);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n3, n4);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n5 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 24 & 0xFF) / 255.0f;
        final Tessellator instance;
        final BufferBuilder buffer = (instance = Tessellator.getInstance()).getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n6, n7, n8, n9);
        final BufferBuilder bufferBuilder = buffer;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(n, n4, 0.0);
        buffer.pos(n3, n4, 0.0);
        buffer.pos(n3, n2, 0.0);
        buffer.pos(n, n2, 0.0);
        instance.draw();
        GlStateManager.enableTexture2D();
        final float n10 = 1.0f;
        final int n11 = 1;
        GlStateManager.color((float)n11, (float)n11, n10, (float)n11);
        GlStateManager.disableBlend();
    }
    
    public static float[] M(final int n) {
        return new float[] { (n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f };
    }
    
    public static double M(final double n, final double n2) {
        return n2 + (n - n2) * ((IMinecraft)xG.D).getTimer().renderPartialTicks;
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final float n5, final int n6, final int n7, final int n8) {
        b();
        GL11.glPushMatrix();
        M(n6);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        M(n, n2, n3, n4, n7, n8);
        M();
    }
    
    public static void M(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void M(final int n, final int n2, final int n3, final int n4, final int n5) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f((n5 >> 16 & 0xFF) / 255.0f, (n5 >> 8 & 0xFF) / 255.0f, (n5 & 0xFF) / 255.0f, (n5 >> 24 & 0xFF) / 255.0f);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7425);
        switch (n3) {
            case 0:
                GL11.glBegin(2);
                GL11.glVertex2d((double)n, (double)(n2 + n4));
                GL11.glVertex2d((double)(n + n4), (double)(n2 - n4));
                GL11.glVertex2d((double)(n - n4), (double)(n2 - n4));
                GL11.glEnd();
                GL11.glBegin(4);
                GL11.glVertex2d((double)n, (double)(n2 + n4));
                GL11.glVertex2d((double)(n + n4), (double)(n2 - n4));
                GL11.glVertex2d((double)(n - n4), (double)(n2 - n4));
                GL11.glEnd();
                break;
            case 1:
                GL11.glBegin(2);
                GL11.glVertex2d((double)n, (double)n2);
                GL11.glVertex2d((double)n, (double)(n2 + n4 / 2));
                GL11.glVertex2d((double)(n + n4 + n4 / 2), (double)n2);
                GL11.glEnd();
                GL11.glBegin(4);
                GL11.glVertex2d((double)n, (double)n2);
                GL11.glVertex2d((double)n, (double)(n2 + n4 / 2));
                GL11.glVertex2d((double)(n + n4 + n4 / 2), (double)n2);
                GL11.glEnd();
            case 3:
                GL11.glBegin(2);
                GL11.glVertex2d((double)n, (double)n2);
                GL11.glVertex2d(n + n4 * 0.0, (double)(n2 - n4 / 2));
                GL11.glVertex2d(n + n4 * 0.0, (double)(n2 + n4 / 2));
                GL11.glEnd();
                GL11.glBegin(4);
                GL11.glVertex2d(n + n4 * 0.0, (double)(n2 - n4 / 2));
                GL11.glVertex2d((double)n, (double)n2);
                GL11.glVertex2d(n + n4 * 0.0, (double)(n2 + n4 / 2));
                GL11.glEnd();
                break;
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void M(float n, float n2, float n3, float n4, final int n5, final int n6) {
        b();
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        final float n7 = 0.5f;
        final float n8 = 0.5f;
        GL11.glScalef(n8, n7, n8);
        M(n, n2 + 1.0f, n4 - 2.0f, n5);
        M(n3 - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        e(n + 2.0f, n3 - 3.0f, n2, n5);
        e(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        e(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        e(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        e(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        e(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        M(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        final float n9 = 2.0f;
        final int n10 = 2;
        GL11.glScalef((float)n10, n9, (float)n10);
        M();
    }
    
    public static void M(final AxisAlignedBB axisAlignedBB) {
        if (axisAlignedBB == null) {
            return;
        }
        GL11.glBegin(7);
        final int n = 7;
        final int n2 = 7;
        final int n3 = 7;
        final int n4 = 7;
        final int n5 = 7;
        final int n6 = 7;
        final int n7 = 7;
        final int n8 = 7;
        final int n9 = 7;
        final int n10 = 7;
        final int n11 = 7;
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n11);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n10);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n9);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n8);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n7);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n6);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n5);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(n4);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n3);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n2);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(n);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glEnd();
    }
    
    public static void M(final float n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        M(n, n2 + 1.0f, n + 1.0f, n3, n4);
    }
    
    public static void M(final Rectangle rectangle, final float n, final int n2, final int n3) {
        final float n4 = (float)rectangle.x;
        final float n5 = (float)rectangle.y;
        final float n6 = (float)(rectangle.x + rectangle.width);
        final float n7 = (float)(rectangle.y + rectangle.height);
        b();
        M(n2);
        M(n4 + n, n5 + n, n6 - n, n7 - n);
        M(n3);
        final float n8 = n4 + 1.0f;
        final float n9 = n6 - 1.0f;
        final float n10 = n5;
        M(n8, n10, n9, n10 + n);
        final float n11 = n5;
        final float n12 = n4;
        M(n12, n11, n12 + n, n7);
        M(n6 - n, n5, n6, n7);
        M(n4 + 1.0f, n7 - n, n6 - 1.0f, n7);
        M();
    }
    
    public static void M(float n, float n2, float n3, final int n4, int n5) {
        n3 *= 2.0f;
        n *= 2.0f;
        n2 *= 2.0f;
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        final float n11;
        final float n10 = (float)Math.cos(n11 = (float)(6.388667265E-315 / n4));
        final float n12 = (float)Math.sin(n11);
        float n13 = n3;
        float n14 = 0.0f;
        b();
        final float n15 = 0.5f;
        final float n16 = 0.5f;
        GL11.glScalef(n16, n15, n16);
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(2);
        int i = 0;
        n5 = 0;
        while (i < n4) {
            GL11.glVertex2f(n13 + n, n14 + n2);
            n3 = n13;
            n13 = n10 * n13 - n12 * n14;
            final float n17 = n12 * n3;
            final float n18 = n10;
            final float n19 = n14;
            ++n5;
            n14 = n17 + n18 * n19;
            i = n5;
        }
        GL11.glEnd();
        final float n20 = 2.0f;
        final int n21 = 2;
        GL11.glScalef((float)n21, n20, (float)n21);
        M();
    }
    
    public static Color M(final long n, final float n2) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public static void M(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static String M(final int n) {
        return ARBShaderObjects.glGetInfoLogARB(n, ARBShaderObjects.glGetObjectParameteriARB(n, 35716));
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        b();
        M(n, n2, n3, n4, n6);
        M(n7);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(n5);
        GL11.glBegin(3);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        M();
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        b();
        GL11.glColor4f(n5, n6, n7, n8);
        M(n, n2, n3, n4);
        M();
    }
    
    public static void M(final double n, final double n2, final double n3, final double n4, final int n5, final int n6) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        M(n5);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        M(n6);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void M(final int n, int n2, float n3, final double n4, final float n5, final float n6, final int n7) {
        final float n8 = (n7 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n7 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double)n, (double)n2, 0.0);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n3);
        if (n4 > 0.0) {
            GL11.glBegin(3);
            int n12 = 0;
            n2 = 0;
            while (n12 < n4) {
                final float n13 = (float)(Math.cos(n3 = (float)(n2 * (n4 * 6.984873503E-315 / n5))) * n6);
                final float n14 = (float)(Math.sin(n3) * n6);
                ++n2;
                GL11.glVertex2f(n13, n14);
                n12 = n2;
            }
            GL11.glEnd();
        }
        if (n4 < 0.0) {
            GL11.glBegin(3);
            int n15 = 0;
            n2 = 0;
            while (n15 > n4) {
                final float n16 = (float)(Math.cos(n3 = (float)(n2 * (n4 * 6.984873503E-315 / n5))) * -n6);
                final float n17 = (float)(Math.sin(n3) * -n6);
                --n2;
                GL11.glVertex2f(n16, n17);
                n15 = n2;
            }
            GL11.glEnd();
        }
        M();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public static int M(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final boolean b, final boolean b2) {
        GL11.glBindTexture(3553, n);
        GL11.glTexParameteri(3553, 10241, b ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10240, b ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10242, b2 ? 10497 : 10496);
        GL11.glTexParameteri(3553, 10243, b2 ? 10497 : 10496);
        GL11.glPixelStorei(3317, 1);
        GL11.glTexImage2D(3553, 0, 32856, n2, n3, 0, 6408, 5121, byteBuffer);
        return n;
    }
    
    public static Vec3d M(final Entity entity) {
        return new Vec3d(M(entity.posX, entity.lastTickPosX) - ((IRenderManager)xG.D.getRenderManager()).getRenderPosX(), M(entity.posY, entity.lastTickPosY) - ((IRenderManager)xG.D.getRenderManager()).getRenderPosY(), M(entity.posZ, entity.lastTickPosZ) - ((IRenderManager)xG.D.getRenderManager()).getRenderPosZ());
    }
    
    public static Vec3d M(final Entity entity, final double n) {
        return M(entity, n, n, n);
    }
    
    public static void M() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static Vec3d M(final Entity entity, final double n, final double n2, final double n3) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * n, (entity.posY - entity.lastTickPosY) * n2, (entity.posZ - entity.lastTickPosZ) * n3);
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4) {
        GL11.glBegin(7);
        GL11.glVertex2f(n, n4);
        GL11.glVertex2f(n3, n4);
        GL11.glVertex2f(n3, n2);
        GL11.glVertex2f(n, n2);
        GL11.glEnd();
    }
    
    public static void M(final float n, final float n2, final float n3, final float n4, final int n5) {
        b();
        M(n5);
        M(n, n2, n3, n4);
        M();
    }
    
    public static Vec3d M(final Entity entity, final float n) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(M(entity, (double)n));
    }
    
    public static void M(int n, int n2, double n3, int n4) {
        n3 *= 0.0;
        n *= 2;
        n2 *= 2;
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        b();
        final float n9 = 0.5f;
        final float n10 = 0.5f;
        GL11.glScalef(n10, n9, n10);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(6);
        int i = 0;
        n4 = 0;
        while (i <= 360) {
            final double n11 = Math.sin(n4 * 6.984873503E-315 / 0.0) * n3;
            final double n12 = Math.cos(n4 * 6.984873503E-315 / 0.0) * n3;
            final double n13 = n + n11;
            final double n14 = n2;
            ++n4;
            GL11.glVertex2d(n13, n14 + n12);
            i = n4;
        }
        GL11.glEnd();
        final float n15 = 2.0f;
        final int n16 = 2;
        GL11.glScalef((float)n16, n15, (float)n16);
        M();
    }
    
    public static void M(final Entity entity, final int n) {
        GL11.glPushMatrix();
        GL11.glTranslated(entity.posX - ((IRenderManager)xG.D.getRenderManager()).getRenderPosX(), entity.posY - ((IRenderManager)xG.D.getRenderManager()).getRenderPosY() + entity.height / 2.0f, entity.posZ - ((IRenderManager)xG.D.getRenderManager()).getRenderPosZ());
        final float n2 = 1.0f;
        final float n3 = 0.0f;
        GL11.glNormal3f(n3, n2, n3);
        final float n4 = -xG.D.player.rotationYaw;
        final float n5 = 1.0f;
        final float n6 = 0.0f;
        GL11.glRotatef(n4, n6, n5, n6);
        GL11.glRotatef(xG.D.player.rotationPitch, 1.0f, 0.0f, (float)0);
        xG.D.entityRenderer.disableLightmap();
        GL11.glDisable(2929);
        final float n7 = 0.2f;
        final int n8 = 2929;
        WG.M(-entity.width, -entity.height + n7 * 3.0f, entity.width, entity.height - n7 * 3.0f, 0x55000000 | n);
        GL11.glEnable(n8);
        xG.D.entityRenderer.enableLightmap();
        GL11.glPopMatrix();
    }
    
    public static void M(final float n, final float n2, final double n3, final Color color) {
        final int n4 = 3553;
        M(color);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glDisable(n4);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.001f);
        final Tessellator instance;
        final BufferBuilder buffer = (instance = Tessellator.getInstance()).getBuffer();
        double n6;
        double n5 = n6 = 0.0;
        while (n5 < 0.0) {
            final double n7 = n6 * 6.984873503E-315 / 0.0;
            final double n8 = (n6 - 1.0) * 6.984873503E-315 / 0.0;
            final double[] array = { Math.cos(n7) * n3, -Math.sin(n7) * n3, Math.cos(n8) * n3, -Math.sin(n8) * n3 };
            final double n9 = n6;
            final Tessellator tessellator = instance;
            final BufferBuilder bufferBuilder = buffer;
            final BufferBuilder bufferBuilder2 = buffer;
            buffer.begin(6, DefaultVertexFormats.POSITION_TEX);
            bufferBuilder2.pos(n + array[2], n2 + array[3], 0.0).endVertex();
            bufferBuilder.pos(n + array[0], n2 + array[1], 0.0).endVertex();
            bufferBuilder.pos((double)n, (double)n2, 0.0).endVertex();
            tessellator.draw();
            n5 = (n6 = n9 + 1.0);
        }
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.disableAlpha();
        GL11.glEnable(3553);
    }
    
    public static int M(final String s, final int n) throws Exception {
        int glCreateShaderObjectARB = 0;
        try {
            if ((glCreateShaderObjectARB = ARBShaderObjects.glCreateShaderObjectARB(n)) == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(glCreateShaderObjectARB, (CharSequence)s);
            ARBShaderObjects.glCompileShaderARB(glCreateShaderObjectARB);
            if (ARBShaderObjects.glGetObjectParameteriARB(glCreateShaderObjectARB, 35713) == 0) {
                throw new RuntimeException(new StringBuilder().insert(0, "Error creating shader: ").append(M(glCreateShaderObjectARB)).toString());
            }
            return glCreateShaderObjectARB;
        }
        catch (Exception ex) {
            ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
            throw ex;
        }
    }
}
