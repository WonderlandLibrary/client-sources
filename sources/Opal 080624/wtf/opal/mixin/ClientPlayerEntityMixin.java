package wtf.opal.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandles;
import net.minecraft.class_1313;
import net.minecraft.class_243;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_744;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.a;
import wtf.opal.b9;
import wtf.opal.bp;
import wtf.opal.d1;
import wtf.opal.j_;
import wtf.opal.ly;
import wtf.opal.lz;
import wtf.opal.on;
import wtf.opal.p;
import wtf.opal.x5;

@Mixin({class_746.class})
public abstract class ClientPlayerEntityMixin extends class_742 {
  @Unique
  private lz preMotionEvent;
  
  @Shadow
  public class_744 field_3913;
  
  private static final long a = on.a(229287411234899076L, 7157214247154569743L, MethodHandles.lookup().lookupClass()).a(231326110864827L);
  
  @Shadow
  protected abstract void method_3148(float paramFloat1, float paramFloat2);
  
  @Shadow
  public abstract float method_5705(float paramFloat);
  
  @Shadow
  public abstract float method_5695(float paramFloat);
  
  public ClientPlayerEntityMixin(class_638 paramclass_638, GameProfile paramGameProfile) {
    super(paramclass_638, paramGameProfile);
  }
  
  @WrapOperation(at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;hasForwardMovement()Z", ordinal = 0)}, method = {"tickMovement()V"})
  private boolean wrapHasForwardMovement(class_744 paramclass_744, Operation<Boolean> paramOperation) {
    j_ j_ = (j_)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { j_.class });
    try {
      if (j_.D(new Object[0]))
        try {
          if (((Boolean)j_.k(new Object[0]).z()).booleanValue())
            return true; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return ((Boolean)paramOperation.call(new Object[] { paramclass_744 })).booleanValue();
  }
  
  @Inject(method = {"sendMovementPackets"}, at = {@At("HEAD")}, cancellable = true)
  private void hookSendMovementPacketsHead(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0xD543B9792B1L;
    int i = (int)((l1 ^ 0x75C323292203L) >>> 32L);
    int j = (int)((l1 ^ 0x75C323292203L) << 32L >>> 48L);
    int k = (int)((l1 ^ 0x75C323292203L) << 48L >>> 48L);
    l1 ^ 0x75C323292203L;
    long l2 = l1 ^ 0x465C00AE20BAL;
    try {
      this.preMotionEvent = new lz(method_19538(), i, method_36454(), j, method_36455(), method_24828(), k);
      (new Object[2])[1] = this.preMotionEvent;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
      if (this.preMotionEvent.X(new Object[0]))
        paramCallbackInfo.cancel(); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(method = {"sendMovementPackets"}, at = {@At("TAIL")})
  private void hookSendMovementPacketsTail(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x71C3D290C5F8L;
    long l2 = l1 ^ 0x2C66157BFE93L;
    long l3 = l1 ^ 0x37F9338ADC24L;
    long l4 = l1 ^ 0x294AD47B7A82L;
    long l5 = l1 ^ 0x3ACBE9A977F3L;
    new Object[1];
    new Object[1];
    new Object[1];
    (new Object[2])[1] = new ly(new class_243(this.preMotionEvent.D(new Object[] { Long.valueOf(l3) }, ), this.preMotionEvent.J(new Object[] { Long.valueOf(l2) }, ), this.preMotionEvent.f(new Object[] { Long.valueOf(l4) })), this.preMotionEvent.R(new Object[0]), this.preMotionEvent.G(new Object[0]), this.preMotionEvent.z(new Object[0]));
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l5) });
  }
  
  @Redirect(method = {"sendMovementPackets"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
  private float hookSendMovementPacketsYaw(class_746 paramclass_746) {
    return this.preMotionEvent.R(new Object[0]);
  }
  
  @Redirect(method = {"sendMovementPackets"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
  private float hookSendMovementPacketsPitch(class_746 paramclass_746) {
    return this.preMotionEvent.G(new Object[0]);
  }
  
  @Redirect(method = {"sendMovementPackets"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
  private boolean hookSendMovementPacketsGround(class_746 paramclass_746) {
    return this.preMotionEvent.z(new Object[0]);
  }
  
  @Redirect(method = {"sendMovementPackets"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
  private double hookSendMovementPacketsPosX(class_746 paramclass_746) {
    long l1 = a ^ 0x6694260E4E3AL;
    long l2 = l1 ^ 0x20AEC71457E6L;
    new Object[1];
    return this.preMotionEvent.D(new Object[] { Long.valueOf(l2) });
  }
  
  @Redirect(method = {"sendMovementPackets"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
  private double hookSendMovementPacketsPosY(class_746 paramclass_746) {
    long l1 = a ^ 0x1EDFB4DF202BL;
    long l2 = l1 ^ 0x437A73341B40L;
    new Object[1];
    return this.preMotionEvent.J(new Object[] { Long.valueOf(l2) });
  }
  
  @Redirect(method = {"sendMovementPackets"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
  private double hookSendMovementPacketsPosZ(class_746 paramclass_746) {
    long l1 = a ^ 0x107073C0A25FL;
    long l2 = l1 ^ 0x48F9752B1D25L;
    new Object[1];
    return this.preMotionEvent.f(new Object[] { Long.valueOf(l2) });
  }
  
  @Overwrite
  public void method_5784(class_1313 paramclass_1313, class_243 paramclass_243) {
    long l1 = a ^ 0x7254E36FBC02L;
    long l2 = l1 ^ 0x395CD8560E09L;
    p p = new p(paramclass_243);
    (new Object[2])[1] = p;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
    double d1 = b9.c.field_1724.method_23317();
    double d2 = b9.c.field_1724.method_23321();
    super.method_5784(paramclass_1313, p.S(new Object[0]));
    method_3148((float)(method_23317() - d1), (float)(method_23321() - d2));
  }
  
  @Inject(method = {"tickMovement"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0)})
  private void hookCustomMultiplier(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x34CA1B3B5D1DL;
    long l2 = l1 ^ 0x7FC22002EF16L;
    class_744 class_7441 = this.field_3913;
    class_7441.field_3905 /= 0.2F;
    class_7441.field_3907 /= 0.2F;
    bp bp = new bp(0.2F, 0.2F);
    (new Object[2])[1] = bp;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
    class_7441.field_3905 *= bp.v(new Object[0]);
    class_7441.field_3907 *= bp.u(new Object[0]);
  }
  
  @Redirect(method = {"canStartSprinting"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
  private boolean hookSprintAffectStart(class_746 paramclass_746) {
    try {
      if (((a)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { a.class })).D(new Object[0]))
        return false; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return paramclass_746.method_6115();
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientPlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */