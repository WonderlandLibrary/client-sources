package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderMagmaCube extends RenderLiving<EntityMagmaCube>
{
    private static final ResourceLocation magmaCubeTextures;
    private static final String[] I;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityMagmaCube)entity);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("8\u001f\u0012?->\u001f\u0019d=\"\u000e\u0003?!c\t\u0006\"5)U\u0007*?!\u001b\t>:)T\u001a%?", "LzjKX");
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderMagmaCube(final RenderManager renderManager) {
        super(renderManager, new ModelMagmaCube(), 0.25f);
    }
    
    static {
        I();
        magmaCubeTextures = new ResourceLocation(RenderMagmaCube.I["".length()]);
    }
    
    @Override
    protected void preRenderCallback(final EntityMagmaCube entityMagmaCube, final float n) {
        final int slimeSize = entityMagmaCube.getSlimeSize();
        final float n2 = 1.0f / ((entityMagmaCube.prevSquishFactor + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor) * n) / (slimeSize * 0.5f + 1.0f) + 1.0f);
        final float n3 = slimeSize;
        GlStateManager.scale(n2 * n3, 1.0f / n2 * n3, n2 * n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityMagmaCube entityMagmaCube) {
        return RenderMagmaCube.magmaCubeTextures;
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityMagmaCube)entityLivingBase, n);
    }
}
