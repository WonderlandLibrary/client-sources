package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader
{
    private static final String HorizonCode_Horizon_È = "CL_00002567";
    
    public int HorizonCode_Horizon_È(final WorldRenderer p_178177_1_, final int p_178177_2_) {
        if (p_178177_2_ > 0) {
            final VertexFormat var3 = p_178177_1_.à();
            final int var4 = var3.Ó();
            final ByteBuffer var5 = p_178177_1_.Ó();
            final List var6 = var3.à();
            for (final VertexFormatElement var8 : var6) {
                final VertexFormatElement.Â var9 = var8.Ý();
                final int var10 = var8.Â().Ý();
                final int var11 = var8.Âµá€();
                switch (WorldVertexBufferUploader.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var9.ordinal()]) {
                    default: {
                        continue;
                    }
                    case 1: {
                        var5.position(var8.HorizonCode_Horizon_È());
                        GL11.glVertexPointer(var8.Ø­áŒŠá(), var10, var4, var5);
                        GL11.glEnableClientState(32884);
                        continue;
                    }
                    case 2: {
                        var5.position(var8.HorizonCode_Horizon_È());
                        OpenGlHelper.á(OpenGlHelper.£à + var11);
                        GL11.glTexCoordPointer(var8.Ø­áŒŠá(), var10, var4, var5);
                        GL11.glEnableClientState(32888);
                        OpenGlHelper.á(OpenGlHelper.£à);
                        continue;
                    }
                    case 3: {
                        var5.position(var8.HorizonCode_Horizon_È());
                        GL11.glColorPointer(var8.Ø­áŒŠá(), var10, var4, var5);
                        GL11.glEnableClientState(32886);
                        continue;
                    }
                    case 4: {
                        var5.position(var8.HorizonCode_Horizon_È());
                        GL11.glNormalPointer(var10, var4, var5);
                        GL11.glEnableClientState(32885);
                        continue;
                    }
                }
            }
            GL11.glDrawArrays(p_178177_1_.áŒŠÆ(), 0, p_178177_1_.Ø());
            for (final VertexFormatElement var8 : var6) {
                final VertexFormatElement.Â var9 = var8.Ý();
                final int var10 = var8.Âµá€();
                switch (WorldVertexBufferUploader.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var9.ordinal()]) {
                    default: {
                        continue;
                    }
                    case 1: {
                        GL11.glDisableClientState(32884);
                        continue;
                    }
                    case 2: {
                        OpenGlHelper.á(OpenGlHelper.£à + var10);
                        GL11.glDisableClientState(32888);
                        OpenGlHelper.á(OpenGlHelper.£à);
                        continue;
                    }
                    case 3: {
                        GL11.glDisableClientState(32886);
                        GlStateManager.ÇŽÉ();
                        continue;
                    }
                    case 4: {
                        GL11.glDisableClientState(32885);
                        continue;
                    }
                }
            }
        }
        p_178177_1_.HorizonCode_Horizon_È();
        return p_178177_2_;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002566";
        
        static {
            HorizonCode_Horizon_È = new int[VertexFormatElement.Â.values().length];
            try {
                WorldVertexBufferUploader.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                WorldVertexBufferUploader.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                WorldVertexBufferUploader.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                WorldVertexBufferUploader.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.Â.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
