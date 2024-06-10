package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1309;
import net.minecraft.class_572;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.d1;
import wtf.opal.je;
import wtf.opal.k0;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_572.class})
public abstract class BipedEntityModelMixin<T extends class_1309> {
  @Final
  @Shadow
  public class_630 field_3401;
  
  private static final long a = on.a(2442906231858486109L, -6484742910175675637L, MethodHandles.lookup().lookupClass()).a(74814329131673L);
  
  @Inject(method = {"setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;animateArms(Lnet/minecraft/entity/LivingEntity;F)V", shift = At.Shift.BEFORE)})
  private void hookPreAnimateArms(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, CallbackInfo paramCallbackInfo) {
    long l = a ^ 0xE7C51EAF781L;
    int i = (int)((l ^ 0x4F115BACDEFCL) >>> 48L);
    int j = (int)((l ^ 0x4F115BACDEFCL) << 16L >>> 32L);
    int k = (int)((l ^ 0x4F115BACDEFCL) << 48L >>> 48L);
    l ^ 0x4F115BACDEFCL;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0]))
        try {
          if (((Boolean)je.w(new Object[0]).z()).booleanValue())
            try {
              if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), paramT })) {
                this.field_3401.field_3654 = this.field_3401.field_3654 * 0.5F - 0.9424779F;
                this.field_3401.field_3675 = -0.5235988F;
              } 
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\BipedEntityModelMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */