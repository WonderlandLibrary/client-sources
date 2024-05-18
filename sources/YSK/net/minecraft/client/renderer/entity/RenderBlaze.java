package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderBlaze extends RenderLiving<EntityBlaze>
{
    private static final String[] I;
    private static final ResourceLocation blazeTextures;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("0\u000e\u0014\u0017,6\u000e\u001fL<*\u001f\u0005\u0017 k\t\u0000\u0002#!E\u001c\r>", "DklcY");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityBlaze entityBlaze) {
        return RenderBlaze.blazeTextures;
    }
    
    public RenderBlaze(final RenderManager renderManager) {
        super(renderManager, new ModelBlaze(), 0.5f);
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityBlaze)entity);
    }
    
    static {
        I();
        blazeTextures = new ResourceLocation(RenderBlaze.I["".length()]);
    }
}
