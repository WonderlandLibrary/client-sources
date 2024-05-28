package arsenic.injection.accessor;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.settings.KeyBinding;

@Mixin(value = KeyBinding.class)
public interface IMixinKeyBinding {
/*
    @Accessor
    boolean isPressed();
    @Accessor
    void setPressed(boolean pressed);
*/
}
