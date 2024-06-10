package nightmare.mixin.mixins.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Minecraft.class})
public interface MinecraftAccessor {
  @Accessor("timer")
  Timer timer();
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\accessor\MinecraftAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */