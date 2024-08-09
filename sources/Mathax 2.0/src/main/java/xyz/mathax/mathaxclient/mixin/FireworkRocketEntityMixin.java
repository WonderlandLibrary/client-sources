package xyz.mathax.mathaxclient.mixin;

import net.minecraft.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    /*@Shadow protected abstract void explodeAndRemove();

    @Shadow private int life;

    @Shadow private int lifeTime;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo info) {
        if (Modules.get().get(ElytraBoost.class).isFirework((FireworkRocketEntity) (Object) this) && this.life > this.lifeTime) {
            explodeAndRemove();
        }
    }

    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo info) {
        if (Modules.get().get(ElytraBoost.class).isFirework((FireworkRocketEntity) (Object) this)) {
            explodeAndRemove();
            info.cancel();
        }
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"), cancellable = true)
    private void onBlockHit(BlockHitResult blockHitResult, CallbackInfo info) {
        if (Modules.get().get(ElytraBoost.class).isFirework((FireworkRocketEntity) (Object) this)) {
            explodeAndRemove();
            info.cancel();
        }
    }*/
}
