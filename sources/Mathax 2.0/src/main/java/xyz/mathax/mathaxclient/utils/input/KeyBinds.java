package xyz.mathax.mathaxclient.utils.input;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.mixin.KeyBindingAccessor;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

public class KeyBinds {
    private static final String CATEGORY = MatHax.NAME;

    public static KeyBinding OPEN_GUI = new KeyBinding("key." + MatHax.ID + ".open-gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY);
    public static KeyBinding OPEN_COMMANDS = new KeyBinding("key." + MatHax.ID + ".open-commands", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, CATEGORY);

    public static KeyBinding[] apply(KeyBinding[] binds) {
        // Add category
        Map<String, Integer> categories = KeyBindingAccessor.getCategoryOrderMap();

        int highest = 0;
        for (int i : categories.values()) {
            if (i > highest) {
                highest = i;
            }
        }

        categories.put(CATEGORY, highest + 1);

        // Add key binding
        KeyBinding[] newBinds = new KeyBinding[binds.length + 2];
        System.arraycopy(binds, 0, newBinds, 0, binds.length);
        newBinds[binds.length] = OPEN_GUI;
        newBinds[binds.length + 1] = OPEN_COMMANDS;

        return newBinds;
    }

    public static int getKey(KeyBinding bind) {
        return ((KeyBindingAccessor) bind).getKey().getCode();
    }
}
