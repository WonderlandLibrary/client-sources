package wtf.opal.mixin;

import net.minecraft.class_1007;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_5617;
import net.minecraft.class_572;
import net.minecraft.class_583;
import net.minecraft.class_591;
import net.minecraft.class_742;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.d1;
import wtf.opal.je;
import wtf.opal.x5;

@Mixin({class_1007.class})
public abstract class PlayerEntityRendererMixin extends class_922<class_742, class_591<class_742>> {
  public PlayerEntityRendererMixin(class_5617.class_5618 paramclass_5618, class_591<class_742> paramclass_591, float paramFloat) {
    super(paramclass_5618, (class_583)paramclass_591, paramFloat);
  }
  
  @Inject(method = {"setModelPose"}, at = {@At("TAIL")})
  private void setModelPose(class_742 paramclass_742, CallbackInfo paramCallbackInfo) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0])) {
        try {
          if (!((Boolean)je.m(new Object[0]).z()).booleanValue())
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue()) {
                try {
                  if (!(paramclass_742.method_6047().method_7909() instanceof net.minecraft.class_1829))
                    return; 
                } catch (x5 x5) {
                  throw a(null);
                } 
              } else {
                return;
              } 
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    class_591 class_591 = (class_591)method_4038();
    try {
      if (paramclass_742.method_6079().method_7909() instanceof net.minecraft.class_1819)
        class_591.field_3399 = class_572.class_573.field_3409; 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PlayerEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */