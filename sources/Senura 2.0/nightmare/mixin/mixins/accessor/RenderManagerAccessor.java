package nightmare.mixin.mixins.accessor;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderManager.class})
public interface RenderManagerAccessor {
  @Accessor
  double getRenderPosX();
  
  @Accessor
  double getRenderPosY();
  
  @Accessor
  double getRenderPosZ();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\accessor\RenderManagerAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */