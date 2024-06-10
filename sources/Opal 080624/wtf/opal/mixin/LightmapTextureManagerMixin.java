package wtf.opal.mixin;

import net.minecraft.class_765;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import wtf.opal.d1;
import wtf.opal.jd;
import wtf.opal.x5;

@Mixin({class_765.class})
public final class LightmapTextureManagerMixin {
  @ModifyArgs(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;setColor(III)V"))
  private void update(Args paramArgs) {
    try {
      if (((jd)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jd.class })).D(new Object[0]))
        paramArgs.set(2, Integer.valueOf(-1)); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\LightmapTextureManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */