package com.client.glowclient;

import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;

public class pa
{
    private static final Minecraft b;
    
    public static void M(final int n, int n2, final int n3, final int n4, final int n5, final int n6) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        final int n7 = 4;
        pa.b.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        final float n8 = 1.0f;
        GlStateManager.color(n8, n8, n8, n8);
        final float n9 = 32.0f;
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        final int n10 = n3 + n;
        final float n11 = 1.0f;
        GlStateManager.color(n11, n11, n11, n11);
        final BufferBuilder bufferBuilder = buffer;
        final BufferBuilder bufferBuilder2 = buffer;
        bufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        final BufferBuilder tex = bufferBuilder2.pos((double)n4, (double)n10, 0.0).tex(0.0, (double)(n10 / n9));
        final int n12 = 64;
        tex.color(n12, n12, n12, 255).endVertex();
        final BufferBuilder tex2 = bufferBuilder2.pos((double)n6, (double)n10, 0.0).tex((double)(n6 / n9), (double)(n10 / n9));
        final int n13 = 64;
        tex2.color(n13, n13, n13, 255).endVertex();
        final BufferBuilder tex3 = bufferBuilder.pos((double)n6, (double)n3, 0.0).tex((double)(n6 / n9), (double)(n3 / n9));
        final int n14 = 64;
        tex3.color(n14, n14, n14, 255).endVertex();
        final BufferBuilder tex4 = bufferBuilder.pos((double)n4, (double)n3, 0.0).tex(0.0, (double)(n3 / n9));
        final int n15 = 64;
        tex4.color(n15, n15, n15, 255).endVertex();
        instance.draw();
        n2 = n5 - n2;
        final float n16 = 1.0f;
        GlStateManager.color(n16, n16, n16, n16);
        final BufferBuilder bufferBuilder3 = buffer;
        final BufferBuilder bufferBuilder4 = buffer;
        bufferBuilder4.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        final BufferBuilder tex5 = bufferBuilder4.pos((double)n4, (double)n5, 0.0).tex(0.0, (double)(n5 / n9));
        final int n17 = 64;
        tex5.color(n17, n17, n17, 255).endVertex();
        final BufferBuilder tex6 = bufferBuilder4.pos((double)n6, (double)n5, 0.0).tex((double)(n6 / n9), (double)(n5 / n9));
        final int n18 = 64;
        tex6.color(n18, n18, n18, 255).endVertex();
        final BufferBuilder tex7 = bufferBuilder3.pos((double)n6, (double)n2, 0.0).tex((double)(n6 / n9), (double)(n2 / n9));
        final int n19 = 64;
        tex7.color(n19, n19, n19, 255).endVertex();
        final BufferBuilder tex8 = bufferBuilder3.pos((double)n4, (double)n2, 0.0).tex(0.0, (double)(n2 / n9));
        final int n20 = 64;
        tex8.color(n20, n20, n20, 255).endVertex();
        instance.draw();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture2D();
        final BufferBuilder bufferBuilder5 = buffer;
        final BufferBuilder bufferBuilder6 = buffer;
        final BufferBuilder bufferBuilder7 = buffer;
        bufferBuilder7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        final BufferBuilder tex9 = bufferBuilder7.pos((double)n4, (double)(n10 + n7), 0.0).tex(0.0, 1.0);
        final int n21 = 0;
        tex9.color(n21, n21, n21, n21).endVertex();
        final BufferBuilder pos = bufferBuilder6.pos((double)n6, (double)(n10 + n7), 0.0);
        final double n22 = 1.0;
        final BufferBuilder tex10 = pos.tex(n22, n22);
        final int n23 = 0;
        tex10.color(n23, n23, n23, n23).endVertex();
        final BufferBuilder tex11 = bufferBuilder5.pos((double)n6, (double)n10, 0.0).tex(1.0, 0.0);
        final int n24 = 0;
        tex11.color(n24, n24, n24, 255).endVertex();
        final BufferBuilder pos2 = bufferBuilder5.pos((double)n4, (double)n10, 0.0);
        final double n25 = 0.0;
        final BufferBuilder tex12 = pos2.tex(n25, n25);
        final int n26 = 0;
        tex12.color(n26, n26, n26, 255).endVertex();
        instance.draw();
        final BufferBuilder bufferBuilder8 = buffer;
        final BufferBuilder bufferBuilder9 = buffer;
        final BufferBuilder bufferBuilder10 = buffer;
        bufferBuilder10.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        final BufferBuilder tex13 = bufferBuilder10.pos((double)n4, (double)n2, 0.0).tex(0.0, 1.0);
        final int n27 = 0;
        tex13.color(n27, n27, n27, 255).endVertex();
        final BufferBuilder pos3 = bufferBuilder9.pos((double)n6, (double)n2, 0.0);
        final double n28 = 1.0;
        final BufferBuilder tex14 = pos3.tex(n28, n28);
        final int n29 = 0;
        tex14.color(n29, n29, n29, 255).endVertex();
        final BufferBuilder tex15 = bufferBuilder8.pos((double)n6, (double)(n2 - n7), 0.0).tex(1.0, 0.0);
        final int n30 = 0;
        tex15.color(n30, n30, n30, n30).endVertex();
        final BufferBuilder pos4 = bufferBuilder8.pos((double)n4, (double)(n2 - n7), 0.0);
        final double n31 = 0.0;
        final BufferBuilder tex16 = pos4.tex(n31, n31);
        final int n32 = 0;
        tex16.color(n32, n32, n32, n32).endVertex();
        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
    }
    
    public static void M(final GuiButton guiButton, final Minecraft minecraft, final int n, final int n2) {
        guiButton.drawButton(minecraft, n, n2, 0.0f);
    }
    
    private pa() {
        super();
        throw new AssertionError();
    }
    
    public static void M(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        pa.b.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        final float n5 = 1.0f;
        GlStateManager.color(n5, n5, n5, n5);
        final float n6 = 32.0f;
        final BufferBuilder bufferBuilder = buffer;
        final BufferBuilder bufferBuilder2 = buffer;
        final double n7 = 0.0;
        final BufferBuilder bufferBuilder3 = buffer;
        bufferBuilder3.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        final BufferBuilder tex = bufferBuilder3.pos(n7, (double)n3, 0.0).tex((double)(0.0f / n6), (double)(n3 / n6));
        final int n8 = 32;
        tex.color(n8, n8, n8, 255).endVertex();
        final BufferBuilder tex2 = bufferBuilder2.pos((double)n4, (double)n3, 0.0).tex((double)(n4 / n6), (double)(n3 / n6));
        final int n9 = 32;
        tex2.color(n9, n9, n9, 255).endVertex();
        final BufferBuilder tex3 = bufferBuilder.pos((double)n4, (double)n, 0.0).tex((double)(n4 / n6), (double)(n / n6));
        final int n10 = 32;
        tex3.color(n10, n10, n10, 255).endVertex();
        final BufferBuilder tex4 = bufferBuilder.pos((double)n2, (double)n, 0.0).tex((double)(n2 / n6), (double)(n / n6));
        final int n11 = 32;
        tex4.color(n11, n11, n11, 255).endVertex();
        instance.draw();
    }
    
    static {
        b = Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP M() {
        return new EntityPlayerSP(uc.A, (World)uc.C, uc.a.connection, uc.a.getStatFileWriter(), uc.a.getRecipeBook());
    }
}
