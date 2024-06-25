package net.optifine.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.settings.KeyBinding;

public class KeyUtils {
   public static void fixKeyConflicts(KeyBinding[] keys, KeyBinding[] keysPrio) {
      Set<Integer> set = new HashSet();
      KeyBinding[] var3 = keysPrio;
      int var4 = keysPrio.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         KeyBinding keybinding = var3[var5];
         set.add(keybinding.getKeyCode());
      }

      Set<KeyBinding> set1 = new HashSet(Arrays.asList(keys));
      Arrays.asList(keysPrio).forEach(set1::remove);
      Iterator var8 = set1.iterator();

      while(var8.hasNext()) {
         KeyBinding keybinding1 = (KeyBinding)var8.next();
         Integer integer = keybinding1.getKeyCode();
         if (set.contains(integer)) {
            keybinding1.setKeyCode(0);
         }
      }

   }
}
