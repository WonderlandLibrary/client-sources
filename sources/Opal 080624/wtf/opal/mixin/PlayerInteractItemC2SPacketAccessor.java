package wtf.opal.mixin;

import net.minecraft.class_2886;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2886.class})
public interface PlayerInteractItemC2SPacketAccessor {
  @Mutable
  @Accessor("sequence")
  void setSequence(int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PlayerInteractItemC2SPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */