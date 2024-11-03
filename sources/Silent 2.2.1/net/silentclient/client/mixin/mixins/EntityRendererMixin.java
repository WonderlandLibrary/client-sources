package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.event.impl.EventCameraRotation;
import net.silentclient.client.event.impl.EventPlayerHeadRotation;
import net.silentclient.client.event.impl.EventRender3D;
import net.silentclient.client.event.impl.EventZoomFov;
import net.silentclient.client.gui.notification.NotificationManager;
import net.silentclient.client.mixin.ducks.EntityRendererExt;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.mods.render.NewMotionBlurMod;
import net.silentclient.client.mods.settings.RenderMod;
import net.silentclient.client.utils.CloudRenderer;
import net.silentclient.client.utils.HUDCaching;
import net.silentclient.client.utils.OptifinePatch;
import net.silentclient.client.utils.animations.SneakHandler;
import net.silentclient.client.utils.culling.EntityCulling;
import net.silentclient.client.utils.shader.MotionBlurUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin implements EntityRendererExt {
    private float rotationYaw;
    private float prevRotationYaw;
    private float rotationPitch;
    private float prevRotationPitch;
    @Unique
    private float partialTicks;

    @Inject(method = "orientCamera", at = @At("HEAD"))
    public void orientCamera(float partialTicks, CallbackInfo ci) {
        this.partialTicks = partialTicks;
        rotationYaw = mc.getRenderViewEntity().rotationYaw;
        prevRotationYaw = mc.getRenderViewEntity().prevRotationYaw;
        rotationPitch = mc.getRenderViewEntity().rotationPitch;
        prevRotationPitch = mc.getRenderViewEntity().prevRotationPitch;
        float roll = 0;

        EventCameraRotation event = new EventCameraRotation(rotationYaw, rotationPitch, roll);
        event.call();

        rotationYaw = event.getYaw();
        rotationPitch = event.getPitch();
        roll = event.getRoll();

        prevRotationYaw = event.getYaw();
        prevRotationPitch = event.getPitch();
        GlStateManager.rotate(event.getRoll(), 0, 0, 1);
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float orientCamera(Entity entity) {
        if (mc.getRenderViewEntity() != mc.thePlayer) return entity.getEyeHeight();
        return SneakHandler.getInstance().getEyeHeight(partialTicks);
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"))
    public float getRotationYaw(Entity entity) {
        return rotationYaw;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"))
    public float getPrevRotationYaw(Entity entity) {
        return prevRotationYaw;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"))
    public float getRotationPitch(Entity entity) {
        return rotationPitch;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"))
    public float getPrevRotationPitch(Entity entity) {
        return prevRotationPitch;
    }

    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"))
    public void updateCameraAndRender(EntityPlayerSP entityPlayerSP, float yaw, float pitch) {
        EventPlayerHeadRotation event = new EventPlayerHeadRotation(yaw, pitch);
        event.call();
        yaw = event.getYaw();
        pitch = event.getPitch();

        if(!event.isCancelable()) {
            entityPlayerSP.setAngles(yaw, pitch);
        }
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 18))
    public void render3DEvent(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        EventRender3D event = new EventRender3D(partialTicks);
        event.call();
    }

    @Shadow protected abstract void loadShader(ResourceLocation resourceLocationIn);

    @Shadow private Minecraft mc;
    
    @Shadow private ShaderGroup theShaderGroup;

    @Shadow private boolean useShader;

    @Shadow protected abstract void setupViewBobbing(float partialTicks);

    @Override
    public void silent$loadShader(StaticResourceLocation location) {
        this.loadShader(location.getLocation());
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V"))
    private void shouldPerformCulling(CallbackInfo ci) {
        EntityCulling.shouldPerformCulling = true;
    }

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;F)V", shift = At.Shift.AFTER))
    private void shouldNotPerformCulling(CallbackInfo ci) {
        EntityCulling.shouldPerformCulling = false;
    }

    @Inject(method = "getFOVModifier", at = @At("RETURN"), cancellable = true)
    public void getFOVModifier(float partialTicks, boolean useFOVSetting, CallbackInfoReturnable<Float> cir) {
        EventZoomFov event = new EventZoomFov(cir.getReturnValue());
        event.call();

        cir.setReturnValue(event.getFov());
    }

    @Inject(method = "hurtCameraEffect", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/util/MathHelper;sin(F)F"), cancellable = true)
    public void cancelWorldRotation(float partialTicks, CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getOldAnimationsMod().isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, "No Shaking").getValBoolean()) {
            ci.cancel();
        }
    }

    @Redirect(method = "hurtCameraEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 2))
    public void changeIntensity(float angle, float x, float y, float z) {
        if(!Client.getInstance().getModInstances().getOldAnimationsMod().isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, "No Shaking").getValBoolean()) {
            GlStateManager.rotate(angle, x, y, z);
            return;
        }
        float intensity = Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, "Shaking Intensity").getValFloat();
        GlStateManager.rotate(angle * (intensity / 14), x, y, z);
    }

    @Redirect(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V"))
    public void modelBobbing(EntityRenderer instance, float f) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Model Bobbing Only").getValBoolean()) {
            this.setupViewBobbing(f);
        }
    }

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader" + "/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE))
    public void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci) {
        ShaderGroup colorSaturation = Client.getInstance().getModInstances().getColorSaturation().getShader();
        if (colorSaturation != null) {
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            colorSaturation.loadShaderGroup(partialTicks);
            GlStateManager.popMatrix();
        }

        List<ShaderGroup> shaders = new ArrayList<ShaderGroup>();

        if (this.theShaderGroup != null && this.useShader) {
            shaders.add(this.theShaderGroup);
        }

        ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();

        if(Client.getInstance().getModInstances().getModByClass(NewMotionBlurMod.class).isEnabled() && !Client.getInstance().getModInstances().getModByClass(NewMotionBlurMod.class).isForceDisabled()) {
            if (motionBlur != null){
                if(OptifinePatch.needPatch()) {
                    OptifinePatch.patch();
                }
                shaders.add(motionBlur);
            }

            for (ShaderGroup shader : shaders){
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                shader.loadShaderGroup(partialTicks);
                GlStateManager.popMatrix();
            }
        }
    }

    @Inject(method = "updateCameraAndRender", at = @At("TAIL"))
    public void notificationRenderer(float partialTicks, long nanoTime, CallbackInfo ci) {
        NotificationManager.render();
    }

    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V"))
    public void renderCachedHUD(GuiIngame guiIngame, float partialTicks) {
        HUDCaching.renderCachedHud((EntityRenderer) (Object) this, guiIngame, partialTicks);
    }

    //#if MC==10809
    @Inject(method = "renderStreamIndicator", at = @At("HEAD"), cancellable = true)
    private void silent$cancelStreamIndicator(CallbackInfo ci) {
        ci.cancel();
    }
    //#endif

    @Inject(
            method = "renderWorldPass",
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/util/EnumWorldBlockLayer;TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;")),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I",
                    ordinal = 0
            )
    )
    private void silent$enablePolygonOffset(CallbackInfo ci) {
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(-0.325F, -0.325F);
    }

    @Inject(
            method = "renderWorldPass",
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/util/EnumWorldBlockLayer;TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;")),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void silent$disablePolygonOffset(CallbackInfo ci) {
        GlStateManager.disablePolygonOffset();
    }

    @Redirect(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/culling/ICamera;setPosition(DDD)V"))
    public void initFrustum(ICamera instance, double v1, double v2, double v3) {
        CloudRenderer.setFrustum((Frustum) instance);
        instance.setPosition(v1, v2, v3);
    }
}
