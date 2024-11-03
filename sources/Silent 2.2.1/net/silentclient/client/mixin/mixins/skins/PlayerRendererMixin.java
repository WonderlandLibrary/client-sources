package net.silentclient.client.mixin.mixins.skins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.accessors.skins.PlayerEntityModelAccessor;
import net.silentclient.client.mixin.accessors.skins.PlayerSettings;
import net.silentclient.client.mods.render.skins.SkinUtil;
import net.silentclient.client.mods.render.skins.SkinsMod;
import net.silentclient.client.mods.render.skins.renderlayers.BodyLayerFeatureRenderer;
import net.silentclient.client.mods.render.skins.renderlayers.HeadLayerFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class PlayerRendererMixin extends RendererLivingEntity<AbstractClientPlayer> implements PlayerEntityModelAccessor {

    @Shadow
    private boolean smallArms;
    @Unique
    private HeadLayerFeatureRenderer client$headLayer;
    @Unique
    private BodyLayerFeatureRenderer client$bodyLayer;
    
    public PlayerRendererMixin(RenderManager p_i46156_1_, ModelBase p_i46156_2_, float p_i46156_3_) {
        super(p_i46156_1_, p_i46156_2_, p_i46156_3_);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void onCreate(CallbackInfo info) {
        client$headLayer = new HeadLayerFeatureRenderer((RenderPlayer)(Object)this);
        client$bodyLayer = new BodyLayerFeatureRenderer((RenderPlayer)(Object)this);
    }
    
    @Inject(method = "setModelVisibilities", at = @At("HEAD"))
    private void setModelProperties(AbstractClientPlayer abstractClientPlayer, CallbackInfo info) {
        ModelPlayer playerModel = getMainModel();
        if(!Client.getInstance().getModInstances().getModByClass(SkinsMod.class).isEnabled()) {
            playerModel.bipedHeadwear.isHidden = false;
            playerModel.bipedBodyWear.isHidden = false;
            playerModel.bipedLeftArmwear.isHidden = false;
            playerModel.bipedRightArmwear.isHidden = false;
            playerModel.bipedLeftLegwear.isHidden = false;
            playerModel.bipedRightLegwear.isHidden = false;
            return;
        }
        if(Minecraft.getMinecraft().thePlayer.getPositionVector().squareDistanceTo(abstractClientPlayer.getPositionVector()) < Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Level Of Detail Distance").getValInt()*Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Level Of Detail Distance").getValInt()) {
            playerModel.bipedHeadwear.isHidden = playerModel.bipedHeadwear.isHidden || Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Head").getValBoolean();
            playerModel.bipedBodyWear.isHidden = playerModel.bipedBodyWear.isHidden || Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Body").getValBoolean();
            playerModel.bipedLeftArmwear.isHidden = playerModel.bipedLeftArmwear.isHidden || Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Left Arm").getValBoolean();
            playerModel.bipedRightArmwear.isHidden = playerModel.bipedRightArmwear.isHidden || Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Right Arm").getValBoolean();
            playerModel.bipedLeftLegwear.isHidden = playerModel.bipedLeftLegwear.isHidden || Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Left Leg").getValBoolean();
            playerModel.bipedRightLegwear.isHidden = playerModel.bipedRightLegwear.isHidden || Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Right Leg").getValBoolean();
        } else {
            // not correct, but the correct way doesn't work cause 1.8 or whatever
            if(!abstractClientPlayer.isSpectator()) {
                playerModel.bipedHeadwear.isHidden = false;
                playerModel.bipedBodyWear.isHidden = false;
                playerModel.bipedLeftArmwear.isHidden = false;
                playerModel.bipedRightArmwear.isHidden = false;
                playerModel.bipedLeftLegwear.isHidden = false;
                playerModel.bipedRightLegwear.isHidden = false;
            }
        }
    }
    
    
    
    @Override
    public HeadLayerFeatureRenderer client$getHeadLayer() {
        return client$headLayer;
    }

    @Override
    public BodyLayerFeatureRenderer client$getBodyLayer() {
        return client$bodyLayer;
    }

    @Override
    public boolean client$hasThinArms() {
        return smallArms;
    }

    @Shadow
    public abstract ModelPlayer getMainModel();
    
    @Inject(method = "renderRightArm", at = @At("RETURN"))
    public void renderRightArm(AbstractClientPlayer player, CallbackInfo info) {
        if(!Client.getInstance().getModInstances().getModByClass(SkinsMod.class).isEnabled()) {
            return;
        }
        client$renderFirstPersonArm(player, 3);
    }

    @Inject(method = "renderLeftArm", at = @At("RETURN"))
    public void renderLeftArm(AbstractClientPlayer player, CallbackInfo info) {
        if(!Client.getInstance().getModInstances().getModByClass(SkinsMod.class).isEnabled()) {
            return;
        }
        client$renderFirstPersonArm(player, 2);
    }
    
    @Unique
    private void client$renderFirstPersonArm(AbstractClientPlayer player, int layerId) {
        ModelPlayer modelplayer = getMainModel();
        float pixelScaling = Client.getInstance().getSettingsManager().getSettingByClass(SkinsMod.class, "Voxel Size").getValFloat();
        PlayerSettings settings = (PlayerSettings) player;
        if(settings.client$getSkinLayers() == null && !client$setupModel(player, settings)) {
            return;
        }
        GlStateManager.pushMatrix();
        modelplayer.bipedRightArm.postRender(0.0625F);
        GlStateManager.scale(0.0625, 0.0625, 0.0625);
        GlStateManager.scale(pixelScaling, pixelScaling, pixelScaling);
        if(!smallArms) {
            settings.client$getSkinLayers()[layerId].x = -0.998f*16f;
        } else {
            settings.client$getSkinLayers()[layerId].x = -0.499f*16;
        }
        settings.client$getSkinLayers()[layerId].render(false);
        GlStateManager.popMatrix();
    }
    
    @Unique
    private boolean client$setupModel(AbstractClientPlayer abstractClientPlayerEntity, PlayerSettings settings) {
        if(!SkinUtil.hasCustomSkin(abstractClientPlayerEntity)) {
            return false; // default skin
        }
        SkinUtil.setup3dLayers(abstractClientPlayerEntity, settings, smallArms, null);
        return true;
    }
    
//    @Inject(method = "renderHand", at = @At("RETURN"))
//    private void renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, int i,
//            AbstractClientPlayer abstractClientPlayer, ModelPart arm, ModelPart sleeve, CallbackInfo info) {
//        if(sleeve.visible)return; // Vanilla one is active
//        PlayerSettings settings = (PlayerSettings) abstractClientPlayer;
//        float pixelScaling = 1.1f;
//        float armHeightScaling = 1.1f;
//        boolean thinArms = ((PlayerEntityModelAccessor)getModel()).hasThinArms();
//        if(settings.getSkinLayers() == null && !SkinUtil.setup3dLayers(abstractClientPlayer, settings, thinArms, getModel())) {
//            return;
//        }
//        CustomizableModelPart part = null;
//        if(sleeve == this.model.leftSleeve) {
//            part = settings.getSkinLayers()[2];
//        }else {
//            part = settings.getSkinLayers()[3];
//        }
//        part.copyFrom(arm);
//        poseStack.pushPose();
//        poseStack.scale(pixelScaling, armHeightScaling, pixelScaling);
//        part.y -= 0.6;
//        if(!thinArms) {
//            part.x -= 0.4;
//        }
//        part.render(poseStack,
//            multiBufferSource
//                    .getBuffer(RenderType.entityTranslucent(abstractClientPlayer.getSkinTextureLocation())),
//            i, OverlayTexture.NO_OVERLAY);
//        part.setPos(0, 0, 0);
//        part.setRotation(0, 0, 0);
//        poseStack.popPose();
//
//    }
    
}
