package ac.sapphire.client.mixin.bind;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface IKeyBindAccessor {
    @Accessor
    void setPressed(boolean pressed);
}
