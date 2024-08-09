/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.math.MathHelper;

public class TurtleModel<T extends TurtleEntity>
extends QuadrupedModel<T> {
    private final ModelRenderer pregnant;

    public TurtleModel(float f) {
        super(12, f, true, 120.0f, 0.0f, 9.0f, 6.0f, 120);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.headModel = new ModelRenderer(this, 3, 0);
        this.headModel.addBox(-3.0f, -1.0f, -3.0f, 6.0f, 5.0f, 6.0f, 0.0f);
        this.headModel.setRotationPoint(0.0f, 19.0f, -10.0f);
        this.body = new ModelRenderer(this);
        this.body.setTextureOffset(7, 37).addBox(-9.5f, 3.0f, -10.0f, 19.0f, 20.0f, 6.0f, 0.0f);
        this.body.setTextureOffset(31, 1).addBox(-5.5f, 3.0f, -13.0f, 11.0f, 18.0f, 3.0f, 0.0f);
        this.body.setRotationPoint(0.0f, 11.0f, -10.0f);
        this.pregnant = new ModelRenderer(this);
        this.pregnant.setTextureOffset(70, 33).addBox(-4.5f, 3.0f, -14.0f, 9.0f, 18.0f, 1.0f, 0.0f);
        this.pregnant.setRotationPoint(0.0f, 11.0f, -10.0f);
        boolean bl = true;
        this.legBackRight = new ModelRenderer(this, 1, 23);
        this.legBackRight.addBox(-2.0f, 0.0f, 0.0f, 4.0f, 1.0f, 10.0f, 0.0f);
        this.legBackRight.setRotationPoint(-3.5f, 22.0f, 11.0f);
        this.legBackLeft = new ModelRenderer(this, 1, 12);
        this.legBackLeft.addBox(-2.0f, 0.0f, 0.0f, 4.0f, 1.0f, 10.0f, 0.0f);
        this.legBackLeft.setRotationPoint(3.5f, 22.0f, 11.0f);
        this.legFrontRight = new ModelRenderer(this, 27, 30);
        this.legFrontRight.addBox(-13.0f, 0.0f, -2.0f, 13.0f, 1.0f, 5.0f, 0.0f);
        this.legFrontRight.setRotationPoint(-5.0f, 21.0f, -4.0f);
        this.legFrontLeft = new ModelRenderer(this, 27, 24);
        this.legFrontLeft.addBox(0.0f, 0.0f, -2.0f, 13.0f, 1.0f, 5.0f, 0.0f);
        this.legFrontLeft.setRotationPoint(5.0f, 21.0f, -4.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.pregnant));
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        this.legBackRight.rotateAngleX = MathHelper.cos(f * 0.6662f * 0.6f) * 0.5f * f2;
        this.legBackLeft.rotateAngleX = MathHelper.cos(f * 0.6662f * 0.6f + (float)Math.PI) * 0.5f * f2;
        this.legFrontRight.rotateAngleZ = MathHelper.cos(f * 0.6662f * 0.6f + (float)Math.PI) * 0.5f * f2;
        this.legFrontLeft.rotateAngleZ = MathHelper.cos(f * 0.6662f * 0.6f) * 0.5f * f2;
        this.legFrontRight.rotateAngleX = 0.0f;
        this.legFrontLeft.rotateAngleX = 0.0f;
        this.legFrontRight.rotateAngleY = 0.0f;
        this.legFrontLeft.rotateAngleY = 0.0f;
        this.legBackRight.rotateAngleY = 0.0f;
        this.legBackLeft.rotateAngleY = 0.0f;
        this.pregnant.rotateAngleX = 1.5707964f;
        if (!((Entity)t).isInWater() && ((Entity)t).isOnGround()) {
            float f6 = ((TurtleEntity)t).isDigging() ? 4.0f : 1.0f;
            float f7 = ((TurtleEntity)t).isDigging() ? 2.0f : 1.0f;
            float f8 = 5.0f;
            this.legFrontRight.rotateAngleY = MathHelper.cos(f6 * f * 5.0f + (float)Math.PI) * 8.0f * f2 * f7;
            this.legFrontRight.rotateAngleZ = 0.0f;
            this.legFrontLeft.rotateAngleY = MathHelper.cos(f6 * f * 5.0f) * 8.0f * f2 * f7;
            this.legFrontLeft.rotateAngleZ = 0.0f;
            this.legBackRight.rotateAngleY = MathHelper.cos(f * 5.0f + (float)Math.PI) * 3.0f * f2;
            this.legBackRight.rotateAngleX = 0.0f;
            this.legBackLeft.rotateAngleY = MathHelper.cos(f * 5.0f) * 3.0f * f2;
            this.legBackLeft.rotateAngleX = 0.0f;
        }
        this.pregnant.showModel = !this.isChild && ((TurtleEntity)t).hasEgg();
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        boolean bl = this.pregnant.showModel;
        if (bl) {
            matrixStack.push();
            matrixStack.translate(0.0, -0.08f, 0.0);
        }
        super.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
        if (bl) {
            matrixStack.pop();
        }
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((TurtleEntity)entity2), f, f2, f3, f4, f5);
    }
}

