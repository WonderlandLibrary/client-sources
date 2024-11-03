package dev.star.utils.addons.waveycapes.renderlayers;

import dev.star.utils.addons.waveycapes.CapeMovement;
import dev.star.utils.addons.waveycapes.config.Config;
import dev.star.utils.addons.waveycapes.sim.StickSimulation;
import dev.star.utils.addons.waveycapes.util.*;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;

public class SmoothCapeRenderer {
    public void renderSmoothCape(CustomCapeRenderLayer layer, AbstractClientPlayer abstractClientPlayer, float delta) {
        WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        Matrix4f oldPositionMatrix = null;
        for (int part = 0; part < 16; ++part) {
            this.modifyPoseStack(layer, poseStack, abstractClientPlayer, delta, part);
            if (oldPositionMatrix == null) {
                oldPositionMatrix = poseStack.last().pose();
            }
            if (part == 0) {
                SmoothCapeRenderer.addTopVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, 0.3f, 0.0f, 0.0f, -0.3f, 0.0f, -0.06f, part);
            }
            if (part == 15) {
                SmoothCapeRenderer.addBottomVertex(worldrenderer, poseStack.last().pose(), poseStack.last().pose(), 0.3f, (float)(part + 1) * 0.06f, 0.0f, -0.3f, (float)(part + 1) * 0.06f, -0.06f, part);
            }
            SmoothCapeRenderer.addLeftVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, -0.3f, (float)(part + 1) * 0.06f, 0.0f, -0.3f, (float)part * 0.06f, -0.06f, part);
            SmoothCapeRenderer.addRightVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, 0.3f, (float)(part + 1) * 0.06f, 0.0f, 0.3f, (float)part * 0.06f, -0.06f, part);
            SmoothCapeRenderer.addBackVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, 0.3f, (float)(part + 1) * 0.06f, -0.06f, -0.3f, (float)part * 0.06f, -0.06f, part);
            SmoothCapeRenderer.addFrontVertex(worldrenderer, oldPositionMatrix, poseStack.last().pose(), 0.3f, (float)(part + 1) * 0.06f, 0.0f, -0.3f, (float)part * 0.06f, 0.0f, part);
            oldPositionMatrix = poseStack.last().pose();
            poseStack.popPose();
        }
        Tessellator.getInstance().draw();
    }

    void modifyPoseStack(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float h, int part) {
        if (Config.capeMovement == CapeMovement.BASIC_SIMULATION) {
            this.modifyPoseStackSimulation(layer, poseStack, abstractClientPlayer, h, part);
            return;
        }
        this.modifyPoseStackVanilla(layer, poseStack, abstractClientPlayer, h, part);
    }

    private void modifyPoseStackSimulation(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float delta, int part) {
        StickSimulation simulation = abstractClientPlayer.stickSimulation;
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.125);
        float z = simulation.points.get(part).getLerpX(delta) - simulation.points.get(0).getLerpX(delta);
        if (z > 0.0f) {
            z = 0.0f;
        }
        float y2 = simulation.points.get(0).getLerpY(delta) - (float)part - simulation.points.get(part).getLerpY(delta);
        float sidewaysRotationOffset = 0.0f;
        float partRotation = (float)(-Math.atan2(y2, z));
        if ((partRotation = Math.max(partRotation, 0.0f)) != 0.0f) {
            partRotation = (float)(Math.PI - (double)partRotation);
        }
        partRotation = (float)((double)partRotation * 57.2958);
        partRotation *= 2.0f;
        float height = 0.0f;
        if (abstractClientPlayer.isSneaking()) {
            height += 25.0f;
            poseStack.translate(0.0, 0.15f, 0.0);
        }
        float naturalWindSwing = layer.getNatrualWindSwing(part);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(6.0f + height + naturalWindSwing));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(sidewaysRotationOffset / 2.0f));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - sidewaysRotationOffset / 2.0f));
        poseStack.translate(0.0, y2 / 16.0f, z / 16.0f);
        poseStack.translate(0.0, 0.03, -0.03);
        poseStack.translate(0.0, (float)part * 1.0f / 16.0f, part * 0 / 16);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-partRotation));
        poseStack.translate(0.0, (float)(-part) * 1.0f / 16.0f, -part * 0 / 16);
        poseStack.translate(0.0, -0.03, 0.03);
    }

    private void modifyPoseStackVanilla(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float h, int part) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.125);
        double d2 = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX) - Mth.lerp((double)h, abstractClientPlayer.prevPosX, abstractClientPlayer.posX);
        double e = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY) - Mth.lerp((double)h, abstractClientPlayer.prevPosY, abstractClientPlayer.posY);
        double m = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ) - Mth.lerp((double)h, abstractClientPlayer.prevPosZ, abstractClientPlayer.posZ);
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        double o2 = Math.sin(n * ((float)Math.PI / 180));
        double p = -Math.cos(n * ((float)Math.PI / 180));
        float height = (float)e * 10.0f;
        height = MathHelper.clamp_float(height, -6.0f, 32.0f);
        float swing = (float)(d2 * o2 + m * p) * SmoothCapeRenderer.easeOutSine(0.0625f * (float)part) * 100.0f;
        swing = MathHelper.clamp_float(swing, 0.0f, 150.0f * SmoothCapeRenderer.easeOutSine(0.0625f * (float)part));
        float sidewaysRotationOffset = (float)(d2 * p - m * o2) * 100.0f;
        sidewaysRotationOffset = MathHelper.clamp_float(sidewaysRotationOffset, -20.0f, 20.0f);
        float t2 = Mth.lerp(h, abstractClientPlayer.prevCameraYaw, abstractClientPlayer.cameraYaw);
        height = (float)((double)height + Math.sin(Mth.lerp(h, abstractClientPlayer.prevDistanceWalkedModified, abstractClientPlayer.distanceWalkedModified) * 6.0f) * 32.0 * (double)t2);
        if (abstractClientPlayer.isSneaking()) {
            height += 25.0f;
            poseStack.translate(0.0, 0.15f, 0.0);
        }
        float naturalWindSwing = layer.getNatrualWindSwing(part);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(6.0f + swing / 2.0f + height + naturalWindSwing));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(sidewaysRotationOffset / 2.0f));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - sidewaysRotationOffset / 2.0f));
    }

    private static void addBackVertex(WorldRenderer worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
            Matrix4f k2 = matrix;
            matrix = oldMatrix;
            oldMatrix = k2;
        }
        float minU = 0.015625f;
        float maxU = 0.171875f;
        float minV = 0.03125f;
        float maxV = 0.53125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 16.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y2, z1).tex(maxU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(minU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y1, z2).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addFrontVertex(WorldRenderer worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
            Matrix4f k2 = matrix;
            matrix = oldMatrix;
            oldMatrix = k2;
        }
        float minU = 0.1875f;
        float maxU = 0.34375f;
        float minV = 0.03125f;
        float maxV = 0.53125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 16.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y1, z1).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y2, z2).tex(minU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y2, z2).tex(maxU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addLeftVertex(WorldRenderer worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float minU = 0.0f;
        float maxU = 0.015625f;
        float minV = 0.03125f;
        float maxV = 0.53125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 16.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z2).tex(minU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(maxU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addRightVertex(WorldRenderer worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float minU = 0.171875f;
        float maxU = 0.1875f;
        float minV = 0.03125f;
        float maxV = 0.53125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 16.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(maxU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z2).tex(minU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addBottomVertex(WorldRenderer worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float minU = 0.171875f;
        float maxU = 0.328125f;
        float minV = 0.0f;
        float maxV = 0.03125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 16.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y2, z2).tex(maxU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z2).tex(minU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z1).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static WorldRenderer vertex(WorldRenderer worldrenderer, Matrix4f matrix4f, float f, float g2, float h) {
        Vector4f vector4f = new Vector4f(f, g2, h, 1.0f);
        vector4f.transform(matrix4f);
        worldrenderer.pos(vector4f.x(), vector4f.y(), vector4f.z());
        return worldrenderer;
    }

    private static void addTopVertex(WorldRenderer worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float minU = 0.015625f;
        float maxU = 0.171875f;
        float minV = 0.0f;
        float maxV = 0.03125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 16.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y2, z1).tex(maxU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(minU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, minV += vPerPart * (float)part).normal(0.0f, 1.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y1, z2).tex(maxU, minV).normal(0.0f, 1.0f, 0.0f).endVertex();
    }

    private static float easeOutSine(float x2) {
        return (float)Math.sin((double)x2 * Math.PI / 2.0);
    }
}

