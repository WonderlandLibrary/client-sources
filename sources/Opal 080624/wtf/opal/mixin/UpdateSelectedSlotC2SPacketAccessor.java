package wtf.opal.mixin;

import net.minecraft.class_2868;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2868.class})
public interface UpdateSelectedSlotC2SPacketAccessor {
  @Mutable
  @Accessor("selectedSlot")
  void setSelectedSlot(int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\UpdateSelectedSlotC2SPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */