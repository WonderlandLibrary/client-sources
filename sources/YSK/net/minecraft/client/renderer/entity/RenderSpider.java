package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderSpider<T extends EntitySpider> extends RenderLiving<T>
{
    private static final ResourceLocation spiderTextures;
    private static final String[] I;
    
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase entityLivingBase) {
        return this.getDeathMaxRotation((EntitySpider)entityLivingBase);
    }
    
    public RenderSpider(final RenderManager renderManager) {
        super(renderManager, new ModelSpider(), 1.0f);
        this.addLayer(new LayerSpiderEyes(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final T t) {
        return RenderSpider.spiderTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntitySpider)entity);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0019\u0002\t\u001f\u000f\u001f\u0002\u0002D\u001f\u0003\u0013\u0018\u001f\u0003B\u0014\u0001\u0002\u001e\b\u0015^\u0018\n\u0004\u0003\u0014\u0019T\u001d\t\u0016", "mgqkz");
    }
    
    static {
        I();
        spiderTextures = new ResourceLocation(RenderSpider.I["".length()]);
    }
    
    @Override
    protected float getDeathMaxRotation(final T t) {
        return 180.0f;
    }
}
