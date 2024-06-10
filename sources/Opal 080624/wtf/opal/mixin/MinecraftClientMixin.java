package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_310;
import net.minecraft.class_434;
import net.minecraft.class_437;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.b6;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.d4;
import wtf.opal.gs;
import wtf.opal.on;
import wtf.opal.u0;
import wtf.opal.x5;

@Mixin({class_310.class})
public abstract class MinecraftClientMixin {
  private static final long a = on.a(-7575796823894653459L, 2636075259995932352L, MethodHandles.lookup().lookupClass()).a(10281598975747L);
  
  @Inject(method = {"<init>"}, at = {@At("TAIL")})
  private void init(CallbackInfo paramCallbackInfo) {
    long l = a ^ 0x697AE838165DL;
    int i = (int)((l ^ 0x4371A51E41F2L) >>> 48L);
    int j = (int)((l ^ 0x4371A51E41F2L) << 16L >>> 32L);
    int k = (int)((l ^ 0x4371A51E41F2L) << 48L >>> 48L);
    l ^ 0x4371A51E41F2L;
    new d1((short)i, j, (char)k);
  }
  
  @Inject(method = {"tick"}, at = {@At("HEAD")})
  private void tickHead(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x21CE341C700BL;
    long l2 = l1 ^ 0x57F04F98A48AL;
    long l3 = l1 ^ 0x386D5207E8CBL;
    b6 b6 = new b6();
    try {
      b6.P(new Object[] { Boolean.valueOf(true) });
      (new Object[2])[1] = b6;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l3) });
      if (d1.q(new Object[0]).C(new Object[0]).c(new Object[0]) == null)
        try {
          if (!(b9.c.field_1755 instanceof gs))
            b9.c.method_1507((class_437)new gs(l2)); 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(method = {"tick"}, at = {@At("TAIL")})
  private void tickTail(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x4123E1B3C893L;
    long l2 = l1 ^ 0x588087A85053L;
    b6 b6 = new b6();
    b6.P(new Object[] { Boolean.valueOf(false) });
    (new Object[2])[1] = b6;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
  }
  
  @Inject(method = {"tick"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;overlay:Lnet/minecraft/client/gui/screen/Overlay;", opcode = 180, shift = At.Shift.AFTER)})
  private void handleInputEventsHead(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x3BF4EFFAE4D5L;
    long l2 = l1 ^ 0x225789E17C15L;
    u0 u0 = new u0();
    u0.C(new Object[] { Boolean.valueOf(true) });
    (new Object[2])[1] = u0;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
  }
  
  @Inject(method = {"tick"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;world:Lnet/minecraft/client/world/ClientWorld;", opcode = 180, ordinal = 7)})
  private void handleInputEventsTail(CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0xD217FC699D4L;
    long l2 = l1 ^ 0x148219DD0114L;
    if (b9.c.method_18506() == null) {
      u0 u0 = new u0();
      u0.C(new Object[] { Boolean.valueOf(false) });
      (new Object[2])[1] = u0;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
    } 
  }
  
  @Inject(method = {"getFramerateLimit"}, at = {@At("HEAD")}, cancellable = true)
  private void getFramerateLimit(CallbackInfoReturnable<Integer> paramCallbackInfoReturnable) {
    paramCallbackInfoReturnable.setReturnValue(Integer.valueOf(b9.c.method_22683().method_16000()));
  }
  
  @Inject(method = {"close"}, at = {@At("HEAD")})
  private void close(CallbackInfo paramCallbackInfo) {
    d1.q(new Object[0]).c(new Object[0]);
  }
  
  @Inject(method = {"joinWorld"}, at = {@At("HEAD")})
  private void joinWorld(class_638 paramclass_638, class_434.class_9678 paramclass_9678, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x275D2DF3B71EL;
    long l2 = l1 ^ 0x3EFE4BE82FDEL;
    (new Object[2])[1] = new d4();
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\MinecraftClientMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */