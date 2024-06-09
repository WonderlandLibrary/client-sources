/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class RenderEnderman
extends RenderLiving {
    private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
    private ModelEnderman endermanModel = (ModelEnderman)this.mainModel;
    private Random rnd = new Random();
    private static final String __OBFID = "CL_00000989";

    public RenderEnderman(RenderManager p_i46182_1_) {
        super(p_i46182_1_, new ModelEnderman(0.0f), 0.5f);
        this.addLayer(new LayerEndermanEyes(this));
        this.addLayer(new LayerHeldBlock(this));
    }

    public void doRender(EntityEnderman p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.endermanModel.isCarrying = p_76986_1_.func_175489_ck().getBlock().getMaterial() != Material.air;
        this.endermanModel.isAttacking = p_76986_1_.isScreaming();
        if (p_76986_1_.isScreaming()) {
            double var10 = 0.02;
            p_76986_2_ += this.rnd.nextGaussian() * var10;
            p_76986_6_ += this.rnd.nextGaussian() * var10;
        }
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected ResourceLocation func_180573_a(EntityEnderman p_180573_1_) {
        return endermanTextures;
    }

    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.func_180573_a((EntityEnderman)p_110775_1_);
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}

