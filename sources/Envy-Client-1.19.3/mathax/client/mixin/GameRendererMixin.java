package mathax.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import mathax.client.MatHax;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.render.RenderAfterWorldEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.renderer.Renderer3D;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.ghost.Reach;
import mathax.client.systems.modules.player.LiquidInteract;
import mathax.client.systems.modules.player.NoMiningTrace;
import mathax.client.systems.modules.render.*;
import mathax.client.systems.modules.world.HighwayBuilder;
import mathax.client.utils.Utils;
import mathax.client.utils.render.NametagUtils;
import mathax.client.utils.render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    MinecraftClient client;

    @Shadow
    public abstract void updateTargetedEntity(float tickDelta);

    @Shadow
    public abstract void reset();

    @Shadow
    @Final
    private Camera camera;

    @Unique
    private Renderer3D renderer;

    @Inject(method = "renderWorld", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = { "ldc=hand" }), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo info, boolean bl, Camera camera, MatrixStack matrixStack, double d, float f, float g, Matrix4f matrix4f, Matrix3f matrix3f) {
        if (!Utils.canUpdate()) return;

        client.getProfiler().push("mathax_render");

        if (renderer == null) renderer = new Renderer3D();
        Render3DEvent event = Render3DEvent.get(matrices, renderer, tickDelta, camera.getPos().x, camera.getPos().y, camera.getPos().z);

        RenderUtils.updateScreenCenter();
        NametagUtils.onRender(matrices, matrix4f);

        renderer.begin();
        MatHax.EVENT_BUS.post(event);
        renderer.render(matrices);

        RenderSystem.applyModelViewMatrix();
        client.getProfiler().pop();
    }

    @Inject(method = "renderWorld", at = @At("TAIL"))
    private void onRenderWorldTail(CallbackInfo info) {
        MatHax.EVENT_BUS.post(RenderAfterWorldEvent.get());
    }

    @Inject(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"), cancellable = true)
    private void onUpdateTargetedEntity(float tickDelta, CallbackInfo info) {
        if (Modules.get().get(NoMiningTrace.class).canWork() && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            client.getProfiler().pop();
            info.cancel();
        }
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
    private HitResult updateTargetedEntityEntityRayTraceProxy(Entity entity, double maxDistance, float tickDelta, boolean includeFluids) {
        if (Modules.get().isActive(LiquidInteract.class)) {
            HitResult result = entity.raycast(maxDistance, tickDelta, includeFluids);
            if (result.getType() != HitResult.Type.MISS) return result;

            return entity.raycast(maxDistance, tickDelta, true);
        }

        return entity.raycast(maxDistance, tickDelta, includeFluids);
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void onBobViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noHurtCam()) info.cancel();
    }

    @Inject(method = "showFloatingItem", at = @At("HEAD"), cancellable = true)
    private void onShowFloatingItem(ItemStack floatingItem, CallbackInfo info) {
        if (floatingItem.getItem() == Items.TOTEM_OF_UNDYING && Modules.get().get(NoRender.class).noTotemAnimation()) info.cancel();
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private void applyCameraTransformationsMathHelperLerpProxy(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        if (Modules.get().get(NoRender.class).noNausea()) {
            assert this.client.player != null;
            this.client.player.lastNauseaStrength = 0;
            this.client.player.nextNauseaStrength = 0;
        }
    }

    // Freecam

    private boolean freecamSet = false;

    @Inject(method = "updateTargetedEntity", at = @At("INVOKE"), cancellable = true)
    private void updateTargetedEntityInvoke(float tickDelta, CallbackInfo info) {
        Freecam freecam = Modules.get().get(Freecam.class);
        boolean highwayBuilder = Modules.get().isActive(HighwayBuilder.class);

        if (freecam.isActive() && client.getCameraEntity() != null && !freecamSet) {
            info.cancel();
            Entity camera = client.getCameraEntity();

            double x = camera.getX();
            double y = camera.getY();
            double z = camera.getZ();
            double prevX = camera.prevX;
            double prevY = camera.prevY;
            double prevZ = camera.prevZ;
            float yaw = camera.getYaw();
            float pitch = camera.getPitch();
            float prevYaw = camera.prevYaw;
            float prevPitch = camera.prevPitch;

            ((IVec3d) camera.getPos()).set(freecam.pos.x, freecam.pos.y - camera.getEyeHeight(camera.getPose()), freecam.pos.z);
            camera.prevX = freecam.prevPos.x;
            camera.prevY = freecam.prevPos.y - camera.getEyeHeight(camera.getPose());
            camera.prevZ = freecam.prevPos.z;
            camera.setYaw(freecam.yaw);
            camera.setPitch(freecam.pitch);
            camera.prevYaw = freecam.prevYaw;
            camera.prevPitch = freecam.prevPitch;

            freecamSet = true;
            updateTargetedEntity(tickDelta);
            freecamSet = false;

            ((IVec3d) camera.getPos()).set(x, y, z);
            camera.prevX = prevX;
            camera.prevY = prevY;
            camera.prevZ = prevZ;
            camera.setYaw(yaw);
            camera.setPitch(pitch);
            camera.prevYaw = prevYaw;
            camera.prevPitch = prevPitch;
        } else if (highwayBuilder && client.getCameraEntity() != null && !freecamSet) {
            Entity cameraE = client.getCameraEntity();

            double x = cameraE.getX();
            double y = cameraE.getY();
            double z = cameraE.getZ();
            double prevX = cameraE.prevX;
            double prevY = cameraE.prevY;
            double prevZ = cameraE.prevZ;
            float yaw = cameraE.getYaw();
            float pitch = cameraE.getPitch();
            float prevYaw = cameraE.prevYaw;
            float prevPitch = cameraE.prevPitch;

            if (highwayBuilder) {
                cameraE.setYaw(camera.getYaw());
                cameraE.setPitch(camera.getPitch());
            } else {
                ((IVec3d) cameraE.getPos()).set(x, y, z);
                cameraE.prevX = prevX;
                cameraE.prevY = prevY;
                cameraE.prevZ = prevZ;
                cameraE.setYaw(yaw);
                cameraE.setPitch(pitch);
                cameraE.prevYaw = prevYaw;
                cameraE.prevPitch = prevPitch;
            }
        }
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void renderHand(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo info) {
        if (!Modules.get().get(Freecam.class).renderHands() || !Modules.get().get(Zoom.class).renderHands()) info.cancel();
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void injectBobView(MatrixStack matrixStack, float f, CallbackInfo info) {
        if (Modules.get().isActive(NoBob.class)) info.cancel();
    }

    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 3))
    private double updateTargetedEntityModifySurvivalReach(double d) {
        Reach reach = Modules.get().get(Reach.class);
        return reach.isActive() ? reach.getReach() : d;
    }

    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 9))
    private double updateTargetedEntityModifySquaredMaxReach(double d) {
        Reach reach = Modules.get().get(Reach.class);
        return reach.isActive() ? reach.getReach() * reach.getReach() : d;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawEntityOutlinesFramebuffer()V", ordinal = 0))
    private void renderShader(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        PostEffectProcessor shader = Modules.get().get(Rendering.class).getShaderEffect();

        if (shader != null) {
            shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            shader.render(tickDelta);
        }
    }
}
