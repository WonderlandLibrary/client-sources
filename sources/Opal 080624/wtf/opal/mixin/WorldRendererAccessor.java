package wtf.opal.mixin;

import net.minecraft.class_4604;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_761.class})
public interface WorldRendererAccessor {
  @Accessor("frustum")
  class_4604 getFrustum();
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\WorldRendererAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */