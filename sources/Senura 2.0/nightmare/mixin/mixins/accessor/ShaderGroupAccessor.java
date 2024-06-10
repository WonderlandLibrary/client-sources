package nightmare.mixin.mixins.accessor;

import java.util.List;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import nightmare.utils.AccessorUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ShaderGroup.class})
public abstract class ShaderGroupAccessor implements AccessorUtils {
  @Accessor
  public abstract List<Shader> getListShaders();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\accessor\ShaderGroupAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */