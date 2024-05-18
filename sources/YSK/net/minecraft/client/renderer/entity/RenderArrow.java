package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class RenderArrow extends Render<EntityArrow>
{
    private static final String[] I;
    private static final ResourceLocation arrowTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityArrow)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityArrow entityArrow) {
        return RenderArrow.arrowTextures;
    }
    
    @Override
    public void doRender(final EntityArrow entityArrow, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.bindEntityTexture(entityArrow);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.rotate(entityArrow.prevRotationYaw + (entityArrow.rotationYaw - entityArrow.prevRotationYaw) * n5 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entityArrow.prevRotationPitch + (entityArrow.rotationPitch - entityArrow.prevRotationPitch) * n5, 0.0f, 0.0f, 1.0f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final int length = "".length();
        final float n6 = 0.0f;
        final float n7 = 0.5f;
        final float n8 = ("".length() + length * (0xB3 ^ 0xB9)) / 32.0f;
        final float n9 = ((0xAB ^ 0xAE) + length * (0x60 ^ 0x6A)) / 32.0f;
        final float n10 = 0.0f;
        final float n11 = 0.15625f;
        final float n12 = ((0x3F ^ 0x3A) + length * (0xA5 ^ 0xAF)) / 32.0f;
        final float n13 = ((0x21 ^ 0x2B) + length * (0xB9 ^ 0xB3)) / 32.0f;
        final float n14 = 0.05625f;
        GlStateManager.enableRescaleNormal();
        final float n15 = entityArrow.arrowShake - n5;
        if (n15 > 0.0f) {
            GlStateManager.rotate(-MathHelper.sin(n15 * 3.0f) * n15, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(n14, n14, n14);
        GlStateManager.translate(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(n14, 0.0f, 0.0f);
        worldRenderer.begin(0x75 ^ 0x72, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-7.0, -2.0, -2.0).tex(n10, n12).endVertex();
        worldRenderer.pos(-7.0, -2.0, 2.0).tex(n11, n12).endVertex();
        worldRenderer.pos(-7.0, 2.0, 2.0).tex(n11, n13).endVertex();
        worldRenderer.pos(-7.0, 2.0, -2.0).tex(n10, n13).endVertex();
        instance.draw();
        GL11.glNormal3f(-n14, 0.0f, 0.0f);
        worldRenderer.begin(0x76 ^ 0x71, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-7.0, 2.0, -2.0).tex(n10, n12).endVertex();
        worldRenderer.pos(-7.0, 2.0, 2.0).tex(n11, n12).endVertex();
        worldRenderer.pos(-7.0, -2.0, 2.0).tex(n11, n13).endVertex();
        worldRenderer.pos(-7.0, -2.0, -2.0).tex(n10, n13).endVertex();
        instance.draw();
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < (0x62 ^ 0x66)) {
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, n14);
            worldRenderer.begin(0x7F ^ 0x78, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(-8.0, -2.0, 0.0).tex(n6, n8).endVertex();
            worldRenderer.pos(8.0, -2.0, 0.0).tex(n7, n8).endVertex();
            worldRenderer.pos(8.0, 2.0, 0.0).tex(n7, n9).endVertex();
            worldRenderer.pos(-8.0, 2.0, 0.0).tex(n6, n9).endVertex();
            instance.draw();
            ++i;
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entityArrow, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityArrow)entity, n, n2, n3, n4, n5);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        arrowTextures = new ResourceLocation(RenderArrow.I["".length()]);
    }
    
    public RenderArrow(final RenderManager renderManager) {
        super(renderManager);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001e\u00037%6\u0018\u0003<~&\u0004\u0012&%:E\u0007=#,\u001dH??$", "jfOQC");
    }
}
