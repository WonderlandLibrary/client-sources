/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.cape.layer;

import net.minecraft.cape.layer.SmoothCapeRenderer;
import net.minecraft.cape.sim.StickSimulation;
import net.minecraft.cape.util.Mth;
import net.minecraft.cape.util.PoseStack;
import net.minecraft.cape.util.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.friendsystem.Friend;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.NoRender;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;

public class CustomCapeRenderLayer
implements LayerRenderer<AbstractClientPlayer> {
    static final int partCount = 32;
    private final RenderPlayer playerRenderer;
    private ModelRenderer[] customCape = new ModelRenderer[32];
    private final SmoothCapeRenderer smoothCapeRenderer = new SmoothCapeRenderer();
    int frame = 0;
    protected final ResourceLocation CAPE_LAYER = new ResourceLocation("vegaline/capes/default/cape1.png");

    public CustomCapeRenderLayer(RenderPlayer playerRenderer, ModelBase model) {
        this.playerRenderer = playerRenderer;
        this.buildMesh(model);
    }

    private static float easeOutSine(float x) {
        return (float)Math.sin((double)x * Math.PI / 2.0);
    }

    private void buildMesh(ModelBase model) {
        this.customCape = new ModelRenderer[32];
        for (int i = 0; i < 32; ++i) {
            ModelRenderer base = new ModelRenderer(model, 0, i);
            base.setTextureSize(64, 32);
            this.customCape[i] = base.addBox(-5.0f, i, -1.0f, 10, 1, 1);
        }
    }

    ResourceLocation getCapeOfFrame(int usedFrame) {
        return new ResourceLocation("vegaline/capes/animated/animation1/c" + usedFrame + ".png");
    }

    private int indexByTime(float percentMs) {
        int min = 1;
        int max = 60;
        return (int)MathUtils.clamp((float)(max - min) * (percentMs * (float)(1 + min / max)), (float)min, (float)max);
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer abstractClientPlayer, float paramFloat1, float paramFloat2, float deltaTick, float animationTick, float paramFloat5, float paramFloat6, float paramFloat7) {
        if (NoRender.get.actived && NoRender.get.ClientCape.bValue) {
            return;
        }
        boolean render = abstractClientPlayer instanceof EntityPlayerSP || FreeCam.fakePlayer != null && abstractClientPlayer == FreeCam.fakePlayer;
        boolean isFr = false;
        for (Friend friends : Client.friendManager.getFriends()) {
            if (friends == null) {
                return;
            }
            if (!abstractClientPlayer.getName().equals(friends.getName())) continue;
            render = true;
            isFr = true;
        }
        if (!render) {
            return;
        }
        ItemStack itemstack = abstractClientPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (Panic.stop || itemstack.getItem() == Items.ELYTRA) {
            render = false;
        }
        if (render) {
            abstractClientPlayer.updateSimulation(abstractClientPlayer, 32);
            float pc = 0.5f + abstractClientPlayer.getHealth() / abstractClientPlayer.getMaxHealth() / 2.0f;
            int ms = 2000;
            long time = System.currentTimeMillis() % (long)ms;
            float pcs = (float)time / (float)ms;
            ResourceLocation finalCape = this.getCapeOfFrame(this.indexByTime(pcs));
            if (finalCape == null) {
                return;
            }
            this.playerRenderer.bindTexture(finalCape);
            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            GL11.glEnable(3042);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            this.smoothCapeRenderer.renderSmoothCape(this, abstractClientPlayer, deltaTick);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    private void modifyPoseStack(AbstractClientPlayer abstractClientPlayer, float h, int part) {
        this.modifyPoseStackSimulation(abstractClientPlayer, h, part);
    }

    private void modifyPoseStackSimulation(AbstractClientPlayer abstractClientPlayer, float delta, int part) {
        StickSimulation simulation = abstractClientPlayer.getSimulation();
        GlStateManager.translate(0.0, 0.0, 0.125);
        float z = simulation.points.get(part).getLerpX(delta) - simulation.points.get(0).getLerpX(delta);
        if (z > 0.0f) {
            z = 0.0f;
        }
        float y = simulation.points.get(0).getLerpY(delta) - (float)part - simulation.points.get(part).getLerpY(delta);
        float sidewaysRotationOffset = 0.0f;
        float partRotation = (float)(-Math.atan2(y, z));
        if ((partRotation = Math.max(partRotation, 0.0f)) != 0.0f) {
            partRotation = (float)(Math.PI - (double)partRotation);
        }
        partRotation = (float)((double)partRotation * 57.2958);
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
        GlStateManager.translate(0.0f, y / 32.0f, z / 32.0f);
        GlStateManager.translate(0.0, 0.03, -0.03);
        GlStateManager.translate(0.0f, (float)part * 1.0f / 32.0f, 0.0f);
        GlStateManager.translate(0.0f, (float)(-part) * 1.0f / 32.0f, 0.0f);
        GlStateManager.translate(0.0, -0.03, 0.03);
    }

    void modifyPoseStackVanilla(AbstractClientPlayer abstractClientPlayer, float h, int part) {
        GlStateManager.translate(0.0, 0.0, 0.125);
        double d = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosX, abstractClientPlayer.chasingPosX) - Mth.lerp((double)h, abstractClientPlayer.prevPosX, abstractClientPlayer.posX);
        double e = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosY, abstractClientPlayer.chasingPosY) - Mth.lerp((double)h, abstractClientPlayer.prevPosY, abstractClientPlayer.posY);
        double m = Mth.lerp((double)h, abstractClientPlayer.prevChasingPosZ, abstractClientPlayer.chasingPosZ) - Mth.lerp((double)h, abstractClientPlayer.prevPosZ, abstractClientPlayer.posZ);
        float n = abstractClientPlayer.renderYawOffset;
        double o = Math.sin(n * ((float)Math.PI / 180));
        double p = -Math.cos(n * ((float)Math.PI / 180));
        float height = (float)e * 10.0f;
        height = MathHelper.clamp(height, -6.0f, 32.0f);
        float swing = (float)(d * o + m * p) * CustomCapeRenderLayer.easeOutSine(0.03125f * (float)part) * 100.0f;
        swing = MathHelper.clamp(swing, 0.0f, 150.0f * CustomCapeRenderLayer.easeOutSine(0.03125f * (float)part));
        float sidewaysRotationOffset = (float)(d * p - m * o) * 100.0f;
        sidewaysRotationOffset = MathHelper.clamp(sidewaysRotationOffset, -20.0f, 20.0f);
        float t = Mth.lerp(h, abstractClientPlayer.prevCameraYaw, abstractClientPlayer.cameraYaw);
        height = (float)((double)height + Math.sin(Mth.lerp(h, abstractClientPlayer.prevDistanceWalkedModified, abstractClientPlayer.distanceWalkedModified) * 6.0f) * 32.0 * (double)t);
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
        return 0.0f;
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    void modifyPoseStack(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float h, int part) {
        this.modifyPoseStackVanilla(layer, poseStack, abstractClientPlayer, h, part);
        this.modifyPoseStackVanillaCmodel(layer, poseStack, abstractClientPlayer, h, part);
    }

    private void modifyPoseStackSimulation(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float delta, int part) {
        StickSimulation simulation = abstractClientPlayer.getSimulation();
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.125);
        float z = simulation.points.get(part).getLerpX(delta) - simulation.points.get(0).getLerpX(delta);
        if (z > 0.0f) {
            z = 0.0f;
        }
        float y = simulation.points.get(0).getLerpY(delta) - (float)part - simulation.points.get(part).getLerpY(delta);
        float sidewaysRotationOffset = 0.0f;
        float partRotation = (float)(-Math.atan2(y, z));
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
        poseStack.translate(0.0, y / 32.0f, z / 32.0f);
        poseStack.translate(0.0, 0.03, -0.03);
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
        float swing = (float)(d * o + m * p) * CustomCapeRenderLayer.easeOutSine(0.03125f * (float)part) * 100.0f;
        swing = MathHelper.clamp(swing, 0.0f, 150.0f * CustomCapeRenderLayer.easeOutSine(0.03125f * (float)part));
        float sidewaysRotationOffset = (float)(d * p - m * o) * 100.0f;
        sidewaysRotationOffset = MathHelper.clamp(sidewaysRotationOffset, -20.0f, 20.0f);
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

    private void modifyPoseStackVanillaCmodel(CustomCapeRenderLayer layer, PoseStack poseStack, AbstractClientPlayer abstractClientPlayer, float h, int part) {
    }
}

