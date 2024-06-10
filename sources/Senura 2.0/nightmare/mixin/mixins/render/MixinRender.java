package nightmare.mixin.mixins.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Render.class})
public abstract class MixinRender<T extends Entity> {
  @Shadow
  protected abstract boolean func_180548_c(T paramT);
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */