package wtf.opal.mixin;

import net.minecraft.class_2743;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2743.class})
public interface EntityVelocityUpdateS2CPacketAccessor {
  @Mutable
  @Accessor("velocityX")
  void setVelocityX(int paramInt);
  
  @Mutable
  @Accessor("velocityY")
  void setVelocityY(int paramInt);
  
  @Mutable
  @Accessor("velocityZ")
  void setVelocityZ(int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\EntityVelocityUpdateS2CPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */