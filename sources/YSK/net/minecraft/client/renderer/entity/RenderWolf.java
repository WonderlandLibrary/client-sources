package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderWolf extends RenderLiving<EntityWolf>
{
    private static final ResourceLocation tamedWolfTextures;
    private static final ResourceLocation wolfTextures;
    private static final ResourceLocation anrgyWolfTextures;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderWolf(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerWolfCollar(this));
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase entityLivingBase, final float n) {
        return this.handleRotationFloat((EntityWolf)entityLivingBase, n);
    }
    
    static {
        I();
        wolfTextures = new ResourceLocation(RenderWolf.I["".length()]);
        tamedWolfTextures = new ResourceLocation(RenderWolf.I[" ".length()]);
        anrgyWolfTextures = new ResourceLocation(RenderWolf.I["  ".length()]);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0003\r\u001f\u001a-\u0005\r\u0014A=\u0019\u001c\u000e\u001a!X\u001f\b\u0002>X\u001f\b\u0002>Y\u0018\t\t", "whgnX");
        RenderWolf.I[" ".length()] = I("'\u000e>'\u0019!\u000e5|\t=\u001f/'\u0015|\u001c)?\n|\u001c)?\n\f\u001f'>\t}\u001b(4", "SkFSl");
        RenderWolf.I["  ".length()] = I("#\u0014\u001a'2%\u0014\u0011|\"9\u0005\u000b'>x\u0006\r?!x\u0006\r?!\b\u0010\f45._\u0012= ", "WqbSG");
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWolf)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityWolf entityWolf, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (entityWolf.isWolfWet()) {
            final float n6 = entityWolf.getBrightness(n5) * entityWolf.getShadingWhileWet(n5);
            GlStateManager.color(n6, n6, n6);
        }
        super.doRender(entityWolf, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWolf)entity);
    }
    
    @Override
    protected float handleRotationFloat(final EntityWolf entityWolf, final float n) {
        return entityWolf.getTailRotation();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityWolf entityWolf) {
        ResourceLocation resourceLocation;
        if (entityWolf.isTamed()) {
            resourceLocation = RenderWolf.tamedWolfTextures;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (entityWolf.isAngry()) {
            resourceLocation = RenderWolf.anrgyWolfTextures;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderWolf.wolfTextures;
        }
        return resourceLocation;
    }
}
