package com.client.glowclient.utils;

import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import java.awt.*;
import com.client.glowclient.modules.render.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.client.renderer.*;

public class Stencil
{
    public static void E() {
        GL11.glStencilFunc(512, 0, 15);
        final int n = 7681;
        GL11.glStencilOp(n, n, n);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void M(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
    
    public static void e() {
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
    
    public Stencil() {
        super();
    }
    
    public static void k() {
        final Framebuffer framebuffer;
        if ((framebuffer = Minecraft.getMinecraft().getFramebuffer()) != null && framebuffer.depthBuffer > -1) {
            final int depthBuffer = -1;
            final Framebuffer framebuffer2 = framebuffer;
            M(framebuffer2);
            framebuffer2.depthBuffer = depthBuffer;
        }
    }
    
    public static void M(final Color color) {
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), (double)(color.getAlpha() / 255.0f));
    }
    
    public static void A() {
        k();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth((float)EntityESP.width.k());
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        final int n = 7681;
        GL11.glStencilOp(n, n, n);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void D() {
        M(new Color(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 255));
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        final int lightmapTexUnit = OpenGlHelper.lightmapTexUnit;
        final float n = 240.0f;
        OpenGlHelper.setLightmapTextureCoords(lightmapTexUnit, n, n);
    }
    
    public static void M() {
        GL11.glStencilFunc(514, 1, 15);
        final int n = 7680;
        GL11.glStencilOp(n, n, n);
        GL11.glPolygonMode(1032, 6913);
    }
}
