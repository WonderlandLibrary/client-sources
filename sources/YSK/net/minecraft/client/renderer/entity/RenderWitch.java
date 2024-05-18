package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderWitch extends RenderLiving<EntityWitch>
{
    private static final String[] I;
    private static final ResourceLocation witchTextures;
    
    @Override
    public void doRender(final EntityWitch entityWitch, final double n, final double n2, final double n3, final float n4, final float n5) {
        final ModelWitch modelWitch = (ModelWitch)this.mainModel;
        int field_82900_g;
        if (entityWitch.getHeldItem() != null) {
            field_82900_g = " ".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            field_82900_g = "".length();
        }
        modelWitch.field_82900_g = (field_82900_g != 0);
        super.doRender(entityWitch, n, n2, n3, n4, n5);
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        witchTextures = new ResourceLocation(RenderWitch.I["".length()]);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWitch)entityLiving, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(",0\u001d\u0003\u001d*0\u0016X\r6!\f\u0003\u0011w\"\f\u0003\u000b0{\u0015\u0019\u000f", "XUewh");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWitch)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityWitch entityWitch) {
        return RenderWitch.witchTextures;
    }
    
    @Override
    protected void preRenderCallback(final EntityWitch entityWitch, final float n) {
        final float n2 = 0.9375f;
        GlStateManager.scale(n2, n2, n2);
    }
    
    public RenderWitch(final RenderManager renderManager) {
        super(renderManager, new ModelWitch(0.0f), 0.5f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItemWitch(this));
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityWitch)entityLivingBase, n);
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
}
