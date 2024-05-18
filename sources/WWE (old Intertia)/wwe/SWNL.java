package wwe;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;

public class SWNL
{
    public SWNL() {
        super();
    }
    
    public static void VZWQ() {
        MqiP();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(3.0f);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void JLYv() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void feKn() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void mptE(final EntityLivingBase entityLivingBase) {
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void SnYv(final Boolean b, final Boolean b2, final Boolean b3) {
        if (b && !b2 && !b3) {
            GL11.glColor3f(145.0f, 0.0f, 255.0f);
            if (SWNL.\u2008\u2002\u200d) {
                throw null;
            }
        }
        else if (!b && b2 && !b3) {
            GL11.glColor3f(255.0f, 0.0f, 0.0f);
            if (SWNL.\u2008\u2002\u200d) {
                throw null;
            }
        }
        else if (!b && !b2 && b3) {
            GL11.glColor3f(1.0f, 0.7f, 0.0f);
        }
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void jdrH(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase.isInvisible()) {}
        if (!entityLivingBase.isPlayerSleeping() && !entityLivingBase.isSneaking() && !entityLivingBase.isInvisible()) {
            GL11.glColor3f(1.0f, 0.0f, 0.0f);
        }
        if (entityLivingBase.isSneaking() && !entityLivingBase.isInvisible()) {
            GL11.glColor3f(0.0f, 0.0f, 1.0f);
        }
        if (vlwv.Zqnd().UsVJ(entityLivingBase.getName())) {
            GL11.glColor3f(255.0f, 137.0f, 0.0f);
        }
    }
    
    public static void VdOT() {
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
    
    public static void zTaX(final Color color) {
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), (double)(color.getAlpha() / 255.0f));
    }
    
    public static void MqiP() {
        final Framebuffer framebuffer = Minecraft.getMinecraft().getFramebuffer();
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            Cvvp(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }
    
    public static void Cvvp(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
}
