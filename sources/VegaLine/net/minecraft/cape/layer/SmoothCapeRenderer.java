/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.cape.layer;

import net.minecraft.cape.layer.CustomCapeRenderLayer;
import net.minecraft.cape.sim.StickSimulation;
import net.minecraft.cape.util.Matrix4f;
import net.minecraft.cape.util.Mth;
import net.minecraft.cape.util.PoseStack;
import net.minecraft.cape.util.Vector3f;
import net.minecraft.cape.util.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemAir;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.modules.NoRender;
import ru.govno.client.module.modules.WallHack;
import ru.govno.client.utils.Math.MathUtils;

public class SmoothCapeRenderer {
    public void renderSmoothCape(CustomCapeRenderLayer layer, AbstractClientPlayer abstractClientPlayer, float delta) {
        BufferBuilder worldrenderer = Tessellator.getInstance().getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        Matrix4f oldPositionMatrix = null;
        for (int part = 0; part < 32; ++part) {
            this.modifyPoseStack(layer, poseStack, abstractClientPlayer, delta, part);
            if (oldPositionMatrix == null) {
                oldPositionMatrix = poseStack.last().pose();
            }
            if (part == 0) {
                SmoothCapeRenderer.addTopVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, 0.3f, 0.0f, 0.0f, -0.3f, 0.0f, -0.06f, part);
            }
            if (part == 31) {
                SmoothCapeRenderer.addBottomVertex(worldrenderer, poseStack.last().pose(), poseStack.last().pose(), 0.3f, (float)(part + 1) * 0.03f, 0.0f, -0.3f, (float)(part + 1) * 0.03f, -0.06f, part);
            }
            SmoothCapeRenderer.addLeftVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, -0.3f, (float)(part + 1) * 0.03f, 0.0f, -0.3f, (float)part * 0.03f, -0.06f, part);
            SmoothCapeRenderer.addRightVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, 0.3f, (float)(part + 1) * 0.03f, 0.0f, 0.3f, (float)part * 0.03f, -0.06f, part);
            SmoothCapeRenderer.addBackVertex(worldrenderer, poseStack.last().pose(), oldPositionMatrix, 0.3f, (float)(part + 1) * 0.03f, -0.06f, -0.3f, (float)part * 0.03f, -0.06f, part);
            SmoothCapeRenderer.addFrontVertex(worldrenderer, oldPositionMatrix, poseStack.last().pose(), 0.3f, (float)(part + 1) * 0.03f, 0.0f, -0.3f, (float)part * 0.03f, 0.0f, part);
            oldPositionMatrix = poseStack.last().pose();
            poseStack.popPose();
        }
        if (abstractClientPlayer.isChild()) {
            GlStateManager.scale(0.5, 0.5, 0.4);
            GlStateManager.translate(0.0, abstractClientPlayer.isSneaking() ? 1.375 : 1.525, (double)0.05f);
        }
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        Runnable draw = () -> Tessellator.getInstance().vboUploader.draw(Tessellator.getInstance().worldRenderer);
        if (WallHack.get.actived) {
            WallHack.get.preRenderLivingBase(abstractClientPlayer, draw, false);
        }
        draw.run();
        if (WallHack.get.actived) {
            WallHack.get.postRenderLivingBase(abstractClientPlayer, draw, false);
        }
        Tessellator.getInstance().worldRenderer.finishDrawing();
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
    }

    void modifyPoseStack(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float h, int part) {
        this.modifyPoseStackSimulation(layer, poseStack, abstractClientPlayer, h, part);
    }

    private void modifyPoseStackSimulation(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float delta, int part) {
        Minecraft mc = Minecraft.getMinecraft();
        StickSimulation simulation = abstractClientPlayer.getSimulation();
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.1);
        double d = Mth.lerp((double)delta, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX) - Mth.lerp((double)delta, abstractClientPlayer.prevPosX, abstractClientPlayer.posX);
        double e = Mth.lerp((double)delta, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY) - Mth.lerp((double)delta, abstractClientPlayer.prevPosY, abstractClientPlayer.posY);
        double m = Mth.lerp((double)delta, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ) - Mth.lerp((double)delta, abstractClientPlayer.prevPosZ, abstractClientPlayer.posZ);
        float z = (float)((double)((simulation.points.get(part).getLerpX(delta) - simulation.points.get(0).getLerpX(delta)) * 1.4f) / (abstractClientPlayer.isChild() ? 1.0 : 1.5) * MathUtils.clamp(Math.sqrt(d * d + m * m) * 5.0, 0.3, 1.0));
        float swing = (float)(Math.abs(d) + Math.abs(m)) * SmoothCapeRenderer.easeOutSine(0.03125f * (float)part) * 50.0f;
        if (z > 0.0f) {
            z = 0.0f;
        }
        float y = (float)((double)(simulation.points.get(0).getLerpY(delta) - (float)part - simulation.points.get(part).getLerpY(delta)) / (abstractClientPlayer.isChild() ? 1.0 : 1.5) * MathUtils.clamp(Math.sqrt(d * d + m * m) * 5.0, 0.3, 1.0));
        float h = delta;
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        float partRotation = (float)(-Math.atan2(y, z));
        if ((partRotation = Math.max(partRotation, 0.0f)) != 0.0f) {
            partRotation = (float)(Math.PI - (double)partRotation);
        }
        partRotation = (float)((double)partRotation * 57.2958);
        int nst = 3500;
        float ttn = System.currentTimeMillis() % (long)nst;
        float deltaS = (ttn > (float)(nst / 2) ? (float)nst - ttn : ttn) / (float)nst;
        swing += deltaS * 1.0f * (float)part;
        float height = 0.0f;
        if (abstractClientPlayer.isSneaking()) {
            if (!abstractClientPlayer.isLay) {
                height += 25.0f;
            }
            poseStack.translate(0.0, 0.15f, 0.0);
        }
        poseStack.scale(0.875f, 1.0f, 0.875f);
        poseStack.translate(0.0, abstractClientPlayer.inventory.armorInventory.get(2).getItem() instanceof ItemAir || NoRender.get.actived && NoRender.get.ArmorLayers.bValue ? (double)0.005f : (double)-0.05f, abstractClientPlayer.inventory.armorInventory.get(2).getItem() instanceof ItemAir ? (double)0.01f : (double)0.075f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(16.0f + height + layer.getNatrualWindSwing(part) + MathUtils.clamp(swing, 0.0f, 10000.0f) / 10.0f));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(0.0f));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
        poseStack.translate(0.0, y / 32.0f, z / 32.0f);
        float offset = 0.0f;
        poseStack.translate(0.0, (double)(-offset * 3.0f) + 0.03, -0.03);
        poseStack.translate(0.0, (float)part * 1.0f / 32.0f, 0.0);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-partRotation));
        poseStack.translate(0.0, (float)(-part) * 1.0f / 32.0f, 0.0);
        poseStack.translate(0.0, -0.03, 0.03);
    }

    private void modifyPoseStackVanilla(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float h, int part) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.125);
        double d = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX) - Mth.lerp((double)h, abstractClientPlayer.prevPosX, abstractClientPlayer.posX);
        double e = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY) - Mth.lerp((double)h, abstractClientPlayer.prevPosY, abstractClientPlayer.posY);
        double m = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ) - Mth.lerp((double)h, abstractClientPlayer.prevPosZ, abstractClientPlayer.posZ);
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        double o = Math.sin(n * ((float)Math.PI / 180));
        double p = -Math.cos(n * ((float)Math.PI / 180));
        float height = (float)e * 10.0f;
        height = MathHelper.clamp(height, -6.0f, 32.0f);
        float swing = (float)(d * o + m * p) * SmoothCapeRenderer.easeOutSine(0.03125f * (float)part) * 100.0f;
        float sidewaysRotationOffset = (float)(d * p - m * o) * 100.0f;
        sidewaysRotationOffset = MathHelper.clamp(sidewaysRotationOffset, -60.0f, 60.0f);
        float t = Mth.lerp(h, abstractClientPlayer.prevCameraYaw, abstractClientPlayer.cameraYaw);
        height = (float)((double)height + Math.sin(Mth.lerp(h, abstractClientPlayer.prevDistanceWalkedModified, abstractClientPlayer.distanceWalkedModified) * 6.0f) * 32.0 * (double)t);
        if (abstractClientPlayer.isSneaking()) {
            height += 25.0f;
            poseStack.translate(0.0, 0.15f, 0.0);
        }
        float naturalWindSwing = layer.getNatrualWindSwing(part);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(6.0f + swing / 2.0f + height + naturalWindSwing));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(sidewaysRotationOffset / 2.0f));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0f - sidewaysRotationOffset / 2.0f));
    }

    private static void addBackVertex(BufferBuilder worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
            Matrix4f k = matrix;
            matrix = oldMatrix;
            oldMatrix = k;
        }
        float minU = 0.015625f;
        float maxU = 0.171875f;
        float minV = 0.03125f;
        float maxV = 0.53125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 32.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y2, z1).tex(maxU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(minU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y1, z2).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addFrontVertex(BufferBuilder worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
            Matrix4f k = matrix;
            matrix = oldMatrix;
            oldMatrix = k;
        }
        float minU = 0.1875f;
        float maxU = 0.34375f;
        float minV = 0.03125f;
        float maxV = 0.53125f;
        float deltaV = maxV - minV;
        float vPerPart = deltaV / 32.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y1, z1).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y2, z2).tex(minU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y2, z2).tex(maxU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addLeftVertex(BufferBuilder worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float vPerPart = deltaV / 32.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z2).tex(minU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(maxU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addRightVertex(BufferBuilder worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float vPerPart = deltaV / 32.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(maxU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z2).tex(minU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static void addBottomVertex(BufferBuilder worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float vPerPart = deltaV / 32.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y2, z2).tex(maxU, minV += vPerPart * (float)part).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z2).tex(minU, minV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z1).tex(minU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y1, z1).tex(maxU, maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    private static BufferBuilder vertex(BufferBuilder worldrenderer, Matrix4f matrix4f, float f, float g, float h) {
        Vector4f vector4f = new Vector4f(f, g, h, 1.0f);
        vector4f.transform(matrix4f);
        worldrenderer.pos(vector4f.x(), vector4f.y(), vector4f.z());
        return worldrenderer;
    }

    private static void addTopVertex(BufferBuilder worldrenderer, Matrix4f matrix, Matrix4f oldMatrix, float x1, float y1, float z1, float x2, float y2, float z2, int part) {
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
        float vPerPart = deltaV / 32.0f;
        maxV = minV + vPerPart * (float)(part + 1);
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x1, y2, z1).tex(maxU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, oldMatrix, x2, y2, z1).tex(minU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x2, y1, z2).tex(minU, minV += vPerPart * (float)part).normal(0.0f, 1.0f, 0.0f).endVertex();
        SmoothCapeRenderer.vertex(worldrenderer, matrix, x1, y1, z2).tex(maxU, minV).normal(0.0f, 1.0f, 0.0f).endVertex();
    }

    private static float easeOutSine(float x) {
        return (float)Math.sin((double)x * Math.PI / 2.0);
    }
}

