/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Arrays;
import java.util.HashSet;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class KeyUtils {
    public static void fixKeyConflicts(KeyBinding[] keyBindingArray, KeyBinding[] keyBindingArray2) {
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < keyBindingArray2.length; ++i) {
            KeyBinding keyBinding = keyBindingArray2[i];
            hashSet.add(keyBinding.getTranslationKey());
        }
        HashSet<KeyBinding> hashSet2 = new HashSet<KeyBinding>(Arrays.asList(keyBindingArray));
        hashSet2.removeAll(Arrays.asList(keyBindingArray2));
        for (KeyBinding keyBinding : hashSet2) {
            String string = keyBinding.getTranslationKey();
            if (!hashSet.contains(string)) continue;
            keyBinding.bind(InputMappings.INPUT_INVALID);
        }
    }
}

