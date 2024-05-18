package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderSnowMan extends RenderLiving<EntitySnowman>
{
    private static final ResourceLocation snowManTextures;
    private static final String[] I;
    
    static {
        I();
        snowManTextures = new ResourceLocation(RenderSnowMan.I["".length()]);
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntitySnowman entitySnowman) {
        return RenderSnowMan.snowManTextures;
    }
    
    @Override
    public ModelSnowMan getMainModel() {
        return (ModelSnowMan)super.getMainModel();
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySnowman)entity);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0013?\u001e\u0013\u0012\u0015?\u0015H\u0002\t.\u000f\u0013\u001eH)\b\b\u0010\n;\bI\u0017\t=", "gZfgg");
    }
    
    public RenderSnowMan(final RenderManager renderManager) {
        super(renderManager, new ModelSnowMan(), 0.5f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerSnowmanHead(this));
    }
}
