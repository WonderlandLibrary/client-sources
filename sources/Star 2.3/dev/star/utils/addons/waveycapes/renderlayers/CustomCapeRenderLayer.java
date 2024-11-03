package dev.star.utils.addons.waveycapes.renderlayers;

import dev.star.utils.addons.waveycapes.CapeMovement;
import dev.star.utils.addons.waveycapes.CapeStyle;
import dev.star.utils.addons.waveycapes.WindMode;
import dev.star.utils.addons.waveycapes.config.Config;
import dev.star.utils.addons.waveycapes.sim.StickSimulation;
import dev.star.utils.addons.waveycapes.util.Mth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class CustomCapeRenderLayer
implements LayerRenderer<AbstractClientPlayer> {
    static final int partCount = 16;
    private ModelRenderer[] customCape = new ModelRenderer[16];
    private final RenderPlayer playerRenderer;
    private SmoothCapeRenderer smoothCapeRenderer = new SmoothCapeRenderer();

    public CustomCapeRenderLayer(RenderPlayer playerRenderer, ModelBase model) {
        this.playerRenderer = playerRenderer;
        this.buildMesh(model);
    }

    private void buildMesh(ModelBase model) {
        this.customCape = new ModelRenderer[16];
        for (int i = 0; i < 16; ++i) {
            ModelRenderer base = new ModelRenderer(model, 0, i);
            base.setTextureSize(64, 32);
            this.customCape[i] = base.addBox(-5.0f, i, -1.0f, 10, 1, 1);
        }
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer abstractClientPlayer, float paramFloat1, float paramFloat2, float deltaTick, float animationTick, float paramFloat5, float paramFloat6, float paramFloat7) {
        if (abstractClientPlayer.isInvisible()) {
            return;
        }
        if (!abstractClientPlayer.hasPlayerInfo() || abstractClientPlayer.isInvisible() || !abstractClientPlayer.isWearing(EnumPlayerModelParts.CAPE) || abstractClientPlayer.getLocationCape() == null) {
            return;
        }
        if (Config.capeMovement == CapeMovement.BASIC_SIMULATION) {
            abstractClientPlayer.updateSimulation(abstractClientPlayer, 16);
        }
        this.playerRenderer.bindTexture(abstractClientPlayer.getLocationCape());
        if (Config.capeStyle == CapeStyle.SMOOTH) {
            if (abstractClientPlayer == Minecraft.getMinecraft().thePlayer) {
                this.smoothCapeRenderer.renderSmoothCape(this, abstractClientPlayer, deltaTick);
            }
        } else {
            ModelRenderer[] parts = this.customCape;
            for (int part = 0; part < 16; ++part) {
                ModelRenderer model = parts[part];
                GlStateManager.pushMatrix();
                this.modifyPoseStack(abstractClientPlayer, deltaTick, part);
                model.render(0.0625f);
                GlStateManager.popMatrix();
            }
        }
    }

    private void modifyPoseStack(AbstractClientPlayer abstractClientPlayer, float h, int part) {
        if (Config.capeMovement == CapeMovement.BASIC_SIMULATION) {
            this.modifyPoseStackSimulation(abstractClientPlayer, h, part);
            return;
        }
        this.modifyPoseStackVanilla(abstractClientPlayer, h, part);
    }

    private void modifyPoseStackSimulation(AbstractClientPlayer abstractClientPlayer, float delta, int part) {
        StickSimulation simulation = abstractClientPlayer.stickSimulation;
        GlStateManager.translate(0.0, 0.0, 0.125);
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
            GlStateManager.translate(0.0f, 0.15f, 0.0f);
        }
        float naturalWindSwing = this.getNatrualWindSwing(part);
        GlStateManager.rotate(6.0f + height + naturalWindSwing, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(sidewaysRotationOffset / 2.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-sidewaysRotationOffset / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, y2 / 16.0f, z / 16.0f);
        GlStateManager.translate(0.0, 0.03, -0.03);
        GlStateManager.translate(0.0f, (float)part * 1.0f / 16.0f, part * 0 / 16);
        GlStateManager.translate(0.0f, (float)(-part) * 1.0f / 16.0f, -part * 0 / 16);
        GlStateManager.translate(0.0, -0.03, 0.03);
    }

    void modifyPoseStackVanilla(AbstractClientPlayer abstractClientPlayer, float h, int part) {
        GlStateManager.translate(0.0, 0.0, 0.125);
        double d2 = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX) - Mth.lerp((double)h, abstractClientPlayer.prevPosX, abstractClientPlayer.posX);
        double e = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY) - Mth.lerp((double)h, abstractClientPlayer.prevPosY, abstractClientPlayer.posY);
        double m = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ) - Mth.lerp((double)h, abstractClientPlayer.prevPosZ, abstractClientPlayer.posZ);
        float n = abstractClientPlayer.prevRenderYawOffset + abstractClientPlayer.renderYawOffset - abstractClientPlayer.prevRenderYawOffset;
        double o2 = Math.sin(n * ((float)Math.PI / 180));
        double p = -Math.cos(n * ((float)Math.PI / 180));
        float height = (float)e * 10.0f;
        height = MathHelper.clamp_float(height, -6.0f, 32.0f);
        float swing = (float)(d2 * o2 + m * p) * CustomCapeRenderLayer.easeOutSine(0.0625f * (float)part) * 100.0f;
        swing = MathHelper.clamp_float(swing, 0.0f, 150.0f * CustomCapeRenderLayer.easeOutSine(0.0625f * (float)part));
        float sidewaysRotationOffset = (float)(d2 * p - m * o2) * 100.0f;
        sidewaysRotationOffset = MathHelper.clamp_float(sidewaysRotationOffset, -20.0f, 20.0f);
        float t2 = Mth.lerp(h, abstractClientPlayer.prevCameraYaw, abstractClientPlayer.cameraYaw);
        height = (float)((double)height + Math.sin(Mth.lerp(h, abstractClientPlayer.prevDistanceWalkedModified, abstractClientPlayer.distanceWalkedModified) * 6.0f) * 32.0 * (double)t2);
        if (abstractClientPlayer.isSneaking()) {
            height += 25.0f;
            GlStateManager.translate(0.0f, 0.15f, 0.0f);
        }
        float naturalWindSwing = this.getNatrualWindSwing(part);
        GlStateManager.rotate(6.0f + swing / 2.0f + height + naturalWindSwing, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(sidewaysRotationOffset / 2.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-sidewaysRotationOffset / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
    }

    float getNatrualWindSwing(int part) {
        if (Config.windMode == WindMode.WAVES) {
            long highlightedPart = System.currentTimeMillis() / 3L % 360L;
            float relativePart = (float)(part + 1) / 16.0f;
            return (float)(Math.sin(Math.toRadians(relativePart * 360.0f - (float)highlightedPart)) * 3.0);
        }
        return 0.0f;
    }

    private static float easeOutSine(float x2) {
        return (float)Math.sin((double)x2 * Math.PI / 2.0);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

