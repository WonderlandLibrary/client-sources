package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderCreeper extends RenderLiving<EntityCreeper>
{
    private static final ResourceLocation creeperTextures;
    private static final String[] I;
    
    public RenderCreeper(final RenderManager renderManager) {
        super(renderManager, new ModelCreeper(), 0.5f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerCreeperCharge(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityCreeper entityCreeper) {
        return RenderCreeper.creeperTextures;
    }
    
    @Override
    protected int getColorMultiplier(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        return this.getColorMultiplier((EntityCreeper)entityLivingBase, n, n2);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0011\u0014\n\u001e\u0011\u0017\u0014\u0001E\u0001\u000b\u0005\u001b\u001e\u001dJ\u0012\u0000\u000f\u0001\u0015\u0014\u0000E\u0007\u0017\u0014\u0017\u001a\u0001\u0017_\u0002\u0004\u0003", "eqrjd");
    }
    
    @Override
    protected void preRenderCallback(final EntityCreeper entityCreeper, final float n) {
        final float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(n);
        final float n2 = 1.0f + MathHelper.sin(creeperFlashIntensity * 100.0f) * creeperFlashIntensity * 0.01f;
        final float clamp_float = MathHelper.clamp_float(creeperFlashIntensity, 0.0f, 1.0f);
        final float n3 = clamp_float * clamp_float;
        final float n4 = n3 * n3;
        final float n5 = (1.0f + n4 * 0.4f) * n2;
        GlStateManager.scale(n5, (1.0f + n4 * 0.1f) / n2, n5);
    }
    
    @Override
    protected int getColorMultiplier(final EntityCreeper entityCreeper, final float n, final float n2) {
        final float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(n2);
        if ((int)(creeperFlashIntensity * 10.0f) % "  ".length() == 0) {
            return "".length();
        }
        return MathHelper.clamp_int((int)(creeperFlashIntensity * 0.2f * 255.0f), "".length(), 106 + 206 - 282 + 225) << (0x35 ^ 0x2D) | 5563852 + 4379783 - 9111167 + 15944747;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityCreeper)entity);
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        creeperTextures = new ResourceLocation(RenderCreeper.I["".length()]);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityCreeper)entityLivingBase, n);
    }
}
