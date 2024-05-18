package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderCow extends RenderLiving<EntityCow>
{
    private static final ResourceLocation cowTextures;
    private static final String[] I;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityCow)entity);
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        cowTextures = new ResourceLocation(RenderCow.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityCow entityCow) {
        return RenderCow.cowTextures;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0004/(\u0007\u0017\u0002/#\\\u0007\u001e>9\u0007\u001b_)?\u0004M\u0013%']\u0012\u001e-", "pJPsb");
    }
    
    public RenderCow(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
}
