package net.shoreline.client.impl.event.keyboard;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.shoreline.client.mixin.MixinKeyboard;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Dispatched in {@link MixinKeyboard#hookOnKey(long, int, int, int, int, CallbackInfo)}
 *
 * @author linus
 * @since 1.0
 *
 * @see MixinKeyboard
 */
@Cancelable
public class KeyboardInputEvent extends Event
{
    // The inputted keycode in GLFW format
    private final int keycode;

    /**
     *
     *
     * @param keycode
     */
    public KeyboardInputEvent(int keycode)
    {
        this.keycode = keycode;
    }

    /**
     * Returns the inputted {@link org.lwjgl.glfw.GLFW} keycode
     *
     * @return The input key
     *
     * @see #keycode
     */
    public int getKeycode()
    {
        return keycode;
    }
}
