/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL11
 */
package skizzle.util;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class OutlineUtils {
    public static void renderThree() {
        GL11.glStencilFunc((int)514, (int)1, (int)15);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void checkSetupFBO() {
        Framebuffer Nigga = Minecraft.getMinecraft().getFramebuffer();
        if (Nigga != null && Nigga.depthBuffer > -1) {
            OutlineUtils.setupFBO(Nigga);
            Nigga.depthBuffer = -1;
        }
    }

    public static {
        throw throwable;
    }

    public static void renderTwo() {
        GL11.glStencilFunc((int)512, (int)0, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6914);
    }

    public static void setupFBO(Framebuffer Nigga) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)Nigga.depthBuffer);
        int Nigga2 = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)Nigga2);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Minecraft.getMinecraft().displayWidth, (int)Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)Nigga2);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)Nigga2);
    }

    public static void setColor(Color Nigga) {
        GL11.glColor4d((double)((float)Nigga.getRed() / Float.intBitsToFloat(1.03220621E9f ^ 0x7EF93767)), (double)((float)Nigga.getGreen() / Float.intBitsToFloat(1.00882221E9f ^ 0x7F5E67AF)), (double)((float)Nigga.getBlue() / Float.intBitsToFloat(1.00838611E9f ^ 0x7F65C02C)), (double)((float)Nigga.getAlpha() / Float.intBitsToFloat(1.00942611E9f ^ 0x7F559EA5)));
    }

    public static void renderOne() {
        OutlineUtils.checkSetupFBO();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)Float.intBitsToFloat(1.0630535E9f ^ 0x7F1CE8D4));
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glClearStencil((int)15);
        GL11.glStencilFunc((int)512, (int)1, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderFive() {
        GL11.glPolygonOffset((float)Float.intBitsToFloat(1.09373069E9f ^ 0x7EB10165), (float)Float.intBitsToFloat(9.3213811E8f ^ 0x7E7B6897));
        GL11.glDisable((int)10754);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2960);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
    }

    public static void renderFour(Color Nigga) {
        OutlineUtils.setColor(Nigga);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)10754);
        GL11.glPolygonOffset((float)Float.intBitsToFloat(1.08589107E9f ^ 0x7F39622D), (float)Float.intBitsToFloat(-1.2142519E9f ^ 0x7E6BD88F));
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Float.intBitsToFloat(1.00991629E9f ^ 0x7F421965), Float.intBitsToFloat(1.01367616E9f ^ 0x7F1B786E));
    }

    public OutlineUtils() {
        OutlineUtils Nigga;
    }
}

