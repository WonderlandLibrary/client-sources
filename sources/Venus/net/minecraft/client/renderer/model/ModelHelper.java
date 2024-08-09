/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ModelHelper {
    public static void func_239104_a_(ModelRenderer modelRenderer, ModelRenderer modelRenderer2, ModelRenderer modelRenderer3, boolean bl) {
        ModelRenderer modelRenderer4 = bl ? modelRenderer : modelRenderer2;
        ModelRenderer modelRenderer5 = bl ? modelRenderer2 : modelRenderer;
        modelRenderer4.rotateAngleY = (bl ? -0.3f : 0.3f) + modelRenderer3.rotateAngleY;
        modelRenderer5.rotateAngleY = (bl ? 0.6f : -0.6f) + modelRenderer3.rotateAngleY;
        modelRenderer4.rotateAngleX = -1.5707964f + modelRenderer3.rotateAngleX + 0.1f;
        modelRenderer5.rotateAngleX = -1.5f + modelRenderer3.rotateAngleX;
    }

    public static void func_239102_a_(ModelRenderer modelRenderer, ModelRenderer modelRenderer2, LivingEntity livingEntity, boolean bl) {
        ModelRenderer modelRenderer3 = bl ? modelRenderer : modelRenderer2;
        ModelRenderer modelRenderer4 = bl ? modelRenderer2 : modelRenderer;
        modelRenderer3.rotateAngleY = bl ? -0.8f : 0.8f;
        modelRenderer4.rotateAngleX = modelRenderer3.rotateAngleX = -0.97079635f;
        float f = CrossbowItem.getChargeTime(livingEntity.getActiveItemStack());
        float f2 = MathHelper.clamp((float)livingEntity.getItemInUseMaxCount(), 0.0f, f);
        float f3 = f2 / f;
        modelRenderer4.rotateAngleY = MathHelper.lerp(f3, 0.4f, 0.85f) * (float)(bl ? 1 : -1);
        modelRenderer4.rotateAngleX = MathHelper.lerp(f3, modelRenderer4.rotateAngleX, -1.5707964f);
    }

    public static <T extends MobEntity> void func_239103_a_(ModelRenderer modelRenderer, ModelRenderer modelRenderer2, T t, float f, float f2) {
        float f3 = MathHelper.sin(f * (float)Math.PI);
        float f4 = MathHelper.sin((1.0f - (1.0f - f) * (1.0f - f)) * (float)Math.PI);
        modelRenderer.rotateAngleZ = 0.0f;
        modelRenderer2.rotateAngleZ = 0.0f;
        modelRenderer.rotateAngleY = 0.15707964f;
        modelRenderer2.rotateAngleY = -0.15707964f;
        if (t.getPrimaryHand() == HandSide.RIGHT) {
            modelRenderer.rotateAngleX = -1.8849558f + MathHelper.cos(f2 * 0.09f) * 0.15f;
            modelRenderer2.rotateAngleX = -0.0f + MathHelper.cos(f2 * 0.19f) * 0.5f;
            modelRenderer.rotateAngleX += f3 * 2.2f - f4 * 0.4f;
            modelRenderer2.rotateAngleX += f3 * 1.2f - f4 * 0.4f;
        } else {
            modelRenderer.rotateAngleX = -0.0f + MathHelper.cos(f2 * 0.19f) * 0.5f;
            modelRenderer2.rotateAngleX = -1.8849558f + MathHelper.cos(f2 * 0.09f) * 0.15f;
            modelRenderer.rotateAngleX += f3 * 1.2f - f4 * 0.4f;
            modelRenderer2.rotateAngleX += f3 * 2.2f - f4 * 0.4f;
        }
        ModelHelper.func_239101_a_(modelRenderer, modelRenderer2, f2);
    }

    public static void func_239101_a_(ModelRenderer modelRenderer, ModelRenderer modelRenderer2, float f) {
        modelRenderer.rotateAngleZ += MathHelper.cos(f * 0.09f) * 0.05f + 0.05f;
        modelRenderer2.rotateAngleZ -= MathHelper.cos(f * 0.09f) * 0.05f + 0.05f;
        modelRenderer.rotateAngleX += MathHelper.sin(f * 0.067f) * 0.05f;
        modelRenderer2.rotateAngleX -= MathHelper.sin(f * 0.067f) * 0.05f;
    }

    public static void func_239105_a_(ModelRenderer modelRenderer, ModelRenderer modelRenderer2, boolean bl, float f, float f2) {
        float f3;
        float f4 = MathHelper.sin(f * (float)Math.PI);
        float f5 = MathHelper.sin((1.0f - (1.0f - f) * (1.0f - f)) * (float)Math.PI);
        modelRenderer2.rotateAngleZ = 0.0f;
        modelRenderer.rotateAngleZ = 0.0f;
        modelRenderer2.rotateAngleY = -(0.1f - f4 * 0.6f);
        modelRenderer.rotateAngleY = 0.1f - f4 * 0.6f;
        modelRenderer2.rotateAngleX = f3 = (float)(-Math.PI) / (bl ? 1.5f : 2.25f);
        modelRenderer.rotateAngleX = f3;
        modelRenderer2.rotateAngleX += f4 * 1.2f - f5 * 0.4f;
        modelRenderer.rotateAngleX += f4 * 1.2f - f5 * 0.4f;
        ModelHelper.func_239101_a_(modelRenderer2, modelRenderer, f2);
    }
}

