package arsenic.injection.accessor;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface IMixinTimer {

    @Accessor
    float getTicksPerSecond();

}
