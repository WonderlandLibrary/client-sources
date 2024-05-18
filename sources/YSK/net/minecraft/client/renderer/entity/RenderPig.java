package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderPig extends RenderLiving<EntityPig>
{
    private static final String[] I;
    private static final ResourceLocation pigTextures;
    
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityPig entityPig) {
        return RenderPig.pigTextures;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("5- ?63-+d&/<1?:n81,l1!?e3//", "AHXKC");
    }
    
    static {
        I();
        pigTextures = new ResourceLocation(RenderPig.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityPig)entity);
    }
    
    public RenderPig(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerSaddle(this));
    }
}
