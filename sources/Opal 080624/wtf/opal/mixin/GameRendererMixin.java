package wtf.opal.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import java.lang.invoke.MethodHandles;
import net.minecraft.class_4587;
import net.minecraft.class_4599;
import net.minecraft.class_5912;
import net.minecraft.class_746;
import net.minecraft.class_757;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.i;
import wtf.opal.k7;
import wtf.opal.lt;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_757.class})
public final class GameRendererMixin {
  @Final
  @Shadow
  private class_4599 field_20948;
  
  private static final long a = on.a(4665532568001441496L, 7285523177001902233L, MethodHandles.lookup().lookupClass()).a(106155528222333L);
  
  @Inject(method = {"renderWorld"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = 180, ordinal = 0)})
  private void hookRenderWorld(float paramFloat, long paramLong, CallbackInfo paramCallbackInfo, @Local(ordinal = 1) Matrix4f paramMatrix4f) {
    long l1 = a ^ 0x13C772B68919L;
    long l2 = l1 ^ 0x435A6492F39FL;
    long l3 = l1 ^ 0x436C139751FCL;
    class_4587 class_4587 = new class_4587();
    class_4587.method_34425(paramMatrix4f);
    (new Object[2])[1] = new k7(this.field_20948.method_23000(), class_4587, l3, paramFloat);
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
  }
  
  @Redirect(method = {"updateCrosshairTarget"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEntityInteractionRange()D"))
  public double hookCrosshairEntityInteractionRange(class_746 paramclass_746) {
    long l1 = a ^ 0x50F8BD0A0C86L;
    long l2 = l1 ^ 0x2CE45FD5EB23L;
    i i = (i)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { i.class });
    double d = b9.c.field_1724.method_55755();
    try {
      new Object[1];
    } catch (x5 x5) {
      throw a(null);
    } 
    return i.D(new Object[0]) ? Math.max(d, i.s(new Object[] { Long.valueOf(l2) })) : d;
  }
  
  @Redirect(method = {"updateCrosshairTarget"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getBlockInteractionRange()D"))
  public double hookCrosshairBlockInteractionRange(class_746 paramclass_746) {
    long l1 = a ^ 0x6CFAC4AF2036L;
    long l2 = l1 ^ 0x19C9CF3DB7E4L;
    i i = (i)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { i.class });
    double d = b9.c.field_1724.method_55754();
    try {
      new Object[1];
    } catch (x5 x5) {
      throw a(null);
    } 
    return i.D(new Object[0]) ? Math.max(d, i.F(new Object[] { Long.valueOf(l2) })) : d;
  }
  
  @Inject(method = {"loadBlurPostProcessor"}, at = {@At("TAIL")})
  private void hookLoadBlurPostProcessor(class_5912 paramclass_5912, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x21B3EE02AE14L;
    long l2 = l1 ^ 0xBEE5791CC24L;
    (new Object[2])[1] = paramclass_5912;
    new Object[2];
    lt.L(new Object[] { Long.valueOf(l2) });
  }
  
  @Inject(method = {"onResized"}, at = {@At("HEAD")})
  private void hookResize(int paramInt1, int paramInt2, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x7D752789D26FL;
    long l2 = l1 ^ 0x3D172A465D67L;
    (new Object[3])[2] = Integer.valueOf(paramInt2);
    new Object[3];
    lt.k(new Object[] { null, Long.valueOf(l2), Integer.valueOf(paramInt1) });
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\GameRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */