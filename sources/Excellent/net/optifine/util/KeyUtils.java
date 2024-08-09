package net.optifine.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KeyUtils {
    public static void fixKeyConflicts(KeyBinding[] keys, KeyBinding[] keysPrio) {
        Set<String> set = new HashSet<>();

        for (KeyBinding keybinding : keysPrio) {
            set.add(keybinding.getTranslationKey());
        }

        Set<KeyBinding> set1 = new HashSet<>(Arrays.asList(keys));
        Arrays.asList(keysPrio).forEach(set1::remove);

        for (KeyBinding keybinding1 : set1) {
            String s = keybinding1.getTranslationKey();

            if (set.contains(s)) {
                keybinding1.bind(InputMappings.INPUT_INVALID);
            }
        }
    }
}
