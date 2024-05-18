package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderEndermite extends RenderLiving<EntityEndermite>
{
    private static final String[] I;
    private static final ResourceLocation ENDERMITE_TEXTURES;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityEndermite entityEndermite) {
        return RenderEndermite.ENDERMITE_TEXTURES;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityEndermite)entity);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("0&=\u0015\u00016&6N\u0011*7,\u0015\rk&+\u0005\u00116.,\u0015\u0011j3+\u0006", "DCEat");
    }
    
    static {
        I();
        ENDERMITE_TEXTURES = new ResourceLocation(RenderEndermite.I["".length()]);
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntityEndermite)entityLivingBase);
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityEndermite entityEndermite) {
        return 180.0f;
    }
    
    public RenderEndermite(final RenderManager renderManager) {
        super(renderManager, new ModelEnderMite(), 0.3f);
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
