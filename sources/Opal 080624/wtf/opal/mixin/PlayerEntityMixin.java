package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3414;
import net.minecraft.class_3545;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.jq;
import wtf.opal.l8;
import wtf.opal.on;
import wtf.opal.x5;
import wtf.opal.xc;

@Mixin({class_1657.class})
public abstract class PlayerEntityMixin {
  @Shadow
  @Final
  private class_1661 field_7514;
  
  private static final long a = on.a(-5203416500617644201L, 168513695312458623L, MethodHandles.lookup().lookupClass()).a(201713454616168L);
  
  @Shadow
  public abstract class_1661 method_31548();
  
  @Shadow
  public abstract void method_5783(class_3414 paramclass_3414, float paramFloat1, float paramFloat2);
  
  @Redirect(method = {"tickNewAi"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getYaw()F"))
  private float hookHeadRotations(class_1657 paramclass_1657) {
    try {
      if (this != b9.c.field_1724)
        return paramclass_1657.method_36454(); 
    } catch (x5 x5) {
      throw a(null);
    } 
    class_241 class_241 = d1.q(new Object[0]).i(new Object[0]).k(new Object[0]);
    class_3545 class_3545 = d1.q(new Object[0]).i(new Object[0]).E(new Object[0]);
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    d1.q(new Object[0]).i(new Object[0]).E(new Object[] { new class_3545(class_3545.method_15441(), Float.valueOf(!d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? paramclass_1657.method_36455() : class_241.field_1342)) });
    return !d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? paramclass_1657.method_36454() : class_241.field_1343;
  }
  
  @Redirect(method = {"attack"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getYaw()F"))
  private float hookFixRotation(class_1657 paramclass_1657) {
    class_241 class_241 = d1.q(new Object[0]).i(new Object[0]).k(new Object[0]);
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return !d1.q(new Object[0]).i(new Object[0]).U(new Object[0]) ? paramclass_1657.method_36454() : class_241.field_1343;
  }
  
  @ModifyArg(method = {"attack"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"))
  private boolean hookKeepSprint(boolean paramBoolean) {
    return ((jq)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jq.class })).D(new Object[0]);
  }
  
  @ModifyArg(method = {"attack"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
  private class_243 hookKeepSprintVelocity(class_243 paramclass_243) {
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return ((jq)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jq.class })).D(new Object[0]) ? b9.c.field_1724.method_18798() : paramclass_243;
  }
  
  @Redirect(method = {"getBlockBreakingSpeed"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F"))
  private float redirectBlockBreakingSpeed(class_1661 paramclass_1661, class_2680 paramclass_2680) {
    xc xc = (xc)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { xc.class });
    try {
      if (xc.D(new Object[0]))
        try {
          if (xc.Z(new Object[0]))
            try {
              if (xc.z(new Object[0]) != -1);
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return paramclass_1661.method_7370(paramclass_2680);
  }
  
  @Redirect(method = {"getBlockBreakingSpeed"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
  private class_1799 redirectMainHandStack(class_1657 paramclass_1657) {
    xc xc = (xc)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { xc.class });
    try {
      if (xc.D(new Object[0]))
        try {
          if (xc.Z(new Object[0]))
            try {
              if (xc.z(new Object[0]) != -1);
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return (class_1799)(b9.c.field_1724.method_31548()).field_7547.get((b9.c.field_1724.method_31548()).field_7545);
  }
  
  @Redirect(method = {"getBlockBreakingSpeed"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isOnGround()Z"))
  private boolean redirectGroundState(class_1657 paramclass_1657) {
    // Byte code:
    //   0: iconst_0
    //   1: anewarray java/lang/Object
    //   4: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   7: iconst_0
    //   8: anewarray java/lang/Object
    //   11: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   14: ldc wtf/opal/xc
    //   16: iconst_1
    //   17: anewarray java/lang/Object
    //   20: dup_x1
    //   21: swap
    //   22: iconst_0
    //   23: swap
    //   24: aastore
    //   25: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   28: checkcast wtf/opal/xc
    //   31: astore_2
    //   32: aload_2
    //   33: iconst_0
    //   34: anewarray java/lang/Object
    //   37: invokevirtual D : ([Ljava/lang/Object;)Z
    //   40: ifeq -> 88
    //   43: aload_2
    //   44: iconst_0
    //   45: anewarray java/lang/Object
    //   48: invokevirtual Z : ([Ljava/lang/Object;)Z
    //   51: ifeq -> 88
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: aload_2
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokevirtual T : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   69: invokevirtual z : ()Ljava/lang/Object;
    //   72: checkcast java/lang/Boolean
    //   75: invokevirtual booleanValue : ()Z
    //   78: ifne -> 107
    //   81: goto -> 88
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   91: getfield field_1724 : Lnet/minecraft/class_746;
    //   94: invokevirtual method_24828 : ()Z
    //   97: ifeq -> 115
    //   100: goto -> 107
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: iconst_1
    //   108: goto -> 116
    //   111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: iconst_0
    //   116: ireturn
    // Exception table:
    //   from	to	target	type
    //   32	54	57	wtf/opal/x5
    //   43	81	84	wtf/opal/x5
    //   61	100	103	wtf/opal/x5
    //   88	111	111	wtf/opal/x5
  }
  
  @Redirect(method = {"getEquippedStack"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
  private class_1799 redirectMainHandStack2(class_1661 paramclass_1661) {
    xc xc = (xc)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { xc.class });
    try {
      if (xc.D(new Object[0]))
        try {
          if (xc.Z(new Object[0]))
            try {
              if (xc.z(new Object[0]) != -1);
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return this.field_7514.method_7391();
  }
  
  @Overwrite
  public boolean method_7305(class_2680 paramclass_2680) {
    int i = this.field_7514.field_7545;
    xc xc = (xc)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { xc.class });
    try {
      if (xc.D(new Object[0]))
        try {
          if (xc.Z(new Object[0]) && xc.z(new Object[0]) != -1)
            i = xc.z(new Object[0]); 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (paramclass_2680.method_29291()) {
        try {
          if (this.field_7514.method_5438(i).method_7951(paramclass_2680));
        } catch (x5 x5) {
          throw a(null);
        } 
        return false;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(method = {"clipAtLedge"}, at = {@At("HEAD")}, cancellable = true)
  private void hookClipAtLedge(CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    long l1 = a ^ 0x5453A4B04A20L;
    long l2 = l1 ^ 0x298526D52225L;
    if (this == b9.c.field_1724) {
      l8 l8 = new l8();
      try {
        (new Object[2])[1] = l8;
        new Object[2];
        d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
        if (l8.v(new Object[0]))
          paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(l8.Q(new Object[0]))); 
      } catch (x5 x5) {
        throw a(null);
      } 
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */