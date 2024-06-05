package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderSpider extends RenderLiving
{
    private static final ResourceLocation spiderTextures;
    
    public RenderSpider(final RenderManager p_i46139_1_) {
        super(p_i46139_1_, new ModelSpider(), 1.0f);
        this.addLayer(new LayerSpiderEyes(this));
    }
    
    protected float getDeathMaxRotation(final EntitySpider p_77037_1_) {
        return 180.0f;
    }
    
    protected ResourceLocation getEntityTexture(final EntitySpider p_110775_1_) {
        return RenderSpider.spiderTextures;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
        return this.getDeathMaxRotation((EntitySpider)p_77037_1_);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySpider)p_110775_1_);
    }
    
    static {
        spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
    }
}
