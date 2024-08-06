package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.FirstPersonCameraEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ThirdPersonCameraEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.CameraNoClip;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    private final Vector3f horizontalPlane = new Vector3f(0.0F, 0.0F, 1.0F);

    @Shadow
    private final Vector3f verticalPlane = new Vector3f(0.0F, 1.0F, 0.0F);

    @Shadow
    private final Vector3f diagonalPlane = new Vector3f(1.0F, 0.0F, 0.0F);

    @Shadow
    private float cameraY;

    @Shadow
    private float lastCameraY;

    @ModifyVariable(
        at = @At("HEAD"),
        method = "clipToSpace(D)D",
        argsOnly = true
    )
    private double changeClipToSpaceDistance(double desiredCameraDistance) {
        if (
            ModuleManager.isEnabled(CameraNoClip.class)
        ) return CameraNoClip.distance;
        return desiredCameraDistance;
    }

    /**
     * @author scoliosis
     * @reason no clear water
     */
    @Overwrite
    public CameraSubmersionType getSubmersionType() {
        return CameraSubmersionType.NONE;
    }

    @Inject(at = @At("HEAD"), method = "clipToSpace(D)D", cancellable = true)
    private void onClipToSpace(
        double desiredCameraDistance,
        CallbackInfoReturnable<Double> cir
    ) {
        if (
            ModuleManager.isEnabled(CameraNoClip.class) && CameraNoClip.noClip
        ) cir.setReturnValue(desiredCameraDistance);
    }

    @Inject(at = @At("TAIL"), method = "update")
    public void onUpdate(
        BlockView area,
        Entity focusedEntity,
        boolean thirdPerson,
        boolean inverseView,
        float tickDelta,
        CallbackInfo ci
    ) {
        Bus.post(new FirstPersonCameraEvent(new Vec2f(lastCameraY, cameraY)));
    }

    @Inject(at = @At("HEAD"), method = "moveBy")
    protected void onMoveCamera(double x, double y, double z, CallbackInfo ci) {
        double d =
            (double) this.horizontalPlane.x() * x +
            (double) this.verticalPlane.x() * y +
            (double) this.diagonalPlane.x() * z;
        double e =
            (double) this.horizontalPlane.y() * x +
            (double) this.verticalPlane.y() * y +
            (double) this.diagonalPlane.y() * z;
        double f =
            (double) this.horizontalPlane.z() * x +
            (double) this.verticalPlane.z() * y +
            (double) this.diagonalPlane.z() * z;

        Bus.post(new ThirdPersonCameraEvent(new Vec3d(d, e, f)));
    }
}
