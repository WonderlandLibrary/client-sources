/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class BoatRenderer
extends EntityRenderer<BoatEntity> {
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/boat/oak.png"), new ResourceLocation("textures/entity/boat/spruce.png"), new ResourceLocation("textures/entity/boat/birch.png"), new ResourceLocation("textures/entity/boat/jungle.png"), new ResourceLocation("textures/entity/boat/acacia.png"), new ResourceLocation("textures/entity/boat/dark_oak.png")};
    protected final BoatModel modelBoat = new BoatModel();

    public BoatRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.8f;
    }

    @Override
    public void render(BoatEntity boatEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        float f3;
        matrixStack.push();
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - f));
        float f4 = (float)boatEntity.getTimeSinceHit() - f2;
        float f5 = boatEntity.getDamageTaken() - f2;
        if (f5 < 0.0f) {
            f5 = 0.0f;
        }
        if (f4 > 0.0f) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.sin(f4) * f4 * f5 / 10.0f * (float)boatEntity.getForwardDirection()));
        }
        if (!MathHelper.epsilonEquals(f3 = boatEntity.getRockingAngle(f2), 0.0f)) {
            matrixStack.rotate(new Quaternion(new Vector3f(1.0f, 0.0f, 1.0f), boatEntity.getRockingAngle(f2), true));
        }
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
        this.modelBoat.setRotationAngles(boatEntity, f2, 0.0f, -0.1f, 0.0f, 0.0f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.modelBoat.getRenderType(this.getEntityTexture(boatEntity)));
        this.modelBoat.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        if (!boatEntity.canSwim()) {
            IVertexBuilder iVertexBuilder2 = iRenderTypeBuffer.getBuffer(RenderType.getWaterMask());
            this.modelBoat.func_228245_c_().render(matrixStack, iVertexBuilder2, n, OverlayTexture.NO_OVERLAY);
        }
        matrixStack.pop();
        super.render(boatEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(BoatEntity boatEntity) {
        return BOAT_TEXTURES[boatEntity.getBoatType().ordinal()];
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((BoatEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((BoatEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

