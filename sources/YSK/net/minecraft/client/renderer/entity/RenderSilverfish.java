package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderSilverfish extends RenderLiving<EntitySilverfish>
{
    private static final ResourceLocation silverfishTextures;
    private static final String[] I;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySilverfish entitySilverfish) {
        return RenderSilverfish.silverfishTextures;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0002\u0003;\u0013:\u0004\u00030H*\u0018\u0012*\u00136Y\u0015*\u000b9\u0013\u0014%\u000e<\u001eH3\t(", "vfCgO");
    }
    
    static {
        I();
        silverfishTextures = new ResourceLocation(RenderSilverfish.I["".length()]);
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntitySilverfish)entityLivingBase);
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderSilverfish(final RenderManager renderManager) {
        super(renderManager, new ModelSilverfish(), 0.3f);
    }
    
    @Override
    protected float getDeathMaxRotation(final EntitySilverfish entitySilverfish) {
        return 180.0f;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySilverfish)entity);
    }
}
