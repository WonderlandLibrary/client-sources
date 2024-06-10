package wtf.opal.mixin;

import net.minecraft.class_310;
import net.minecraft.class_320;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_310.class})
public interface MinecraftClientAccessor {
  @Accessor
  int getItemUseCooldown();
  
  @Accessor("itemUseCooldown")
  void setItemUseCooldown(int paramInt);
  
  @Mutable
  @Accessor("session")
  void setSession(class_320 paramclass_320);
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\MinecraftClientAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */