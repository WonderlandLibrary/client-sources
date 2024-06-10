package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1282;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.je;
import wtf.opal.ln;
import wtf.opal.m;
import wtf.opal.on;
import wtf.opal.p1;
import wtf.opal.pt;
import wtf.opal.x5;

@Mixin({class_1309.class})
public abstract class LivingEntityMixin extends EntityMixin {
  @Shadow
  protected class_1799 field_6277;
  
  @Shadow
  protected int field_6222;
  
  @Shadow
  private int field_6228;
  
  private static final long b = on.a(2073995700727126578L, -4118413656251767903L, MethodHandles.lookup().lookupClass()).a(100938843557180L);
  
  @Shadow
  public abstract boolean method_6115();
  
  @Shadow
  public abstract class_1309 method_6124();
  
  @Shadow
  protected abstract float method_6106();
  
  @Shadow
  public abstract float method_5705(float paramFloat);
  
  @Redirect(method = {"tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"), slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F", ordinal = 1)))
  private float tick(class_1309 paramclass_1309) {
    try {
      if (this != b9.c.field_1724)
        return paramclass_1309.method_36454(); 
    } catch (x5 x5) {
      throw b(null);
    } 
    class_241 class_241 = d1.q(new Object[0]).i(new Object[0]).k(new Object[0]);
    try {
    
    } catch (x5 x5) {
      throw b(null);
    } 
    return !d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? paramclass_1309.method_36454() : class_241.field_1343;
  }
  
  @Redirect(method = {"turnHead"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
  private float turnHead(class_1309 paramclass_1309) {
    try {
      if (this != b9.c.field_1724)
        return paramclass_1309.method_36454(); 
    } catch (x5 x5) {
      throw b(null);
    } 
    class_241 class_241 = d1.q(new Object[0]).i(new Object[0]).k(new Object[0]);
    try {
    
    } catch (x5 x5) {
      throw b(null);
    } 
    return !d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? paramclass_1309.method_36454() : class_241.field_1343;
  }
  
  @Redirect(method = {"travel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F"))
  private float travel(class_1309 paramclass_1309) {
    class_241 class_241 = d1.q(new Object[0]).i(new Object[0]).k(new Object[0]);
    try {
      if (paramclass_1309 != b9.c.field_1724)
        return paramclass_1309.method_36455(); 
    } catch (x5 x5) {
      throw b(null);
    } 
    try {
    
    } catch (x5 x5) {
      throw b(null);
    } 
    return !d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? paramclass_1309.method_36455() : class_241.field_1342;
  }
  
  @Redirect(method = {"travel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"))
  private class_243 travelRotationVector(class_1309 paramclass_1309) {
    class_241 class_241 = d1.q(new Object[0]).i(new Object[0]).k(new Object[0]);
    try {
      if (paramclass_1309 == b9.c.field_1724)
        try {
          return d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? ln.P(new Object[] { null, Float.valueOf(class_241.field_1343), Float.valueOf(class_241.field_1342) }) : paramclass_1309.method_5720();
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    return paramclass_1309.method_5720();
  }
  
  @Inject(method = {"jump"}, at = {@At("HEAD")}, cancellable = true)
  private void hookJump(CallbackInfo paramCallbackInfo) {
    long l1 = b ^ 0x72A3D22E08D5L;
    long l2 = l1 ^ 0x7F40E891CD1BL;
    if (this == b9.c.field_1724) {
      float f = method_36454();
      p1 p1 = new p1(f);
      (new Object[2])[1] = p1;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
      if (p1.h(new Object[0]) != f) {
        class_243 class_243 = method_18798();
        method_18799(new class_243(class_243.field_1352, method_6106(), class_243.field_1350));
        if (method_5624()) {
          float f1 = p1.h(new Object[0]) * 0.017453292F;
          float f2 = 0.2F;
          method_18799(method_18798().method_1031((-class_3532.method_15374(f1) * f2), 0.0D, (class_3532.method_15362(f1) * f2)));
        } 
        this.field_6007 = true;
        paramCallbackInfo.cancel();
      } 
    } 
  }
  
  @Inject(method = {"tickMovement"}, at = {@At("HEAD")})
  private void hookTickMovement(CallbackInfo paramCallbackInfo) {
    try {
      if (this == b9.c.field_1724)
        try {
          if (((m)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { m.class })).D(new Object[0]))
            this.field_6228 = 0; 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  @Inject(method = {"onDeath"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V")})
  private void onEntityKilled(class_1282 paramclass_1282, CallbackInfo paramCallbackInfo) {
    long l1 = b ^ 0x4DCFE7F22AL;
    long l2 = l1 ^ 0xDAEF55837E4L;
    pt pt = new pt((class_1309)this, paramclass_1282);
    (new Object[2])[1] = pt;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
  }
  
  @ModifyConstant(method = {"getHandSwingDuration"}, constant = {@Constant(intValue = 6)})
  private int modifyHandSwingDuration(int paramInt) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
    
    } catch (x5 x5) {
      throw b(null);
    } 
    return je.D(new Object[0]) ? (int)(paramInt * ((Double)je.j(new Object[0]).z()).doubleValue()) : paramInt;
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\LivingEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */