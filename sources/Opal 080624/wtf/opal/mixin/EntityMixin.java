package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.jy;
import wtf.opal.l;
import wtf.opal.l3;
import wtf.opal.la;
import wtf.opal.on;
import wtf.opal.ux;
import wtf.opal.x5;

@Mixin({class_1297.class})
public abstract class EntityMixin {
  @Shadow
  public boolean field_6007;
  
  private static final long a = on.a(-1859304963600672644L, 449187648238369256L, MethodHandles.lookup().lookupClass()).a(137181470986936L);
  
  @Shadow
  public abstract class_243 method_18798();
  
  @Shadow
  public abstract void method_18799(class_243 paramclass_243);
  
  @Shadow
  public abstract boolean method_5624();
  
  @Shadow
  public abstract float method_36454();
  
  @Shadow
  public static class_243 method_18795(class_243 paramclass_243, float paramFloat1, float paramFloat2) {
    return null;
  }
  
  @Shadow
  public abstract class_238 method_5829();
  
  @Shadow
  public abstract class_1937 method_37908();
  
  @Redirect(method = {"updateVelocity"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"))
  public class_243 hookVelocity(class_243 paramclass_243, float paramFloat1, float paramFloat2) {
    long l1 = a ^ 0x585E530F7674L;
    long l2 = l1 ^ 0x170EC27F7169L;
    long l3 = l1 ^ 0xAA73892E499L;
    if (this == b9.c.field_1724) {
      l3 l31 = new l3(paramclass_243, l2, paramFloat1, paramFloat2, method_18795(paramclass_243, paramFloat1, paramFloat2));
      (new Object[2])[1] = l31;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l3) });
      return l31.M(new Object[0]);
    } 
    return method_18795(paramclass_243, paramFloat1, paramFloat2);
  }
  
  @Redirect(method = {"adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getStepHeight()F"))
  private float hookStepHeight(class_1297 paramclass_1297) {
    long l1 = a ^ 0x13467B5E97E7L;
    long l2 = l1 ^ 0x41BF10C3050AL;
    if (this == b9.c.field_1724) {
      ux ux = new ux(paramclass_1297.method_49476());
      (new Object[2])[1] = ux;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
      return ux.o(new Object[0]);
    } 
    return paramclass_1297.method_49476();
  }
  
  @Inject(method = {"adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"}, at = {@At(value = "RETURN", ordinal = 0)}, cancellable = true)
  private void hookStepHeight(class_243 paramclass_243, CallbackInfoReturnable<class_243> paramCallbackInfoReturnable) {
    long l1 = a ^ 0x14E0FE3A2998L;
    long l2 = l1 ^ 0x461995A7BB75L;
    if (this == b9.c.field_1724) {
      la la = new la(paramclass_243, (class_243)paramCallbackInfoReturnable.getReturnValue());
      (new Object[2])[1] = la;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
      paramCallbackInfoReturnable.setReturnValue(la.D(new Object[0]));
    } 
  }
  
  @Inject(method = {"isGlowing"}, at = {@At("HEAD")}, cancellable = true)
  private void isGlowing(CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    long l1 = a ^ 0xA874BB8131AL;
    long l2 = l1 ^ 0xDA363190D1EL;
    long l3 = l1 ^ 0x1E1035D30761L;
    try {
      if (this instanceof net.minecraft.class_1657)
        try {
          new Object[2];
          if (!l.K(new Object[] { null, Long.valueOf(l3), this }))
            try {
              new Object[1];
              if (((jy)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jy.class })).N(new Object[] { Long.valueOf(l2) }))
                paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(true)); 
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\EntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */