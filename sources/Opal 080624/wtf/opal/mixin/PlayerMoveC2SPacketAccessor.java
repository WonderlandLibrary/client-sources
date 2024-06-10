package wtf.opal.mixin;

import net.minecraft.class_2828;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2828.class})
public interface PlayerMoveC2SPacketAccessor {
  @Mutable
  @Accessor("x")
  void setX(double paramDouble);
  
  @Mutable
  @Accessor("y")
  void setY(double paramDouble);
  
  @Mutable
  @Accessor("z")
  void setZ(double paramDouble);
  
  @Mutable
  @Accessor("onGround")
  void setOnGround(boolean paramBoolean);
  
  @Mutable
  @Accessor("yaw")
  void setYaw(float paramFloat);
  
  @Mutable
  @Accessor("pitch")
  void setPitch(float paramFloat);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PlayerMoveC2SPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */