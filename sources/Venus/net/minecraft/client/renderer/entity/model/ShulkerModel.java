/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.util.math.MathHelper;

public class ShulkerModel<T extends ShulkerEntity>
extends SegmentedModel<T> {
    private final ModelRenderer base;
    private final ModelRenderer lid = new ModelRenderer(64, 64, 0, 0);
    private final ModelRenderer head;

    public ShulkerModel() {
        super(RenderType::getEntityCutoutNoCullZOffset);
        this.base = new ModelRenderer(64, 64, 0, 28);
        this.head = new ModelRenderer(64, 64, 0, 52);
        this.lid.addBox(-8.0f, -16.0f, -8.0f, 16.0f, 12.0f, 16.0f);
        this.lid.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.base.addBox(-8.0f, -8.0f, -8.0f, 16.0f, 8.0f, 16.0f);
        this.base.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.head.addBox(-3.0f, 0.0f, -3.0f, 6.0f, 6.0f, 6.0f);
        this.head.setRotationPoint(0.0f, 12.0f, 0.0f);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = f3 - (float)((ShulkerEntity)t).ticksExisted;
        float f7 = (0.5f + ((ShulkerEntity)t).getClientPeekAmount(f6)) * (float)Math.PI;
        float f8 = -1.0f + MathHelper.sin(f7);
        float f9 = 0.0f;
        if (f7 > (float)Math.PI) {
            f9 = MathHelper.sin(f3 * 0.1f) * 0.7f;
        }
        this.lid.setRotationPoint(0.0f, 16.0f + MathHelper.sin(f7) * 8.0f + f9, 0.0f);
        this.lid.rotateAngleY = ((ShulkerEntity)t).getClientPeekAmount(f6) > 0.3f ? f8 * f8 * f8 * f8 * (float)Math.PI * 0.125f : 0.0f;
        this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.head.rotateAngleY = (((ShulkerEntity)t).rotationYawHead - 180.0f - ((ShulkerEntity)t).renderYawOffset) * ((float)Math.PI / 180);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.base, this.lid);
    }

    public ModelRenderer getBase() {
        return this.base;
    }

    public ModelRenderer getLid() {
        return this.lid;
    }

    public ModelRenderer getHead() {
        return this.head;
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((ShulkerEntity)entity2), f, f2, f3, f4, f5);
    }
}

