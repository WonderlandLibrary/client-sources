package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.mixin.KeybindingAccessor;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindUtil {

    public static void pressKey(KeyBinding key, boolean pressed) {
        long window = C.mc.getWindow().getHandle();
        int action = pressed ? 1 : 0;

        switch (((KeybindingAccessor) key).getBoundKey().getCategory()) {
            case KEYSYM:
                C.mc.keyboard.onKey(
                    window,
                    ((KeybindingAccessor) key).getBoundKey().getCode(),
                    0,
                    action,
                    0
                );
                break;
            case SCANCODE:
                C.mc.keyboard.onKey(
                    window,
                    GLFW.GLFW_KEY_UNKNOWN,
                    ((KeybindingAccessor) key).getBoundKey().getCode(),
                    action,
                    0
                );
                break;
            case MOUSE:
                C.mc.mouse.onMouseButton(
                    window,
                    ((KeybindingAccessor) key).getBoundKey().getCode(),
                    action,
                    0
                );
                break;
            default:
                System.out.println(
                    "Unknown keybinding type: " + key.getCategory()
                );
                break;
        }
    }
}
