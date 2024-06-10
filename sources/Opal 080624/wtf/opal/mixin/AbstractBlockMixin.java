package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3726;
import net.minecraft.class_4970;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.bw;
import wtf.opal.d1;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_4970.class})
public final class AbstractBlockMixin {
  private static final long a = on.a(-2224834440757166641L, 2585247004980593955L, MethodHandles.lookup().lookupClass()).a(68054705725734L);
  
  @Inject(method = {"getCollisionShape"}, at = {@At("RETURN")}, cancellable = true)
  private void getCollisionShape(class_2680 paramclass_2680, class_1922 paramclass_1922, class_2338 paramclass_2338, class_3726 paramclass_3726, CallbackInfoReturnable<class_265> paramCallbackInfoReturnable) {
    long l1 = a ^ 0x47EBDD5E12A0L;
    long l2 = l1 ^ 0x579F13D7A825L;
    try {
      if (paramclass_2338 != null)
        try {
          if (d1.q(new Object[0]) != null) {
            bw bw = new bw(paramclass_2680, paramclass_2338, (class_265)paramCallbackInfoReturnable.getReturnValue());
            (new Object[2])[1] = bw;
            new Object[2];
            d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
            paramCallbackInfoReturnable.setReturnValue(bw.Y(new Object[0]));
            return;
          } 
          return;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\AbstractBlockMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */