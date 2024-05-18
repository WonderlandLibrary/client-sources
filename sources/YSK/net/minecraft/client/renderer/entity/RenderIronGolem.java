package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderIronGolem extends RenderLiving<EntityIronGolem>
{
    private static final ResourceLocation ironGolemTextures;
    private static final String[] I;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityIronGolem)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityIronGolem entityIronGolem) {
        return RenderIronGolem.ironGolemTextures;
    }
    
    public RenderIronGolem(final RenderManager renderManager) {
        super(renderManager, new ModelIronGolem(), 0.5f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerIronGolemFlower(this));
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("#\u0013>\u0000\u0012%\u00135[\u00029\u0002/\u0000\u001ex\u001f4\u001b\t\b\u0011)\u0018\u0002:X6\u001a\u0000", "WvFtg");
    }
    
    static {
        I();
        ironGolemTextures = new ResourceLocation(RenderIronGolem.I["".length()]);
    }
    
    @Override
    protected void rotateCorpse(final EntityIronGolem entityIronGolem, final float n, final float n2, final float n3) {
        super.rotateCorpse(entityIronGolem, n, n2, n3);
        if (entityIronGolem.limbSwingAmount >= 0.01) {
            final float n4 = 13.0f;
            GlStateManager.rotate(6.5f * ((Math.abs((entityIronGolem.limbSwing - entityIronGolem.limbSwingAmount * (1.0f - n3) + 6.0f) % n4 - n4 * 0.5f) - n4 * 0.25f) / (n4 * 0.25f)), 0.0f, 0.0f, 1.0f);
        }
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityIronGolem)entityLivingBase, n, n2, n3);
    }
}
