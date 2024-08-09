package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Confetti;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mathax.mathaxclient.utils.Utils;

@Mixin(TotemParticle.class)
public abstract class TotemParticleMixin extends AnimatedParticle {
    protected TotemParticleMixin(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onParticleConstructor(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider, CallbackInfo info) {
        Confetti confetti = Modules.get().get(Confetti.class);
        if (!confetti.isEnabled()) {
            return;
        }

        Vec3d firstColor = confetti.getFirstColor();
        Vec3d secondColor = confetti.getSecondColor();
        if (Utils.random(0, 4) == 0) {
            this.setColor((float) firstColor.x, (float) firstColor.y, (float) firstColor.z);
        } else {
            this.setColor((float) secondColor.x, (float) secondColor.y, (float) secondColor.z);
        }
    }
}
