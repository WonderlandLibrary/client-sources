package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_743;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.on;
import wtf.opal.p8;
import wtf.opal.x5;

@Mixin({class_743.class})
public final class KeyboardInputMixin {
  @Unique
  private p8 moveInputEvent;
  
  private static final long a = on.a(5161602543715800525L, 5982794785798342016L, MethodHandles.lookup().lookupClass()).a(68022215611131L);
  
  @Inject(method = {"tick"}, at = {@At("HEAD")})
  private void hookMoveInputEvent(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x9387899ACF9L;
    long l2 = l1 ^ 0x2B03A1198414L;
    this.moveInputEvent = new p8(getMovementMultiplier(b9.c.field_1690.field_1894.method_1434(), b9.c.field_1690.field_1881.method_1434()), getMovementMultiplier(b9.c.field_1690.field_1913.method_1434(), b9.c.field_1690.field_1849.method_1434()));
    (new Object[2])[1] = this.moveInputEvent;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
  }
  
  @Redirect(method = {"tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 0))
  private float hookMoveInputEventForward(boolean paramBoolean1, boolean paramBoolean2) {
    return this.moveInputEvent.Y(new Object[0]);
  }
  
  @Redirect(method = {"tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 1))
  private float hookMoveInputEventStrafe(boolean paramBoolean1, boolean paramBoolean2) {
    return this.moveInputEvent.s(new Object[0]);
  }
  
  @Unique
  private static float getMovementMultiplier(boolean paramBoolean1, boolean paramBoolean2) {
    try {
      if (paramBoolean1 == paramBoolean2)
        return 0.0F; 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return paramBoolean1 ? 1.0F : -1.0F;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\KeyboardInputMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */