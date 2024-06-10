package wtf.opal.mixin;

import net.minecraft.class_276;
import net.minecraft.class_283;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import wtf.opal.lt;
import wtf.opal.x5;

@Mixin({class_283.class})
public final class PostEffectPassMixin {
  @ModifyVariable(method = {"<init>"}, at = @At("HEAD"), ordinal = 1, argsOnly = true)
  private static class_276 hookOutputFramebuffer(class_276 paramclass_276) {
    try {
      if (lt.j) {
        lt.j = false;
        return lt.t;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return paramclass_276;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PostEffectPassMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */