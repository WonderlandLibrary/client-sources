package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class RenderXPOrb extends Render<EntityXPOrb>
{
    private static final String[] I;
    private static final ResourceLocation experienceOrbTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityXPOrb)entity);
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRender(final EntityXPOrb entityXPOrb, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        this.bindEntityTexture(entityXPOrb);
        final int textureByXP = entityXPOrb.getTextureByXP();
        final float n6 = (textureByXP % (0xB0 ^ 0xB4) * (0x71 ^ 0x61) + "".length()) / 64.0f;
        final float n7 = (textureByXP % (0x42 ^ 0x46) * (0x80 ^ 0x90) + (0x53 ^ 0x43)) / 64.0f;
        final float n8 = (textureByXP / (0x40 ^ 0x44) * (0x90 ^ 0x80) + "".length()) / 64.0f;
        final float n9 = (textureByXP / (0x2C ^ 0x28) * (0x10 ^ 0x0) + (0x9B ^ 0x8B)) / 64.0f;
        final float n10 = 1.0f;
        final float n11 = 0.5f;
        final float n12 = 0.25f;
        final int brightnessForRender = entityXPOrb.getBrightnessForRender(n5);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (62159 + 8782 - 70616 + 65211) / 1.0f, brightnessForRender / (57878 + 27993 - 76104 + 55769) / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float n13 = (entityXPOrb.xpColor + n5) / 2.0f;
        final int n14 = (int)((MathHelper.sin(n13 + 0.0f) + 1.0f) * 0.5f * 255.0f);
        final int n15 = (int)((MathHelper.sin(n13 + 4.1887903f) + 1.0f) * 0.1f * 255.0f);
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.3f, 0.3f, 0.3f);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0xAD ^ 0xAA, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        worldRenderer.pos(0.0f - n11, 0.0f - n12, 0.0).tex(n6, n9).color(n14, 144 + 140 - 48 + 19, n15, 79 + 46 - 58 + 61).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(n10 - n11, 0.0f - n12, 0.0).tex(n7, n9).color(n14, 18 + 134 + 51 + 52, n15, 58 + 120 - 55 + 5).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(n10 - n11, 1.0f - n12, 0.0).tex(n7, n8).color(n14, 128 + 177 - 143 + 93, n15, 106 + 108 - 141 + 55).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(0.0f - n11, 1.0f - n12, 0.0).tex(n6, n8).color(n14, 229 + 227 - 332 + 131, n15, 41 + 37 + 40 + 10).normal(0.0f, 1.0f, 0.0f).endVertex();
        instance.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entityXPOrb, n, n2, n3, n4, n5);
    }
    
    public RenderXPOrb(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityXPOrb entityXPOrb) {
        return RenderXPOrb.experienceOrbTextures;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityXPOrb)entity, n, n2, n3, n4, n5);
    }
    
    static {
        I();
        experienceOrbTextures = new ResourceLocation(RenderXPOrb.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("7\u0006\u001e%#1\u0006\u0015~3-\u0017\u000f%/l\u0006\u001e!31\n\u0003?5&<\t#4m\u0013\b6", "CcfQV");
    }
}
