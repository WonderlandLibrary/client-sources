/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import ru.govno.client.module.modules.ESP;

public class ModelEnderCrystal
extends ModelBase {
    private final ModelRenderer cube;
    private final ModelRenderer glass = new ModelRenderer(this, "glass");
    private ModelRenderer base;

    public ModelEnderCrystal(float p_i1170_1_, boolean renderBase) {
        this.glass.setTextureOffset(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        if (renderBase) {
            this.base = new ModelRenderer(this, "base");
            this.base.setTextureOffset(0, 16).addBox(-6.0f, 0.0f, -6.0f, 12, 4, 12);
        }
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean improve = ESP.get.crystalImprove();
        if (improve) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0, 0.002, 2.0);
            this.glass.render(scale);
            GlStateManager.rotate(limbSwingAmount * 1.5f, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(0.5, 1.0, 0.5);
            this.glass.render(scale);
            GlStateManager.rotate(-limbSwingAmount * 3.0f, 0.0f, 1.0f, 0.0f);
            this.glass.render(scale);
            GlStateManager.scale(2.0f, 1.0f, 2.0f);
            GlStateManager.rotate(limbSwingAmount * 1.5f, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(1.0f, 1000.0f, 1.0f);
            GlStateManager.translate(0.0, 0.35, 0.0);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(0.0, (double)ageInTicks - 0.15, 0.0);
            GlStateManager.rotate(limbSwingAmount * 4.0f, 0.0f, 1.0f, 1.0f);
            this.cube.render(scale);
            this.glass.render(scale);
            GlStateManager.scale(1.2, 1.2, 1.2);
            GlStateManager.rotate(-limbSwingAmount * 6.0f, 0.0f, 1.0f, 1.0f);
            this.glass.render(scale);
            GlStateManager.scale(1.2, 1.2, 1.2);
            GlStateManager.rotate(-limbSwingAmount * 3.0f, 1.0f, 1.0f, 0.0f);
            this.glass.render(scale);
            GlStateManager.popMatrix();
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.translate(0.0f, -0.5f, 0.0f);
        if (this.base != null) {
            this.base.render(scale);
        }
        GlStateManager.rotate(limbSwingAmount, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.8f + ageInTicks, 0.0f);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        this.glass.render(scale);
        float f = 0.875f;
        GlStateManager.scale(0.875f, 0.875f, 0.875f);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.rotate(limbSwingAmount, 0.0f, 1.0f, 0.0f);
        this.glass.render(scale);
        GlStateManager.scale(0.875f, 0.875f, 0.875f);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.rotate(limbSwingAmount, 0.0f, 1.0f, 0.0f);
        this.cube.render(scale);
        GlStateManager.popMatrix();
    }
}

