package ru.FecuritySQ.capes;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexConsumer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.render.RenderEnv;

public class CustomCapeRenderLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>  {

    private static int partCount;
    private ModelRenderer[] customCape = new ModelRenderer[partCount];
    public CustomCapeRenderLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
        partCount = 16;
        buildMesh();
    }

    private void buildMesh() {
        customCape = new ModelRenderer[partCount];
        for (int i = 0; i < partCount; i++) {
            ModelRenderer base = new ModelRenderer(64, 32, 0, i);
            this.customCape[i] = base.addBox(-5.0F, (float)i, -1.0F, 10.0F, 1.0F, 1F);
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entitylivingbaseIn.isInvisible())return;

        CapeRenderer renderer = getCapeRenderer(entitylivingbaseIn, bufferIn);
        if(renderer == null) return;
        ItemStack itemStack = entitylivingbaseIn.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (itemStack.getItem() == Items.ELYTRA)
            return;

        CapeHolder holder = (CapeHolder) entitylivingbaseIn;
        holder.updateSimulation(entitylivingbaseIn, partCount);


                ModelRenderer[] parts = customCape;
        for (int part = 0; part < partCount; part++) {
            ModelRenderer model = parts[part];
            modifyPoseStack(matrixStackIn, entitylivingbaseIn, partialTicks, part);
            renderer.render(entitylivingbaseIn, part, model, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
            matrixStackIn.pop();
        }


    }
    private static VanillaCapeRenderer vanillaCape = new VanillaCapeRenderer();
    private CapeRenderer getCapeRenderer(AbstractClientPlayerEntity abstractClientPlayer,IRenderTypeBuffer multiBufferSource) {
        if (!abstractClientPlayer.hasPlayerInfo() || abstractClientPlayer.isInvisible()
                || !abstractClientPlayer.isWearing(PlayerModelPart.CAPE)
                || abstractClientPlayer.getLocationCape() == null) {
            return null;
        }
          vanillaCape.vertexConsumer = multiBufferSource.getBuffer(RenderType.getEntitySolid(abstractClientPlayer.getLocationCape()));

            return vanillaCape;
    }

    private void renderSmoothCape(MatrixStack poseStack, IRenderTypeBuffer multiBufferSource, CapeRenderer capeRenderer, AbstractClientPlayerEntity abstractClientPlayer, float delta, int light) {
        IVertexBuilder bufferBuilder = capeRenderer.getVertexConsumer(multiBufferSource, abstractClientPlayer);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Matrix4f oldPositionMatrix = null;
        for (int part = 0; part < partCount; part++) {
            modifyPoseStack(poseStack, abstractClientPlayer, delta, part);

            if (oldPositionMatrix == null) {
                oldPositionMatrix = poseStack.getLast().getMatrix();
            }

            if (part == 0) {
                addTopVertex(bufferBuilder,poseStack.getLast().getMatrix(), oldPositionMatrix,
                        0.3F,
                        0,
                        0F,
                        -0.3F,
                        0,
                        -0.06F, part, light);
            }

            if (part == partCount - 1) {
                addBottomVertex(bufferBuilder, poseStack.getLast().getMatrix(),  poseStack.getLast().getMatrix(),
                        0.3F,
                        (part + 1) * (0.96F / partCount),
                        0F,
                        -0.3F,
                        (part + 1) * (0.96F / partCount),
                        -0.06F, part, light);
            }

            addLeftVertex(bufferBuilder, poseStack.getLast().getMatrix(), oldPositionMatrix,
                    -0.3F,
                    (part + 1) * (0.96F / partCount),
                    0F,
                    -0.3F,
                    part * (0.96F / partCount),
                    -0.06F, part, light);

            addRightVertex(bufferBuilder, poseStack.getLast().getMatrix(), oldPositionMatrix,
                    0.3F,
                    (part + 1) * (0.96F / partCount),
                    0F,
                    0.3F,
                    part * (0.96F / partCount),
                    -0.06F, part, light);

            addBackVertex(bufferBuilder, poseStack.getLast().getMatrix(), oldPositionMatrix,
                    0.3F,
                    (part + 1) * (0.96F / partCount),
                    -0.06F,
                    -0.3F,
                    part * (0.96F / partCount),
                    -0.06F, part, light);

            addFrontVertex(bufferBuilder, oldPositionMatrix, poseStack.getLast().getMatrix(),
                    0.3F,
                    (part + 1) * (0.96F / partCount),
                    0F,
                    -0.3F,
                    part * (0.96F / partCount),
                    0F, part, light);

            oldPositionMatrix = poseStack.getLast().getMatrix();
            poseStack.pop();
        }

    }

    private static void addBackVertex(IVertexBuilder bufferBuilder, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part, int light) {
        float i;
        Matrix4f k;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;

            k = matrix;
            matrix = oldMatrix;
            oldMatrix = k;
        }

        float minU = .015625F;
        float maxU = .171875F;

        float minV = .03125F;
        float maxV = .53125F;

        float deltaV = maxV - minV;
        float vPerPart = deltaV / partCount;
        maxV = minV + (vPerPart * (part + 1));
        minV = minV + (vPerPart * part);

        bufferBuilder.pos(oldMatrix, x1, y2, z1).color(1f, 1f, 1f, 1f).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z1).color(1f, 1f, 1f, 1f).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x2, y1, z2).color(1f, 1f, 1f, 1f).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x1, y1, z2).color(1f, 1f, 1f, 1f).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
    }

    private static void addFrontVertex(IVertexBuilder bufferBuilder, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part, int light) {
        float i;
        Matrix4f k;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;

            k = matrix;
            matrix = oldMatrix;
            oldMatrix = k;
        }

        float minU = .1875F;
        float maxU = .34375F;

        float minV = .03125F;
        float maxV = .53125F;

        float deltaV = maxV - minV;
        float vPerPart = deltaV / partCount;
        maxV = minV + (vPerPart * (part + 1));
        minV = minV + (vPerPart * part);

        bufferBuilder.pos(oldMatrix, x1, y1, z1).color(1f, 1f, 1f, 1f).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y1, z1).color(1f, 1f, 1f, 1f).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x2, y2, z2).color(1f, 1f, 1f, 1f).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x1, y2, z2).color(1f, 1f, 1f, 1f).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
    }

    private static void addLeftVertex(IVertexBuilder bufferBuilder, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part, int light) {
        float i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }

        float minU = 0;
        float maxU = .015625F;

        float minV = .03125F;
        float maxV = .53125F;

        float deltaV = maxV - minV;
        float vPerPart = deltaV / partCount;
        maxV = minV + (vPerPart * (part + 1));
        minV = minV + (vPerPart * part);

        bufferBuilder.pos(matrix, x2, y1, z1).color(1f, 1f, 1f, 1f).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x2, y1, z2).color(1f, 1f, 1f, 1f).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z2).color(1f, 1f, 1f, 1f).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z1).color(1f, 1f, 1f, 1f).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
    }

    private static void addRightVertex(IVertexBuilder bufferBuilder, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part, int light) {
        float i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }

        float minU = .171875F;
        float maxU = .1875F;

        float minV = .03125F;
        float maxV = .53125F;

        float deltaV = maxV - minV;
        float vPerPart = deltaV / partCount;
        maxV = minV + (vPerPart * (part + 1));
        minV = minV + (vPerPart * part);

        bufferBuilder.pos(matrix, x2, y1, z2).color(1f, 1f, 1f, 1f).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x2, y1, z1).color(1f, 1f, 1f, 1f).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z1).color(1f, 1f, 1f, 1f).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z2).color(1f, 1f, 1f, 1f).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
    }

    private static void addBottomVertex(IVertexBuilder bufferBuilder, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part, int light) {
        float i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }

        float minU = .171875F;
        float maxU = .328125F;

        float minV = 0;
        float maxV = .03125F;

        float deltaV = maxV - minV;
        float vPerPart = deltaV / partCount;
        maxV = minV + (vPerPart * (part + 1));
        minV = minV + (vPerPart * part);

        bufferBuilder.pos(oldMatrix, x1, y2, z2).color(1f, 1f, 1f, 1f).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z2).color(1f, 1f, 1f, 1f).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x2, y1, z1).color(1f, 1f, 1f, 1f).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
        bufferBuilder.pos(matrix, x1, y1, z1).color(1f, 1f, 1f, 1f).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(1, 0, 0).endVertex();
    }

    private static void addTopVertex(IVertexBuilder bufferBuilder, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part, int light) {
        float i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }

        float minU = .015625F;
        float maxU = .171875F;

        float minV = 0;
        float maxV = .03125F;

        float deltaV = maxV - minV;
        float vPerPart = deltaV / partCount;
        maxV = minV + (vPerPart * (part + 1));
        minV = minV + (vPerPart * part);

        bufferBuilder.pos(oldMatrix, x1, y2, z1).color(1f, 1f, 1f, 1f).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(0, 1, 0).endVertex();
        bufferBuilder.pos(oldMatrix, x2, y2, z1).color(1f, 1f, 1f, 1f).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(0, 1, 0).endVertex();
        bufferBuilder.pos(matrix, x2, y1, z2).color(1f, 1f, 1f, 1f).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(0, 1, 0).endVertex();
        bufferBuilder.pos(matrix, x1, y1, z2).color(1f, 1f, 1f, 1f).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(0, 1, 0).endVertex();
    }


    private void modifyPoseStack(MatrixStack poseStack, AbstractClientPlayerEntity abstractClientPlayer, float h, int part) {
       modifyPoseStackSimulation(poseStack, abstractClientPlayer, h, part);
    }


    private void modifyPoseStackSimulation(MatrixStack poseStack, AbstractClientPlayerEntity abstractClientPlayer, float delta, int part) {
        StickSimulation simulation = ((CapeHolder)abstractClientPlayer).getSimulation();
        poseStack.push();
        poseStack.translate(0.0D, 0.0D, 0.125D);

        float z = simulation.points.get(part).getLerpX(delta) - simulation.points.get(0).getLerpX(delta);
        if(z > 0) {
            z = 0;
        }
        float y = simulation.points.get(0).getLerpY(delta) - part - simulation.points.get(part).getLerpY(delta);

//        float sidewaysRotationOffset = (float) (d * p - m * o) * 100.0F;
//        sidewaysRotationOffset = Mth.clamp(sidewaysRotationOffset, -20.0F, 20.0F);
        float sidewaysRotationOffset = 0;
        float partRotation = (float) -MathHelper.atan2(y, z);
        partRotation = Math.max(partRotation, 0);
        if(partRotation != 0)
            partRotation = (float) (Math.PI-partRotation);
        partRotation *= 57.2958;
        partRotation *= 2;

        float height = 0;
        if (abstractClientPlayer.isCrouching()) {
            height += 25.0F;
            poseStack.translate(0, 0.15F, 0);
        }

        float naturalWindSwing = getNatrualWindSwing(part);


        // vanilla rotating and wind
        poseStack.rotate(Vector3f.XP.rotationDegrees(6.0F + height + naturalWindSwing));
        poseStack.rotate(Vector3f.ZP.rotationDegrees(sidewaysRotationOffset / 2.0F));
        poseStack.rotate(Vector3f.YP.rotationDegrees(180.0F - sidewaysRotationOffset / 2.0F));
        poseStack.translate(0, y/partCount, z/partCount); // movement from the simulation
        //offsetting so the rotation is on the cape part
        //float offset = (float) (part * (16 / partCount))/16; // to fold the entire cape into one position for debugging
        poseStack.translate(0, /*-offset*/ + (0.48/16) , - (0.48/16)); // (0.48/16)
        poseStack.translate(0, part * 1f/partCount, part * (0)/partCount);
        poseStack.rotate(Vector3f.XP.rotationDegrees(-partRotation)); // apply actual rotation
        // undoing the rotation
        poseStack.translate(0, -part * 1f/partCount, -part * (0)/partCount);
        poseStack.translate(0, -(0.48/16), (0.48/16));

    }

    private void modifyPoseStackVanilla(MatrixStack poseStack, AbstractClientPlayerEntity abstractClientPlayer, float h, int part) {
        poseStack.push();
        poseStack.translate(0.0D, 0.0D, 0.125D);
        double d = MathHelper.lerp(h, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX)
                - MathHelper.lerp(h, abstractClientPlayer.prevPosX, abstractClientPlayer.getPosX());
        double e = MathHelper.lerp(h, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY)
                - MathHelper.lerp(h, abstractClientPlayer.prevPosY, abstractClientPlayer.getPosY());
        double m = MathHelper.lerp(h, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ)
                - MathHelper.lerp(h, abstractClientPlayer.prevPosZ, abstractClientPlayer.getPosZ());
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        double o = MathHelper.sin(n * 0.017453292F);
        double p = -MathHelper.cos(n * 0.017453292F);
        float height = (float) e * 10.0F;
        height = MathHelper.clamp(height, -6.0F, 32.0F);
        float swing = (float) (d * o + m * p) * easeOutSine(1.0F/partCount*part)*100;
        swing = MathHelper.clamp(swing, 0.0F, 150.0F * easeOutSine(1F/partCount*part));
        float sidewaysRotationOffset = (float) (d * p - m * o) * 100.0F;
        sidewaysRotationOffset = MathHelper.clamp(sidewaysRotationOffset, -20.0F, 20.0F);
        float t = MathHelper.lerp(h, abstractClientPlayer.prevCameraYaw, abstractClientPlayer.cameraYaw);
        height += MathHelper.sin(MathHelper.lerp(h, abstractClientPlayer.prevDistanceWalkedModified, abstractClientPlayer.distanceWalkedModified) * 6.0F) * 32.0F * t;
        if (abstractClientPlayer.isCrouching()) {
            height += 25.0F;
            poseStack.translate(0, 0.15F, 0);
        }

        float naturalWindSwing = getNatrualWindSwing(part);

        poseStack.rotate(Vector3f.XP.rotationDegrees(6.0F + swing / 2.0F + height + naturalWindSwing));
        poseStack.rotate(Vector3f.ZP.rotationDegrees(sidewaysRotationOffset / 2.0F));
        poseStack.rotate(Vector3f.YP.rotationDegrees(180.0F - sidewaysRotationOffset / 2.0F));
    }

    private float getNatrualWindSwing(int part) {
        long highlightedPart = (System.currentTimeMillis() / 3) % 360;
        float relativePart = (float) (part + 1) / partCount;
        return (float) (Math.sin(Math.toRadians((relativePart) * 360 - (highlightedPart))) * 3);

//        if (WaveyCapesBase.config.windMode == WindMode.SLIGHT) {
//            return getWind(60);
//        }
    }


    private static int scale = 1000*60*60;
    private static float getWind(double posY) {
        float x = (System.currentTimeMillis()%scale)/10000f;
        float mod = MathHelper.clamp(1f/200f*(float)posY, 0f, 1f);
        return MathHelper.clamp((float) (Math.sin(2 * x) + Math.sin(Math.PI * x)) * mod, 0, 2);
    }

    private static float easeOutSine(float x) {
        return (float) Math.sin((x * Math.PI) / 2f);

    }
}