package xyz.mathax.mathaxclient.mixin;

import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {
    /*@Inject(method = "tick", at = @At("TAIL"))
    private void isPressed(boolean slowDown, float f, CallbackInfo info) {
        if (Modules.get().get(Sneak.class).doVanilla()) {
            sneaking = true;
        }
    }*/
}
