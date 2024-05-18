package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;

public class RenderCaveSpider extends RenderSpider<EntityCaveSpider>
{
    private static final String[] I;
    private static final ResourceLocation caveSpiderTextures;
    
    static {
        I();
        caveSpiderTextures = new ResourceLocation(RenderCaveSpider.I["".length()]);
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityCaveSpider entityCaveSpider) {
        return RenderCaveSpider.caveSpiderTextures;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("74\u00199\u001414\u0012b\u0004-%\b9\u0018l\"\u0011$\u0005&#N.\u000054>>\u0011*5\u0004?O3?\u0006", "CQaMa");
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityCaveSpider)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySpider entitySpider) {
        return this.getEntityTexture((EntityCaveSpider)entitySpider);
    }
    
    public RenderCaveSpider(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize *= 0.7f;
    }
    
    @Override
    protected void preRenderCallback(final EntityCaveSpider entityCaveSpider, final float n) {
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
    }
}
