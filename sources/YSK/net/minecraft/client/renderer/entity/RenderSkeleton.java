package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderSkeleton extends RenderBiped<EntitySkeleton>
{
    private static final String[] I;
    private static final ResourceLocation witherSkeletonTextures;
    private static final ResourceLocation skeletonTextures;
    
    @Override
    protected void preRenderCallback(final EntitySkeleton entitySkeleton, final float n) {
        if (entitySkeleton.getSkeletonType() == " ".length()) {
            GlStateManager.scale(1.2f, 1.2f, 1.2f);
        }
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0010$5\f&\u0016$>W6\n5$\f*K2&\u001d?\u00015\"\u0016|\u0017*(\u00146\u0010.#V#\n&", "dAMxS");
        RenderSkeleton.I[" ".length()] = I("%\u00117\u00197#\u0011<B'?\u0000&\u0019;~\u0007$\b.4\u0000 \u0003m&\u001d;\u0005'#+<\u0006'=\u0011;\u0002,\u007f\u0004!\n", "QtOmB");
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntitySkeleton)entityLivingBase, n);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.getEntityTexture((EntitySkeleton)entityLiving);
    }
    
    static {
        I();
        skeletonTextures = new ResourceLocation(RenderSkeleton.I["".length()]);
        witherSkeletonTextures = new ResourceLocation(RenderSkeleton.I[" ".length()]);
    }
    
    public RenderSkeleton(final RenderManager renderManager) {
        super(renderManager, new ModelSkeleton(), 0.5f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItem(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerBipedArmor(this, this) {
            final RenderSkeleton this$0;
            
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
                    if (4 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected void initArmor() {
                this.field_177189_c = (T)new ModelSkeleton(0.5f, " ".length() != 0);
                this.field_177186_d = (T)new ModelSkeleton(1.0f, " ".length() != 0);
            }
        });
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySkeleton entitySkeleton) {
        ResourceLocation resourceLocation;
        if (entitySkeleton.getSkeletonType() == " ".length()) {
            resourceLocation = RenderSkeleton.witherSkeletonTextures;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderSkeleton.skeletonTextures;
        }
        return resourceLocation;
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
}
