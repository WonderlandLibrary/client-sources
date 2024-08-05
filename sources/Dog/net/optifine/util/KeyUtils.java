package net.optifine.util;

import net.minecraft.client.settings.KeyBinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KeyUtils {
    public static void fixKeyConflicts(KeyBinding[] keys, KeyBinding[] keysPrio) {
        Set<Integer> set = new HashSet();

        for (KeyBinding keybinding : keysPrio) {
            set.add(keybinding.getKeyCode());
        }

        Set<KeyBinding> set1 = new HashSet(Arrays.asList(keys));
        set1.removeAll(Arrays.asList(keysPrio));

        for (KeyBinding keybinding1 : set1) {
            Integer integer = keybinding1.getKeyCode();

            if (set.contains(integer)) {
                keybinding1.setKeyCode(0);
            }
        }
    }
}
