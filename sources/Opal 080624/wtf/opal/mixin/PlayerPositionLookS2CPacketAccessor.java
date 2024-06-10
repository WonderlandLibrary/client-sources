package wtf.opal.mixin;

import net.minecraft.class_2708;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2708.class})
public interface PlayerPositionLookS2CPacketAccessor {
  @Mutable
  @Accessor("x")
  void setX(double paramDouble);
  
  @Mutable
  @Accessor("y")
  void setY(double paramDouble);
  
  @Mutable
  @Accessor("z")
  void setZ(double paramDouble);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PlayerPositionLookS2CPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */