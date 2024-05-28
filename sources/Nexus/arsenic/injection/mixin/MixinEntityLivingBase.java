package arsenic.injection.mixin;

import arsenic.event.impl.EventJump;
import arsenic.main.Nexus;
import arsenic.module.impl.movement.NoJumpDelay;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    @Shadow
    protected abstract float getJumpUpwardsMotion();

    @Shadow
    private int jumpTicks;

    private EventJump jumpEvent;

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void jump(CallbackInfo ci) {
        jumpEvent = new EventJump(this.rotationYaw, this.getJumpUpwardsMotion());
        if (jumpEvent.isCancelled())
            ci.cancel();
    }

    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getJumpUpwardsMotion()F"))
    private float upwardsMotion(EntityLivingBase instance) {
        return jumpEvent.getMotion();
    }

    //ahh why does this give an error kys mc dev plugin
    @ModifyVariable(method = "jump", at = @At(value = "STORE"), ordinal = 0)
    public float yaw(float f) {
        return jumpEvent.getYaw();
    }



    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void headLiving(CallbackInfo callbackInfo) {
        if (Nexus.getInstance().getModuleManager().getModuleByClass(NoJumpDelay.class).isEnabled())
            jumpTicks = 0;
    }
}
