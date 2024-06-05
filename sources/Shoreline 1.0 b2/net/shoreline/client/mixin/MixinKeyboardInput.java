package net.shoreline.client.mixin;

import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.keyboard.KeyboardTickEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 *
 * @author Shoreline
 * @since 1.0
 */
@Mixin(KeyboardInput.class)
public class MixinKeyboardInput
{
    /**
     *
     * @param slowDown
     * @param f
     * @param ci
     */
    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/" +
            "client/input/KeyboardInput;sneaking:Z", shift = At.Shift.BEFORE), cancellable = true)
    private void hookTick(boolean slowDown, float f, CallbackInfo ci)
    {
        KeyboardTickEvent keyboardTickEvent = new KeyboardTickEvent();
        Shoreline.EVENT_HANDLER.dispatch(keyboardTickEvent);
        if (keyboardTickEvent.isCanceled())
        {
            ci.cancel();
        }
    }
}
