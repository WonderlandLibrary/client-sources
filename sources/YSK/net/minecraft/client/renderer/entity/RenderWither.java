package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderWither extends RenderLiving<EntityWither>
{
    private static final String[] I;
    private static final ResourceLocation invulnerableWitherTextures;
    private static final ResourceLocation witherTextures;
    
    static {
        I();
        invulnerableWitherTextures = new ResourceLocation(RenderWither.I["".length()]);
        witherTextures = new ResourceLocation(RenderWither.I[" ".length()]);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWither)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityWither entityWither, final double n, final double n2, final double n3, final float n4, final float n5) {
        BossStatus.setBossStatus(entityWither, " ".length() != 0);
        super.doRender(entityWither, n, n2, n3, n4, n5);
    }
    
    public RenderWither(final RenderManager renderManager) {
        super(renderManager, new ModelWither(0.0f), 1.0f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerWitherAura(this));
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
    protected void preRenderCallback(final EntityWither entityWither, final float n) {
        float n2 = 2.0f;
        final int invulTime = entityWither.getInvulTime();
        if (invulTime > 0) {
            n2 -= (invulTime - n) / 220.0f * 0.5f;
        }
        GlStateManager.scale(n2, n2, n2);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("6?*\u001a#0?!A3,.;\u001a/m-;\u001a>'(}\u0019?627\u001c\t+4$\u001b:,? \u000f4.?|\u001e8%", "BZRnV");
        RenderWither.I[" ".length()] = I(";\u000e29'=\u000e9b7!\u001f#9+`\u001c#9:*\u0019e:;;\u0003/?|?\u0005-", "OkJMR");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWither)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityWither entityWither) {
        final int invulTime = entityWither.getInvulTime();
        ResourceLocation resourceLocation;
        if (invulTime > 0 && (invulTime > (0xD7 ^ 0x87) || invulTime / (0x8B ^ 0x8E) % "  ".length() != " ".length())) {
            resourceLocation = RenderWither.invulnerableWitherTextures;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderWither.witherTextures;
        }
        return resourceLocation;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityWither)entityLivingBase, n);
    }
}
