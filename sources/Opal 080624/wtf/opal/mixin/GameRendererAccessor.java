package wtf.opal.mixin;

import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_757.class})
public interface GameRendererAccessor {
  @Invoker
  double callGetFov(class_4184 paramclass_4184, float paramFloat, boolean paramBoolean);
  
  @Invoker
  void callTiltViewWhenHurt(class_4587 paramclass_4587, float paramFloat);
  
  @Invoker
  void callBobView(class_4587 paramclass_4587, float paramFloat);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\GameRendererAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */