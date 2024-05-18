package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderChicken extends RenderLiving<EntityChicken>
{
    private static final String[] I;
    private static final ResourceLocation chickenTextures;
    
    @Override
    protected float handleRotationFloat(final EntityChicken entityChicken, final float n) {
        return (MathHelper.sin(entityChicken.field_70888_h + (entityChicken.wingRotation - entityChicken.field_70888_h) * n) + 1.0f) * (entityChicken.field_70884_g + (entityChicken.destPos - entityChicken.field_70884_g) * n);
    }
    
    static {
        I();
        chickenTextures = new ResourceLocation(RenderChicken.I["".length()]);
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityChicken)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityChicken entityChicken) {
        return RenderChicken.chickenTextures;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("#$/\u001e&%$$E695>\u001e*x\"?\u00030<$9D#9&", "WAWjS");
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.handleRotationFloat((EntityChicken)entityLivingBase, n);
    }
    
    public RenderChicken(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
}
