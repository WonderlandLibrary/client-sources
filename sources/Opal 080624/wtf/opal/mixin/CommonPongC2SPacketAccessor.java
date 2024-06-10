package wtf.opal.mixin;

import net.minecraft.class_6374;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_6374.class})
public interface CommonPongC2SPacketAccessor {
  @Mutable
  @Accessor("parameter")
  void setParameter(int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\CommonPongC2SPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */