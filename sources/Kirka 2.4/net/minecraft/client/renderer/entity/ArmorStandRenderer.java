/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRenderer
extends RendererLivingEntity {
    public static final ResourceLocation field_177103_a = new ResourceLocation("textures/entity/armorstand/wood.png");
    private static final String __OBFID = "CL_00002447";

    public ArmorStandRenderer(RenderManager p_i46195_1_) {
        super(p_i46195_1_, new ModelArmorStand(), 0.0f);
        LayerBipedArmor var2 = new LayerBipedArmor(this){
            private static final String __OBFID = "CL_00002446";

            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelArmorStandArmor(0.5f);
                this.field_177186_d = new ModelArmorStandArmor(1.0f);
            }
        };
        this.addLayer(var2);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerCustomHead(this.func_177100_a().bipedHead));
    }

    protected ResourceLocation func_177102_a(EntityArmorStand p_177102_1_) {
        return field_177103_a;
    }

    public ModelArmorStand func_177100_a() {
        return (ModelArmorStand)super.getMainModel();
    }

    protected void func_177101_a(EntityArmorStand p_177101_1_, float p_177101_2_, float p_177101_3_, float p_177101_4_) {
        GlStateManager.rotate(180.0f - p_177101_3_, 0.0f, 1.0f, 0.0f);
    }

    protected boolean func_177099_b(EntityArmorStand p_177099_1_) {
        return p_177099_1_.getAlwaysRenderNameTag();
    }

    @Override
    protected boolean canRenderName(EntityLivingBase targetEntity) {
        return this.func_177099_b((EntityArmorStand)targetEntity);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        this.func_177101_a((EntityArmorStand)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    @Override
    public ModelBase getMainModel() {
        return this.func_177100_a();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.func_177102_a((EntityArmorStand)p_110775_1_);
    }

    @Override
    protected boolean func_177070_b(Entity p_177070_1_) {
        return this.func_177099_b((EntityArmorStand)p_177070_1_);
    }

}

