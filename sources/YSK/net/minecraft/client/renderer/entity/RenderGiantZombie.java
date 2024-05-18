package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderGiantZombie extends RenderLiving<EntityGiantZombie>
{
    private static final ResourceLocation zombieTextures;
    private static final String[] I;
    private float scale;
    
    public RenderGiantZombie(final RenderManager renderManager, final ModelBase modelBase, final float n, final float scale) {
        super(renderManager, modelBase, n * scale);
        this.scale = scale;
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItem(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerBipedArmor(this, this) {
            final RenderGiantZombie this$0;
            
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
                    if (3 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected void initArmor() {
                this.field_177189_c = (T)new ModelZombie(0.5f, " ".length() != 0);
                this.field_177186_d = (T)new ModelZombie(1.0f, " ".length() != 0);
            }
        });
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        zombieTextures = new ResourceLocation(RenderGiantZombie.I["".length()]);
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityGiantZombie)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityGiantZombie entityGiantZombie) {
        return RenderGiantZombie.zombieTextures;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("7313!13:h1-\" 3-l,&*6*3f=;.4 \"z38.", "CVIGT");
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGiantZombie)entityLivingBase, n);
    }
    
    @Override
    protected void preRenderCallback(final EntityGiantZombie entityGiantZombie, final float n) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
}
