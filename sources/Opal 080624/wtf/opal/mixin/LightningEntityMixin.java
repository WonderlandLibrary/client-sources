package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1538;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.opal.ds;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_1538.class})
public class LightningEntityMixin {
  private static final long a = on.a(-5413817497936409828L, -9005524578962812571L, MethodHandles.lookup().lookupClass()).a(113683022048587L);
  
  @Redirect(method = {"tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isClient()Z"))
  private boolean hookTick(class_1937 paramclass_1937) {
    long l1 = a ^ 0x5A379B0F2E5L;
    long l2 = l1 ^ 0x606314D6DA26L;
    try {
      if (paramclass_1937.method_8608())
        try {
          new Object[1];
          if (ds.C(new Object[] { Long.valueOf(l2) }));
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\LightningEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */