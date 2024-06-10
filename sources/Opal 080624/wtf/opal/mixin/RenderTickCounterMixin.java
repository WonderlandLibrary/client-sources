package wtf.opal.mixin;

import net.minecraft.class_317;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.d1;

@Mixin({class_317.class})
public final class RenderTickCounterMixin {
  @Shadow
  public float field_1969;
  
  @Inject(method = {"beginRenderTick"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J")})
  private void beginRenderTick(long paramLong, CallbackInfoReturnable<Integer> paramCallbackInfoReturnable) {
    this.field_1969 *= d1.q(new Object[0]).l(new Object[0]).x(new Object[0]);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\RenderTickCounterMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */