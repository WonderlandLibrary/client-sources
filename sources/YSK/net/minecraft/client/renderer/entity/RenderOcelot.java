package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderOcelot extends RenderLiving<EntityOcelot>
{
    private static final ResourceLocation ocelotTextures;
    private static final ResourceLocation siameseOcelotTextures;
    private static final String[] I;
    private static final ResourceLocation redOcelotTextures;
    private static final ResourceLocation blackOcelotTextures;
    
    @Override
    protected void preRenderCallback(final EntityOcelot entityOcelot, final float n) {
        super.preRenderCallback(entityOcelot, n);
        if (entityOcelot.isTamed()) {
            GlStateManager.scale(0.8f, 0.8f, 0.8f);
        }
    }
    
    public RenderOcelot(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    static {
        I();
        blackOcelotTextures = new ResourceLocation(RenderOcelot.I["".length()]);
        ocelotTextures = new ResourceLocation(RenderOcelot.I[" ".length()]);
        redOcelotTextures = new ResourceLocation(RenderOcelot.I["  ".length()]);
        siameseOcelotTextures = new ResourceLocation(RenderOcelot.I["   ".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityOcelot entityOcelot) {
        switch (entityOcelot.getTameSkin()) {
            default: {
                return RenderOcelot.ocelotTextures;
            }
            case 1: {
                return RenderOcelot.blackOcelotTextures;
            }
            case 2: {
                return RenderOcelot.redOcelotTextures;
            }
            case 3: {
                return RenderOcelot.siameseOcelotTextures;
            }
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x23 ^ 0x27])["".length()] = I("\u0013\f\u0002\u0017\u0013\u0015\f\tL\u0003\t\u001d\u0013\u0017\u001fH\n\u001b\u0017I\u0005\u0005\u001b\u0000\rI\u0019\u0014\u0004", "gizcf");
        RenderOcelot.I[" ".length()] = I("\u0000\u0013\u0017 \u0013\u0006\u0013\u001c{\u0003\u001a\u0002\u0006 \u001f[\u0015\u000e I\u001b\u0015\n8\t\u0000X\u001f:\u0001", "tvoTf");
        RenderOcelot.I["  ".length()] = I("'.3\"6!.8y&=?\"\":|(*\"l!./x3=,", "SKKVC");
        RenderOcelot.I["   ".length()] = I("<1\u0014\u000e\u0005:1\u001fU\u0015& \u0005\u000e\tg7\r\u000e_;=\r\u0017\u0015;1B\n\u001e/", "HTlzp");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityOcelot)entity);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityOcelot)entityLivingBase, n);
    }
}
