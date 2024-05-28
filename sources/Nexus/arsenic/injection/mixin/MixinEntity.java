package arsenic.injection.mixin;

import arsenic.event.impl.EventLook;
import arsenic.event.impl.EventMove;
import arsenic.main.Nexus;
import arsenic.module.impl.movement.Scaffold;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public double motionX;
    @Shadow
    public boolean isAirBorne;

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    public abstract void moveFlying(float strafe, float forward, float friction);

    @Shadow
    protected abstract Vec3 getVectorForRotation(float pitch, float yaw);

    @Shadow
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    public boolean secondCall;

    public Minecraft minecraft = Minecraft.getMinecraft();

    @Inject(method = "moveFlying", at = @At("HEAD"), cancellable = true)
    private void moveFlyingHead(float p_moveFlying_1_, float p_moveFlying_2_, float p_moveFlying_3_, CallbackInfo ci) {
        if ((Object) this == Minecraft.getMinecraft().thePlayer) {
            if (secondCall) {
                secondCall = false;
                return;
            }
            EventMove e = new EventMove(p_moveFlying_1_, p_moveFlying_2_, p_moveFlying_3_, rotationYaw);
            Nexus.getNexus().getEventManager().post(e);
            float cachedYawM = rotationYaw;
            rotationYaw = e.getYaw();
            secondCall = true;
            moveFlying(e.getStrafe(), e.getForward(), e.getFriction());
            rotationYaw = cachedYawM;
            ci.cancel();
        }
    }
    @ModifyVariable(method = "moveEntity", at = @At(value = "STORE"), ordinal = 0)
    public boolean mixinMoveEntity(boolean flag) {
        if ((Object) this != Minecraft.getMinecraft().thePlayer) {
            return flag;
        }
        Scaffold scaffold = Nexus.getNexus().getModuleManager().getModuleByClass(Scaffold.class);
        if (!scaffold.isEnabled()) {
            return flag;
        }
        return scaffold.mixinResult(flag);
    }

    @ModifyVariable(method = "rayTrace", at = @At("STORE"), ordinal = 1)
    public Vec3 rayTrace(Vec3 vec31) {
        if ((Object) this != Minecraft.getMinecraft().getRenderViewEntity()) {
            return vec31;
        }
        EventLook eventLook = new EventLook(rotationYaw, rotationPitch);
        Nexus.getNexus().getEventManager().post(eventLook);
        if (!eventLook.hasBeenModified()) {
            return vec31;
        }
        return getVectorForRotation(eventLook.getPitch(), eventLook.getYaw());
    }
}
