package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderGhast extends RenderLiving<EntityGhast>
{
    private static final ResourceLocation ghastTextures;
    private static final String[] I;
    private static final ResourceLocation ghastShootingTextures;
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGhast)entityLivingBase, n);
    }
    
    @Override
    protected void preRenderCallback(final EntityGhast entityGhast, final float n) {
        final float n2 = 1.0f;
        final float n3 = (8.0f + n2) / 2.0f;
        final float n4 = (8.0f + 1.0f / n2) / 2.0f;
        GlStateManager.scale(n4, n3, n4);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityGhast)entity);
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        ghastTextures = new ResourceLocation(RenderGhast.I["".length()]);
        ghastShootingTextures = new ResourceLocation(RenderGhast.I[" ".length()]);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(">!<'\u00108!7|\u0000$0-'\u001ce#,2\u0016>k#;\u000490j#\u000b-", "JDDSe");
        RenderGhast.I[" ".length()] = I("2&5>>4&>e.(7$>2i$%+82l*\"*57\u00129#),9#%!m=$,", "FCMJK");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityGhast entityGhast) {
        ResourceLocation resourceLocation;
        if (entityGhast.isAttacking()) {
            resourceLocation = RenderGhast.ghastShootingTextures;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderGhast.ghastTextures;
        }
        return resourceLocation;
    }
    
    public RenderGhast(final RenderManager renderManager) {
        super(renderManager, new ModelGhast(), 0.5f);
    }
}
