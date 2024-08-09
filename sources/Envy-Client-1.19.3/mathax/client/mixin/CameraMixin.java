package mathax.client.mixin;

import mathax.client.mixininterface.ICamera;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.Utils;
import mathax.client.systems.modules.render.CameraTweaks;
import mathax.client.systems.modules.render.FreeLook;
import mathax.client.systems.modules.render.Freecam;
import mathax.client.systems.modules.render.InstantSneak;
import mathax.client.systems.modules.world.HighwayBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public abstract class CameraMixin implements ICamera {
    @Shadow
    private boolean thirdPerson;

    @Shadow
    protected abstract double clipToSpace(double desiredCameraDistance);

    @Shadow
    private float yaw;

    @Shadow
    private float pitch;

    @Shadow
    private float cameraY;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Unique
    private float tickDelta;

    @Shadow
    private Entity focusedEntity;

    @Inject(at = @At("HEAD"), method = "updateEyeHeight")
    public void updateEyeHeight(CallbackInfo info) {
        if (Modules.get().isActive(InstantSneak.class) && focusedEntity != null) cameraY = focusedEntity.getStandingEyeHeight();
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;moveBy(DDD)V", ordinal = 0))
    private void modifyCameraDistance(Args args) {
        args.set(0, -clipToSpace(Modules.get().get(CameraTweaks.class).getDistance()));
        if (Modules.get().isActive(Freecam.class)) args.set(0, -clipToSpace(0));
    }

    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    private void onClipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> info) {
        if (Modules.get().get(CameraTweaks.class).clip()) info.setReturnValue(desiredCameraDistance);
    }

    @Inject(method = "update", at = @At("HEAD"))
    private void onUpdateHead(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        this.tickDelta = tickDelta;
    }

    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdateTail(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        if (Modules.get().isActive(Freecam.class)) this.thirdPerson = true;
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    private void onUpdateSetPosArgs(Args args) {
        Freecam freecam = Modules.get().get(Freecam.class);

        if (freecam.isActive()) {
            args.set(0, freecam.getX(tickDelta));
            args.set(1, freecam.getY(tickDelta));
            args.set(2, freecam.getZ(tickDelta));
        }
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
    private void onUpdateSetRotationArgs(Args args) {
        Freecam freecam = Modules.get().get(Freecam.class);
        FreeLook freeLook = Modules.get().get(FreeLook.class);

        if (freecam.isActive()) {
            args.set(0, (float) freecam.getYaw(tickDelta));
            args.set(1, (float) freecam.getPitch(tickDelta));
        } else if (Modules.get().isActive(HighwayBuilder.class)) {
            args.set(0, yaw);
            args.set(1, pitch);
        } else if (freeLook.isActive()) {
            args.set(0, freeLook.cameraYaw);
            args.set(1, freeLook.cameraPitch);
        }
    }

    @Override
    public void setRot(double yaw, double pitch) {
        setRotation((float) yaw, (float) Utils.clamp(pitch, -90, 90));
    }
}
