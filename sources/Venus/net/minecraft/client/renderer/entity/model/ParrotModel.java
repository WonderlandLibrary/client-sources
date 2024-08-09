/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.math.MathHelper;

public class ParrotModel
extends SegmentedModel<ParrotEntity> {
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer wingLeft;
    private final ModelRenderer wingRight;
    private final ModelRenderer head;
    private final ModelRenderer head2;
    private final ModelRenderer beak1;
    private final ModelRenderer beak2;
    private final ModelRenderer feather;
    private final ModelRenderer legLeft;
    private final ModelRenderer legRight;

    public ParrotModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 2, 8);
        this.body.addBox(-1.5f, 0.0f, -1.5f, 3.0f, 6.0f, 3.0f);
        this.body.setRotationPoint(0.0f, 16.5f, -3.0f);
        this.tail = new ModelRenderer(this, 22, 1);
        this.tail.addBox(-1.5f, -1.0f, -1.0f, 3.0f, 4.0f, 1.0f);
        this.tail.setRotationPoint(0.0f, 21.07f, 1.16f);
        this.wingLeft = new ModelRenderer(this, 19, 8);
        this.wingLeft.addBox(-0.5f, 0.0f, -1.5f, 1.0f, 5.0f, 3.0f);
        this.wingLeft.setRotationPoint(1.5f, 16.94f, -2.76f);
        this.wingRight = new ModelRenderer(this, 19, 8);
        this.wingRight.addBox(-0.5f, 0.0f, -1.5f, 1.0f, 5.0f, 3.0f);
        this.wingRight.setRotationPoint(-1.5f, 16.94f, -2.76f);
        this.head = new ModelRenderer(this, 2, 2);
        this.head.addBox(-1.0f, -1.5f, -1.0f, 2.0f, 3.0f, 2.0f);
        this.head.setRotationPoint(0.0f, 15.69f, -2.76f);
        this.head2 = new ModelRenderer(this, 10, 0);
        this.head2.addBox(-1.0f, -0.5f, -2.0f, 2.0f, 1.0f, 4.0f);
        this.head2.setRotationPoint(0.0f, -2.0f, -1.0f);
        this.head.addChild(this.head2);
        this.beak1 = new ModelRenderer(this, 11, 7);
        this.beak1.addBox(-0.5f, -1.0f, -0.5f, 1.0f, 2.0f, 1.0f);
        this.beak1.setRotationPoint(0.0f, -0.5f, -1.5f);
        this.head.addChild(this.beak1);
        this.beak2 = new ModelRenderer(this, 16, 7);
        this.beak2.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 2.0f, 1.0f);
        this.beak2.setRotationPoint(0.0f, -1.75f, -2.45f);
        this.head.addChild(this.beak2);
        this.feather = new ModelRenderer(this, 2, 18);
        this.feather.addBox(0.0f, -4.0f, -2.0f, 0.0f, 5.0f, 4.0f);
        this.feather.setRotationPoint(0.0f, -2.15f, 0.15f);
        this.head.addChild(this.feather);
        this.legLeft = new ModelRenderer(this, 14, 18);
        this.legLeft.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 2.0f, 1.0f);
        this.legLeft.setRotationPoint(1.0f, 22.0f, -1.05f);
        this.legRight = new ModelRenderer(this, 14, 18);
        this.legRight.addBox(-0.5f, 0.0f, -0.5f, 1.0f, 2.0f, 1.0f);
        this.legRight.setRotationPoint(-1.0f, 22.0f, -1.05f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.wingLeft, this.wingRight, this.tail, this.head, this.legLeft, this.legRight);
    }

    @Override
    public void setRotationAngles(ParrotEntity parrotEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(ParrotModel.getParrotState(parrotEntity), parrotEntity.ticksExisted, f, f2, f3, f4, f5);
    }

    @Override
    public void setLivingAnimations(ParrotEntity parrotEntity, float f, float f2, float f3) {
        this.setLivingAnimations(ParrotModel.getParrotState(parrotEntity));
    }

    public void renderOnShoulder(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, int n3) {
        this.setLivingAnimations(State.ON_SHOULDER);
        this.setRotationAngles(State.ON_SHOULDER, n3, f, f2, 0.0f, f3, f4);
        this.getParts().forEach(arg_0 -> ParrotModel.lambda$renderOnShoulder$0(matrixStack, iVertexBuilder, n, n2, arg_0));
    }

    private void setRotationAngles(State state, int n, float f, float f2, float f3, float f4, float f5) {
        this.head.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.head.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.head.rotateAngleZ = 0.0f;
        this.head.rotationPointX = 0.0f;
        this.body.rotationPointX = 0.0f;
        this.tail.rotationPointX = 0.0f;
        this.wingRight.rotationPointX = -1.5f;
        this.wingLeft.rotationPointX = 1.5f;
        switch (1.$SwitchMap$net$minecraft$client$renderer$entity$model$ParrotModel$State[state.ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                float f6 = MathHelper.cos(n);
                float f7 = MathHelper.sin(n);
                this.head.rotationPointX = f6;
                this.head.rotationPointY = 15.69f + f7;
                this.head.rotateAngleX = 0.0f;
                this.head.rotateAngleY = 0.0f;
                this.head.rotateAngleZ = MathHelper.sin(n) * 0.4f;
                this.body.rotationPointX = f6;
                this.body.rotationPointY = 16.5f + f7;
                this.wingLeft.rotateAngleZ = -0.0873f - f3;
                this.wingLeft.rotationPointX = 1.5f + f6;
                this.wingLeft.rotationPointY = 16.94f + f7;
                this.wingRight.rotateAngleZ = 0.0873f + f3;
                this.wingRight.rotationPointX = -1.5f + f6;
                this.wingRight.rotationPointY = 16.94f + f7;
                this.tail.rotationPointX = f6;
                this.tail.rotationPointY = 21.07f + f7;
                break;
            }
            case 3: {
                this.legLeft.rotateAngleX += MathHelper.cos(f * 0.6662f) * 1.4f * f2;
                this.legRight.rotateAngleX += MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
            }
            default: {
                float f8 = f3 * 0.3f;
                this.head.rotationPointY = 15.69f + f8;
                this.tail.rotateAngleX = 1.015f + MathHelper.cos(f * 0.6662f) * 0.3f * f2;
                this.tail.rotationPointY = 21.07f + f8;
                this.body.rotationPointY = 16.5f + f8;
                this.wingLeft.rotateAngleZ = -0.0873f - f3;
                this.wingLeft.rotationPointY = 16.94f + f8;
                this.wingRight.rotateAngleZ = 0.0873f + f3;
                this.wingRight.rotationPointY = 16.94f + f8;
                this.legLeft.rotationPointY = 22.0f + f8;
                this.legRight.rotationPointY = 22.0f + f8;
            }
        }
    }

    private void setLivingAnimations(State state) {
        this.feather.rotateAngleX = -0.2214f;
        this.body.rotateAngleX = 0.4937f;
        this.wingLeft.rotateAngleX = -0.69813174f;
        this.wingLeft.rotateAngleY = (float)(-Math.PI);
        this.wingRight.rotateAngleX = -0.69813174f;
        this.wingRight.rotateAngleY = (float)(-Math.PI);
        this.legLeft.rotateAngleX = -0.0299f;
        this.legRight.rotateAngleX = -0.0299f;
        this.legLeft.rotationPointY = 22.0f;
        this.legRight.rotationPointY = 22.0f;
        this.legLeft.rotateAngleZ = 0.0f;
        this.legRight.rotateAngleZ = 0.0f;
        switch (1.$SwitchMap$net$minecraft$client$renderer$entity$model$ParrotModel$State[state.ordinal()]) {
            case 1: {
                float f = 1.9f;
                this.head.rotationPointY = 17.59f;
                this.tail.rotateAngleX = 1.5388988f;
                this.tail.rotationPointY = 22.97f;
                this.body.rotationPointY = 18.4f;
                this.wingLeft.rotateAngleZ = -0.0873f;
                this.wingLeft.rotationPointY = 18.84f;
                this.wingRight.rotateAngleZ = 0.0873f;
                this.wingRight.rotationPointY = 18.84f;
                this.legLeft.rotationPointY += 1.0f;
                this.legRight.rotationPointY += 1.0f;
                this.legLeft.rotateAngleX += 1.0f;
                this.legRight.rotateAngleX += 1.0f;
                break;
            }
            case 2: {
                this.legLeft.rotateAngleZ = -0.34906584f;
                this.legRight.rotateAngleZ = 0.34906584f;
            }
            default: {
                break;
            }
            case 4: {
                this.legLeft.rotateAngleX += 0.69813174f;
                this.legRight.rotateAngleX += 0.69813174f;
            }
        }
    }

    private static State getParrotState(ParrotEntity parrotEntity) {
        if (parrotEntity.isPartying()) {
            return State.PARTY;
        }
        if (parrotEntity.isSleeping()) {
            return State.SITTING;
        }
        return parrotEntity.isFlying() ? State.FLYING : State.STANDING;
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((ParrotEntity)entity2, f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((ParrotEntity)entity2, f, f2, f3, f4, f5);
    }

    private static void lambda$renderOnShoulder$0(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2);
    }

    public static enum State {
        FLYING,
        STANDING,
        SITTING,
        PARTY,
        ON_SHOULDER;

    }
}

