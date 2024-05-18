package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderSheep extends RenderLiving<EntitySheep>
{
    private static final String[] I;
    private static final ResourceLocation shearedSheepTextures;
    
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
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0015\f1\u0000\u0005\u0013\f:[\u0015\u000f\u001d \u0000\tN\u001a!\u0011\u0015\u0011F:\u001c\u0015\u0004\u0019g\u0004\u001e\u0006", "aiItp");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySheep)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySheep entitySheep) {
        return RenderSheep.shearedSheepTextures;
    }
    
    static {
        I();
        shearedSheepTextures = new ResourceLocation(RenderSheep.I["".length()]);
    }
    
    public RenderSheep(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerSheepWool(this));
    }
}
