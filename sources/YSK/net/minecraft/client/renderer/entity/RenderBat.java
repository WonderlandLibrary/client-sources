package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderBat extends RenderLiving<EntityBat>
{
    private static final String[] I;
    private static final ResourceLocation batTextures;
    
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
            if (-1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void rotateCorpse(final EntityBat entityBat, final float n, final float n2, final float n3) {
        if (!entityBat.getIsBatHanging()) {
            GlStateManager.translate(0.0f, MathHelper.cos(n * 0.3f) * 0.1f, 0.0f);
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            GlStateManager.translate(0.0f, -0.1f, 0.0f);
        }
        super.rotateCorpse(entityBat, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityBat entityBat) {
        return RenderBat.batTextures;
    }
    
    public RenderBat(final RenderManager renderManager) {
        super(renderManager, new ModelBat(), 0.25f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityBat)entity);
    }
    
    @Override
    protected void preRenderCallback(final EntityBat entityBat, final float n) {
        GlStateManager.scale(0.35f, 0.35f, 0.35f);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityBat)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityBat)entityLivingBase, n);
    }
    
    static {
        I();
        batTextures = new ResourceLocation(RenderBat.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0001\u001c\u00027\u0000\u0007\u001c\tl\u0010\u001b\r\u00137\fZ\u001b\u001b7[\u0005\u0017\u001d", "uyzCu");
    }
}
