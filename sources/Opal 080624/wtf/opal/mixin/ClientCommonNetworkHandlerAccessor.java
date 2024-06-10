package wtf.opal.mixin;

import net.minecraft.class_7975;
import net.minecraft.class_8673;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_8673.class})
public interface ClientCommonNetworkHandlerAccessor {
  @Accessor("worldSession")
  class_7975 getWorldSession();
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientCommonNetworkHandlerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */