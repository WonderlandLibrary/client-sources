package ClassSub;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.shader.*;
import cn.Hanabi.injection.interfaces.*;

public class Class246
{
    public static float delta;
    
    
    public static double interpolate(final double n, final double n2) {
        return n2 + (n - n2) * Class211.getTimer().renderPartialTicks;
    }
    
    public static void pre() {
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }
    
    public static void post() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glColor3d(1.0, 1.0, 1.0);
    }
    
    public static void drawArc(float n, float n2, double n3, final int n4, final int n5, final double n6, final int n7) {
        n3 *= 2.0;
        n *= 2.0f;
        n2 *= 2.0f;
        final float n8 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n4 & 0xFF) / 255.0f;
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth((float)n7);
        GL11.glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glBegin(3);
        for (int n12 = n5; n12 <= n6; ++n12) {
            GL11.glVertex2d(n + Math.sin(n12 * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(n12 * 3.141592653589793 / 180.0) * n3);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static int rainbow(final int n) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() + n) / 20.0) % 360.0 / 360.0), 0.8f, 0.7f).brighter().getRGB();
    }
    
    public static Color rainbowEffect(final int n) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB((System.nanoTime() + n) / 2.0E10f % 1.0f, 1.0f, 1.0f))), 16));
        return new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void drawFullscreenImage(final ResourceLocation resourceLocation) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight());
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawPlayerHead(final String s, final int n, final int n2, final int n3, final int n4) {
        for (final EntityPlayer entityPlayer : Minecraft.getMinecraft().theWorld.playerEntities) {
            if (!s.equalsIgnoreCase(entityPlayer.getName())) {
                continue;
            }
            final NetworkPlayerInfo networkPlayerInfo = new NetworkPlayerInfo(new GameProfile(entityPlayer.getUniqueID(), entityPlayer.getName()));
            final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glDepthMask(false);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
            Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
        }
    }
    
    public static double getAnimationState(double n, final double n2, final double n3) {
        final float n4 = (float)(Class246.delta * n3);
        if (n < n2) {
            if (n + n4 < n2) {
                n += n4;
            }
            else {
                n = n2;
            }
        }
        else if (n - n4 > n2) {
            n -= n4;
        }
        else {
            n = n2;
        }
        return n;
    }
    
    public static void drawLoadingCircle() {
        final float n = (float)(System.currentTimeMillis() * 0.1 % 400.0);
        final float n2 = 0.5f;
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final float n3 = scaledResolution.getScaledWidth() / 16.0f;
        drawCircle(scaledResolution.getScaledWidth() / 2.0f, scaledResolution.getScaledHeight() / 2.0f, n3, new Color(Class320.DARK_GREY.c), 5.0f, 0.0f, 1.0f);
        drawCircle(scaledResolution.getScaledWidth() / 2.0f, scaledResolution.getScaledHeight() / 2.0f, n3, new Color(Class320.BLUE.c), 7.0f, n, n2);
    }
    
    public static void drawCircle(final int n, final int n2, final int n3, final Color color) {
        final int n4 = 70;
        final double n5 = 6.283185307179586 / n4;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int i = 0; i < n4; ++i) {
            final float n6 = (float)(n3 * Math.cos(i * n5));
            final float n7 = (float)(n3 * Math.sin(i * n5));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n6, n2 + n7);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawCircle(final float n, final float n2, final float n3, final Color color) {
        final int n4 = 70;
        final double n5 = 6.283185307179586 / n4;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int i = 0; i < n4; ++i) {
            final float n6 = (float)(n3 * Math.cos(i * n5));
            final float n7 = (float)(n3 * Math.sin(i * n5));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n6, n2 + n7);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawCircle(final float n, final float n2, final float n3, final Color color, final float n4, final float n5, final float n6) {
        final int n7 = 100;
        final double n8 = n6 * 2.0f * 3.141592653589793 / n7;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(n4);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        for (int n9 = (int)n5; n9 < n5 + n7; ++n9) {
            final float n10 = (float)(n3 * Math.cos(n9 * n8));
            final float n11 = (float)(n3 * Math.sin(n9 * n8));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n10, n2 + n11);
        }
        for (int i = (int)(n5 + n7); i > (int)n5; --i) {
            final float n12 = (float)(n3 * Math.cos(i * n8));
            final float n13 = (float)(n3 * Math.sin(i * n8));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n12, n2 + n13);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final int n, final int n2, final float n3, final Color color) {
        final int n4 = 100;
        final double n5 = 6.283185307179586 / n4;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < n4; ++i) {
            final float n6 = (float)(n3 * Math.sin(i * n5));
            final float n7 = (float)(n3 * Math.cos(i * n5));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n6, n2 + n7);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final float n, final float n2, final float n3, final Color color) {
        final int n4 = 50;
        final double n5 = 6.283185307179586 / n4;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < n4; ++i) {
            final float n6 = (float)(n3 * Math.sin(i * n5));
            final float n7 = (float)(n3 * Math.cos(i * n5));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n6, n2 + n7);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final int n, final int n2, final float n3, final int n4) {
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        final int n9 = 50;
        final double n10 = 6.283185307179586 / n9;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(6);
        for (int i = 0; i < n9; ++i) {
            final float n11 = (float)(n3 * Math.sin(i * n10));
            final float n12 = (float)(n3 * Math.cos(i * n10));
            GL11.glColor4f(n6, n7, n8, n5);
            GL11.glVertex2f(n + n11, n2 + n12);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final float n, final float n2, final float n3, final int n4) {
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        final int n9 = 50;
        final double n10 = 6.283185307179586 / n9;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < n9; ++i) {
            final float n11 = (float)(n3 * Math.sin(i * n10));
            final float n12 = (float)(n3 * Math.cos(i * n10));
            GL11.glColor4f(n6, n7, n8, n5);
            GL11.glVertex2f(n + n11, n2 + n12);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledCircle(final int n, final int n2, final float n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final float n9 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n4 & 0xFF) / 255.0f;
        final int n13 = 50;
        final double n14 = 6.283185307179586 / n13;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < n13; ++i) {
            final float n15 = (float)(n3 * Math.sin(i * n14));
            final float n16 = (float)(n3 * Math.cos(i * n14));
            float n17 = n + n15;
            float n18 = n2 + n16;
            if (n17 < n5) {
                n17 = n5;
            }
            if (n17 > n7) {
                n17 = n7;
            }
            if (n18 < n6) {
                n18 = n6;
            }
            if (n18 > n8) {
                n18 = n8;
            }
            GL11.glColor4f(n10, n11, n12, n9);
            GL11.glVertex2f(n17, n18);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawLine(final float n, final float n2, final float n3, final float n4, final Color color) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        getWorldRenderer.begin(1, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos((double)n, (double)n2, 0.0).endVertex();
        getWorldRenderer.pos((double)n3, (double)n4, 0.0).endVertex();
        getInstance.draw();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static String getShaderCode(final InputStreamReader inputStreamReader) {
        String string = "";
        try {
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                string = String.valueOf(string) + line + "\n";
            }
            bufferedReader.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return string.toString();
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final int n, final int n2, final int n3, final int n4) {
        drawImage(resourceLocation, n, n2, n3, n4, 1.0f);
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final float n, final float n2, final float n3, final float n4) {
        drawImage(resourceLocation, (int)n, (int)n2, (int)n3, (int)n4, 1.0f);
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final int n, final int n2, final int n3, final int n4, final float n5) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, n5);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }
    
    public static void drawOutlinedRect(final int n, final int n2, final int n3, final int n4, final int n5, final Color color, final Color color2) {
        drawRect(n, n2, n3, n4, color2.getRGB());
        drawRect(n, n2, n3, n2 + n5, color.getRGB());
        drawRect(n, n4 - n5, n3, n4, color.getRGB());
        drawRect(n, n2 + n5, n + n5, n4 - n5, color.getRGB());
        drawRect(n3 - n5, n2 + n5, n3, n4 - n5, color.getRGB());
    }
    
    public static void drawOutlinedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n, n2, n3, n4, n7);
        drawRect(n, n2, n3, n2 + n5, n6);
        drawRect(n, n4 - n5, n3, n4, n6);
        drawRect(n, n2 + n5, n + n5, n4 - n5, n6);
        drawRect(n3 - n5, n2 + n5, n3, n4 - n5, n6);
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final int n, final int n2, final int n3, final int n4, final Color color) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(color.getRed() / 255.0f, color.getBlue() / 255.0f, color.getRed() / 255.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, (float)n3, (float)n4);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }
    
    public static void doGlScissor(final int n, final int n2, final int n3, final int n4) {
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        int n5 = 1;
        int guiScale = getMinecraft.gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }
        while (n5 < guiScale && getMinecraft.displayWidth / (n5 + 1) >= 320 && getMinecraft.displayHeight / (n5 + 1) >= 240) {
            ++n5;
        }
        GL11.glScissor(n * n5, getMinecraft.displayHeight - (n2 + n4) * n5, n3 * n5, n4 * n5);
    }
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4, final int n5) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        color(n5);
        GL11.glBegin(7);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void color(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static int createShader(final String s, final int n) throws Exception {
        int glCreateShaderObjectARB = 0;
        try {
            glCreateShaderObjectARB = ARBShaderObjects.glCreateShaderObjectARB(n);
            if (glCreateShaderObjectARB == 0) {
                return 0;
            }
        }
        catch (Exception ex) {
            ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
            throw ex;
        }
        ARBShaderObjects.glShaderSourceARB(glCreateShaderObjectARB, (CharSequence)s);
        ARBShaderObjects.glCompileShaderARB(glCreateShaderObjectARB);
        if (ARBShaderObjects.glGetObjectParameteriARB(glCreateShaderObjectARB, 35713) == 0) {
            throw new RuntimeException("Error creating shader:");
        }
        return glCreateShaderObjectARB;
    }
    
    public void drawCircle(final int n, final int n2, final float n3, final int n4) {
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        final boolean glIsEnabled = GL11.glIsEnabled(3042);
        final boolean glIsEnabled2 = GL11.glIsEnabled(2848);
        final boolean glIsEnabled3 = GL11.glIsEnabled(3553);
        if (!glIsEnabled) {
            GL11.glEnable(3042);
        }
        if (!glIsEnabled2) {
            GL11.glEnable(2848);
        }
        if (glIsEnabled3) {
            GL11.glDisable(3553);
        }
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(n + Math.sin(i * 3.141526 / 180.0) * n3, n2 + Math.cos(i * 3.141526 / 180.0) * n3);
        }
        GL11.glEnd();
        if (glIsEnabled3) {
            GL11.glEnable(3553);
        }
        if (!glIsEnabled2) {
            GL11.glDisable(2848);
        }
        if (!glIsEnabled) {
            GL11.glDisable(3042);
        }
    }
    
    public static void outlineOne() {
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(4.0f);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void outlineTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void outlineThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void outlineFour() {
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        GL11.glColor4f(0.9529412f, 0.6117647f, 0.07058824f, 1.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void outlineFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(3, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(3, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(1, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getInstance.draw();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getInstance.draw();
    }
    
    public static void drawOutlinedBlockESP(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(n8);
        GL11.glColor4f(n4, n5, n6, n7);
        drawOutlinedBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawBlockESP(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n4, n5, n6, n7);
        drawBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glLineWidth(n12);
        GL11.glColor4f(n8, n9, n10, n11);
        drawOutlinedBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawSolidBlockESP(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n4, n5, n6, n7);
        drawBoundingBox(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawOutlinedEntityESP(final double n, final double n2, final double n3, final double n4, final double n5, final float n6, final float n7, final float n8, final float n9) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n6, n7, n8, n9);
        drawOutlinedBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawSolidEntityESP(final double n, final double n2, final double n3, final double n4, final double n5, final float n6, final float n7, final float n8, final float n9) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n6, n7, n8, n9);
        drawBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawEntityESP(final double n, final double n2, final double n3, final double n4, final double n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n6, n7, n8, n9);
        drawBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glLineWidth(n14);
        GL11.glColor4f(n10, n11, n12, n13);
        drawOutlinedBoundingBox(new AxisAlignedBB(n - n4, n2, n3 - n4, n + n4, n2 + n5, n3 + n4));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawTracerLine(final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(n8);
        GL11.glColor4f(n4, n5, n6, n7);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0, 0.0 + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(n, n2, n3);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getInstance.draw();
        getWorldRenderer.begin(7, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        getInstance.draw();
    }
    
    public static void checkSetupFBO() {
        final Framebuffer getFramebuffer = Minecraft.getMinecraft().getFramebuffer();
        if (getFramebuffer != null && getFramebuffer.depthBuffer > -1) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(getFramebuffer.depthBuffer);
            final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
            EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
            EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
            getFramebuffer.depthBuffer = -1;
        }
    }
    
    public static void drawRoundedRect(float n, float n2, float n3, float n4, final float n5, final int n6) {
        n += (float)(n5 / 2.0f + 0.5);
        n2 += (float)(n5 / 2.0f + 0.5);
        n3 -= (float)(n5 / 2.0f + 0.5);
        n4 -= (float)(n5 / 2.0f + 0.5);
        drawRect(n, n2, n3, n4, n6);
        circle(n3 - n5 / 2.0f, n2 + n5 / 2.0f, n5, n6);
        circle(n + n5 / 2.0f, n4 - n5 / 2.0f, n5, n6);
        circle(n + n5 / 2.0f, n2 + n5 / 2.0f, n5, n6);
        circle(n3 - n5 / 2.0f, n4 - n5 / 2.0f, n5, n6);
        drawRect(n - n5 / 2.0f - 0.5f, n2 + n5 / 2.0f, n3, n4 - n5 / 2.0f, n6);
        drawRect(n, n2 + n5 / 2.0f, n3 + n5 / 2.0f + 0.5f, n4 - n5 / 2.0f, n6);
        drawRect(n + n5 / 2.0f, n2 - n5 / 2.0f - 0.5f, n3 - n5 / 2.0f, n4 - n5 / 2.0f, n6);
        drawRect(n + n5 / 2.0f, n2, n3 - n5 / 2.0f, n4 + n5 / 2.0f + 0.5f, n6);
    }
    
    public static void circle(final float n, final float n2, final float n3, final int n4) {
        arc(n, n2, 0.0f, 360.0f, n3, n4);
    }
    
    public static void circle(final float n, final float n2, final float n3, final Color color) {
        arc(n, n2, 0.0f, 360.0f, n3, color);
    }
    
    public static void arc(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        arcEllipse(n, n2, n3, n4, n5, n5, n6);
    }
    
    public static void arc(final float n, final float n2, final float n3, final float n4, final float n5, final Color color) {
        arcEllipse(n, n2, n3, n4, n5, n5, color);
    }
    
    public static void arcEllipse(final float n, final float n2, float n3, float n4, final float n5, final float n6, final int n7) {
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        if (n3 > n4) {
            final float n8 = n4;
            n4 = n3;
            n3 = n8;
        }
        final float n9 = (n7 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n7 & 0xFF) / 255.0f;
        Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n10, n11, n12, n9);
        if (n9 > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float n13 = n4; n13 >= n3; n13 -= 4.0f) {
                GL11.glVertex2f(n + (float)Math.cos(n13 * 3.141592653589793 / 180.0) * (n5 * 1.001f), n2 + (float)Math.sin(n13 * 3.141592653589793 / 180.0) * (n6 * 1.001f));
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float n14 = n4; n14 >= n3; n14 -= 4.0f) {
            GL11.glVertex2f(n + (float)Math.cos(n14 * 3.141592653589793 / 180.0) * n5, n2 + (float)Math.sin(n14 * 3.141592653589793 / 180.0) * n6);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void arcEllipse(final float n, final float n2, float n3, float n4, final float n5, final float n6, final Color color) {
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        if (n3 > n4) {
            final float n7 = n4;
            n4 = n3;
            n3 = n7;
        }
        Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        if (color.getAlpha() > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float n8 = n4; n8 >= n3; n8 -= 4.0f) {
                GL11.glVertex2f(n + (float)Math.cos(n8 * 3.141592653589793 / 180.0) * (n5 * 1.001f), n2 + (float)Math.sin(n8 * 3.141592653589793 / 180.0) * (n6 * 1.001f));
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float n9 = n4; n9 >= n3; n9 -= 4.0f) {
            GL11.glVertex2f(n + (float)Math.cos(n9 * 3.141592653589793 / 180.0) * n5, n2 + (float)Math.sin(n9 * 3.141592653589793 / 180.0) * n6);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void startDrawing() {
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        ((IEntityRenderer)Minecraft.getMinecraft().entityRenderer).runSetupCameraTransform(Class211.getTimer().renderPartialTicks, 0);
    }
    
    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n, n2, n3, n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static Color blend(final Color color, final Color color2, final double n) {
        final float n2 = (float)n;
        final float n3 = 1.0f - n2;
        final float[] array = new float[3];
        final float[] array2 = new float[3];
        final float[] array3 = color.getColorComponents(array);
        final float[] array4 = color2.getColorComponents(array2);
        return new Color(array[0] * n2 + array2[0] * n3, array[1] * n2 + array2[1] * n3, array[2] * n2 + array2[2] * n3);
    }
}
