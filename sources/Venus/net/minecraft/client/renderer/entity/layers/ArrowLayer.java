/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;

public class ArrowLayer<T extends LivingEntity, M extends PlayerModel<T>>
extends StuckInBodyLayer<T, M> {
    private final EntityRendererManager field_215336_a;
    private ArrowEntity field_229130_b_;

    public ArrowLayer(LivingRenderer<T, M> livingRenderer) {
        super(livingRenderer);
        this.field_215336_a = livingRenderer.getRenderManager();
    }

    @Override
    protected int func_225631_a_(T t) {
        return ((LivingEntity)t).getArrowCountInEntity();
    }

    @Override
    protected void func_225632_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4) {
        float f5 = MathHelper.sqrt(f * f + f3 * f3);
        this.field_229130_b_ = new ArrowEntity(entity2.world, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ());
        this.field_229130_b_.rotationYaw = (float)(Math.atan2(f, f3) * 57.2957763671875);
        this.field_229130_b_.rotationPitch = (float)(Math.atan2(f2, f5) * 57.2957763671875);
        this.field_229130_b_.prevRotationYaw = this.field_229130_b_.rotationYaw;
        this.field_229130_b_.prevRotationPitch = this.field_229130_b_.rotationPitch;
        this.field_215336_a.renderEntityStatic(this.field_229130_b_, 0.0, 0.0, 0.0, 0.0f, f4, matrixStack, iRenderTypeBuffer, n);
    }
}

