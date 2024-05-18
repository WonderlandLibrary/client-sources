package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderMooshroom extends RenderLiving<EntityMooshroom>
{
    private static final String[] I;
    private static final ResourceLocation mooshroomTextures;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0012\u001d9\u0010\u001d\u0014\u001d2K\r\b\f(\u0010\u0011I\u001b.\u0013G\u000b\u0017.\u0017\u0000\u0014\u0017.\tF\u0016\u0016&", "fxAdh");
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityMooshroom)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityMooshroom entityMooshroom) {
        return RenderMooshroom.mooshroomTextures;
    }
    
    public RenderMooshroom(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerMooshroomMushroom(this));
    }
    
    static {
        I();
        mooshroomTextures = new ResourceLocation(RenderMooshroom.I["".length()]);
    }
}
