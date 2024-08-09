/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;

public class PlayerModel<T extends LivingEntity>
extends BipedModel<T> {
    private List<ModelRenderer> modelRenderers = Lists.newArrayList();
    public final ModelRenderer bipedLeftArmwear;
    public final ModelRenderer bipedRightArmwear;
    public final ModelRenderer bipedLeftLegwear;
    public final ModelRenderer bipedRightLegwear;
    public final ModelRenderer bipedBodyWear;
    private final ModelRenderer bipedCape;
    private final ModelRenderer bipedDeadmau5Head;
    private final boolean smallArms;

    public PlayerModel(float f, boolean bl) {
        super(RenderType::getEntityTranslucent, f, 0.0f, 64, 64);
        this.smallArms = bl;
        this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
        this.bipedDeadmau5Head.addBox(-3.0f, -6.0f, -1.0f, 6.0f, 6.0f, 1.0f, f);
        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0f, 0.0f, -1.0f, 10.0f, 16.0f, 1.0f, f);
        if (bl) {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, f);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.5f, 0.0f);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, f);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.5f, 0.0f);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, f + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.5f, 0.0f);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, f + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.5f, 10.0f);
        } else {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.0f, 0.0f);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.0f, 10.0f);
        }
        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f + 0.25f);
        this.bipedLeftLegwear.setRotationPoint(1.9f, 12.0f, 0.0f);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f + 0.25f);
        this.bipedRightLegwear.setRotationPoint(-1.9f, 12.0f, 0.0f);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, f + 0.25f);
        this.bipedBodyWear.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.bipedLeftLegwear, this.bipedRightLegwear, this.bipedLeftArmwear, this.bipedRightArmwear, this.bipedBodyWear));
    }

    public void renderEars(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2) {
        this.bipedDeadmau5Head.copyModelAngles(this.bipedHead);
        this.bipedDeadmau5Head.rotationPointX = 0.0f;
        this.bipedDeadmau5Head.rotationPointY = 0.0f;
        this.bipedDeadmau5Head.render(matrixStack, iVertexBuilder, n, n2);
    }

    public void renderCape(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2) {
        this.bipedCape.render(matrixStack, iVertexBuilder, n, n2);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        this.bipedLeftLegwear.copyModelAngles(this.bipedLeftLeg);
        this.bipedRightLegwear.copyModelAngles(this.bipedRightLeg);
        this.bipedLeftArmwear.copyModelAngles(this.bipedLeftArm);
        this.bipedRightArmwear.copyModelAngles(this.bipedRightArm);
        this.bipedBodyWear.copyModelAngles(this.bipedBody);
        if (((LivingEntity)t).getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty()) {
            if (((Entity)t).isCrouching()) {
                this.bipedCape.rotationPointZ = 1.4f;
                this.bipedCape.rotationPointY = 1.85f;
            } else {
                this.bipedCape.rotationPointZ = 0.0f;
                this.bipedCape.rotationPointY = 0.0f;
            }
        } else if (((Entity)t).isCrouching()) {
            this.bipedCape.rotationPointZ = 0.3f;
            this.bipedCape.rotationPointY = 0.8f;
        } else {
            this.bipedCape.rotationPointZ = -1.1f;
            this.bipedCape.rotationPointY = -0.85f;
        }
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
        this.bipedLeftArmwear.showModel = bl;
        this.bipedRightArmwear.showModel = bl;
        this.bipedLeftLegwear.showModel = bl;
        this.bipedRightLegwear.showModel = bl;
        this.bipedBodyWear.showModel = bl;
        this.bipedCape.showModel = bl;
        this.bipedDeadmau5Head.showModel = bl;
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        ModelRenderer modelRenderer = this.getArmForSide(handSide);
        if (this.smallArms) {
            float f = 0.5f * (float)(handSide == HandSide.RIGHT ? 1 : -1);
            modelRenderer.rotationPointX += f;
            modelRenderer.translateRotate(matrixStack);
            modelRenderer.rotationPointX -= f;
        } else {
            modelRenderer.translateRotate(matrixStack);
        }
    }

    public ModelRenderer getRandomModelRenderer(Random random2) {
        return this.modelRenderers.get(random2.nextInt(this.modelRenderers.size()));
    }

    @Override
    public void accept(ModelRenderer modelRenderer) {
        if (this.modelRenderers == null) {
            this.modelRenderers = Lists.newArrayList();
        }
        this.modelRenderers.add(modelRenderer);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((LivingEntity)entity2), f, f2, f3, f4, f5);
    }

    @Override
    public void accept(Object object) {
        this.accept((ModelRenderer)object);
    }
}

