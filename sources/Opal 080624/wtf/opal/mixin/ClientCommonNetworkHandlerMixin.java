package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_2596;
import net.minecraft.class_8673;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.d1;
import wtf.opal.lb;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_8673.class})
public final class ClientCommonNetworkHandlerMixin {
  private static final long a = on.a(-6566366834291516497L, 8189435823941860830L, MethodHandles.lookup().lookupClass()).a(240211884757663L);
  
  @Inject(method = {"sendPacket"}, at = {@At("HEAD")}, cancellable = true)
  private void sendPacket(class_2596<?> paramclass_2596, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x3F7DC6294614L;
    long l2 = l1 ^ 0x7C1B94F95C9FL;
    long l3 = l1 ^ 0x11EE4B262388L;
    lb lb = new lb(paramclass_2596, l3);
    try {
      (new Object[2])[1] = lb;
      new Object[2];
      d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
      if (lb.X(new Object[0]))
        paramCallbackInfo.cancel(); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientCommonNetworkHandlerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */