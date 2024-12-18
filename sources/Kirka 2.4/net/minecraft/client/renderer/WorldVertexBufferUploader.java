/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.renderer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader {
    private static final String __OBFID = "CL_00002567";

    public int draw(WorldRenderer p_178177_1_, int p_178177_2_) {
        if (p_178177_2_ > 0) {
            int var10;
            VertexFormatElement.EnumUseage var9;
            VertexFormat var3 = p_178177_1_.func_178973_g();
            int var4 = var3.func_177338_f();
            ByteBuffer var5 = p_178177_1_.func_178966_f();
            List var6 = var3.func_177343_g();
            for (VertexFormatElement var8 : var6) {
                var9 = var8.func_177375_c();
                var10 = var8.func_177367_b().func_177397_c();
                int var11 = var8.func_177369_e();
                switch (SwitchEnumUseage.field_178958_a[var9.ordinal()]) {
                    case 1: {
                        var5.position(var8.func_177373_a());
                        GL11.glVertexPointer((int)var8.func_177370_d(), (int)var10, (int)var4, (ByteBuffer)var5);
                        GL11.glEnableClientState((int)32884);
                        break;
                    }
                    case 2: {
                        var5.position(var8.func_177373_a());
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var11);
                        GL11.glTexCoordPointer((int)var8.func_177370_d(), (int)var10, (int)var4, (ByteBuffer)var5);
                        GL11.glEnableClientState((int)32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;
                    }
                    case 3: {
                        var5.position(var8.func_177373_a());
                        GL11.glColorPointer((int)var8.func_177370_d(), (int)var10, (int)var4, (ByteBuffer)var5);
                        GL11.glEnableClientState((int)32886);
                        break;
                    }
                    case 4: {
                        var5.position(var8.func_177373_a());
                        GL11.glNormalPointer((int)var10, (int)var4, (ByteBuffer)var5);
                        GL11.glEnableClientState((int)32885);
                    }
                }
            }
            GL11.glDrawArrays((int)p_178177_1_.getDrawMode(), (int)0, (int)p_178177_1_.func_178989_h());
            for (VertexFormatElement var8 : var6) {
                var9 = var8.func_177375_c();
                var10 = var8.func_177369_e();
                switch (SwitchEnumUseage.field_178958_a[var9.ordinal()]) {
                    case 1: {
                        GL11.glDisableClientState((int)32884);
                        break;
                    }
                    case 2: {
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var10);
                        GL11.glDisableClientState((int)32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;
                    }
                    case 3: {
                        GL11.glDisableClientState((int)32886);
                        GlStateManager.func_179117_G();
                        break;
                    }
                    case 4: {
                        GL11.glDisableClientState((int)32885);
                    }
                }
            }
        }
        p_178177_1_.reset();
        return p_178177_2_;
    }

    static final class SwitchEnumUseage {
        static final int[] field_178958_a = new int[VertexFormatElement.EnumUseage.values().length];
        private static final String __OBFID = "CL_00002566";

        static {
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumUseage.field_178958_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchEnumUseage() {
        }
    }

}

