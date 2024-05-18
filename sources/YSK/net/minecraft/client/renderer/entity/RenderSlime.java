package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSlime extends RenderLiving<EntitySlime>
{
    private static final String[] I;
    private static final ResourceLocation slimeTextures;
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntitySlime)entityLivingBase, n);
    }
    
    static {
        I();
        slimeTextures = new ResourceLocation(RenderSlime.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySlime entitySlime) {
        return RenderSlime.slimeTextures;
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRender(final EntitySlime entitySlime, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.shadowSize = 0.25f * entitySlime.getSlimeSize();
        super.doRender(entitySlime, n, n2, n3, n4, n5);
    }
    
    public RenderSlime(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerSlimeGel(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySlime)entity);
    }
    
    @Override
    protected void preRenderCallback(final EntitySlime entitySlime, final float n) {
        final float n2 = entitySlime.getSlimeSize();
        final float n3 = 1.0f / ((entitySlime.prevSquishFactor + (entitySlime.squishFactor - entitySlime.prevSquishFactor) * n) / (n2 * 0.5f + 1.0f) + 1.0f);
        GlStateManager.scale(n3 * n2, 1.0f / n3 * n2, n3 * n2);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntitySlime)entityLiving, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("1);5\u00017)0n\u0011+8*5\rj?/(\u0019 c0-\u001d()m1\u001a\"", "ELCAt");
    }
}
