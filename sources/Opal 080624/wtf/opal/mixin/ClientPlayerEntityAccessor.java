package wtf.opal.mixin;

import net.minecraft.class_634;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_746.class})
public interface ClientPlayerEntityAccessor {
  @Mutable
  @Accessor("networkHandler")
  void setNetworkHandler(class_634 paramclass_634);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientPlayerEntityAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */