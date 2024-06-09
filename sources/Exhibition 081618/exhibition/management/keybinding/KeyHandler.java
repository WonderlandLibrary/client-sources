package exhibition.management.keybinding;

import exhibition.Client;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;

public class KeyHandler {
   private static final HashMap registeredKeys = new HashMap();
   private static final List activeMasks = new ArrayList();

   public static void update(boolean isInGui) {
      if (!Client.isHidden()) {
         boolean pressed = Keyboard.getEventKeyState();
         int key = Keyboard.getEventKey();
         updateMasks(key, pressed);
         if (isInGui) {
            ;
         }

         if (key != 0) {
            if (key == 1) {
               activeMasks.clear();
            }

            if (keyHasBinds(key)) {
               ArrayList set = new ArrayList();
               set.addAll((Collection)registeredKeys.get(key));
               Iterator var4 = set.iterator();

               while(var4.hasNext()) {
                  Keybind keybind = (Keybind)var4.next();
                  if (isMaskDown(keybind.getMask())) {
                     if (pressed) {
                        keybind.press();
                     } else {
                        keybind.release();
                     }
                  }
               }

            }
         }
      }
   }

   private static void updateMasks(int key, boolean pressed) {
      KeyMask[] var2 = KeyMask.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         KeyMask mask = var2[var4];
         if (!mask.equals(KeyMask.None)) {
            int[] var6 = mask.getKeys();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               int keyInList = var6[var8];
               if (keyInList == key) {
                  boolean contains = activeMasks.contains(mask);
                  if (pressed) {
                     if (!contains) {
                        activeMasks.add(mask);
                     }
                  } else if (contains) {
                     activeMasks.remove(mask);
                  }

                  return;
               }
            }
         }
      }

   }

   public static boolean isRegistered(Keybind keybind) {
      int key = keybind.getKeyInt();
      boolean hasKeys = keyHasBinds(key);
      return hasKeys && ((ArrayList)registeredKeys.get(key)).contains(keybind);
   }

   public static void register(Keybind keybind) {
      int key = keybind.getKeyInt();
      boolean hasKeys = keyHasBinds(key);
      if (hasKeys) {
         if (!((ArrayList)registeredKeys.get(key)).contains(keybind)) {
            ((ArrayList)registeredKeys.get(key)).add(keybind);
         }
      } else {
         ArrayList keyList = new ArrayList();
         keyList.add(keybind);
         registeredKeys.put(key, keyList);
      }

   }

   public static void update(Bindable owner, Keybind keybind, Keybind newBind) {
      int key = keybind.getKeyInt();
      int newKey = newBind.getKeyInt();
      boolean hasKeys = keyHasBinds(key);
      if (hasKeys) {
         Iterator var6 = ((ArrayList)registeredKeys.get(key)).iterator();

         while(var6.hasNext()) {
            Keybind regKey = (Keybind)var6.next();
            if (regKey.getBindOwner().equals(owner)) {
               regKey.update(newBind);
               return;
            }
         }
      }

   }

   public static void unregister(Bindable owner, Keybind keybind) {
      int key = keybind.getKeyInt();
      boolean hasKeys = keyHasBinds(key);
      if (hasKeys) {
         ArrayList list = (ArrayList)registeredKeys.get(key);
         int in = -1;
         int i = 0;

         for(Iterator var7 = list.iterator(); var7.hasNext(); ++i) {
            Keybind bind = (Keybind)var7.next();
            if (bind.getBindOwner().equals(owner)) {
               in = i;
            }
         }

         if (in >= 0) {
            list.remove(in);
         }
      }

   }

   public static boolean keyHasBinds(int key) {
      return registeredKeys.containsKey(key);
   }

   static boolean isMaskDown(KeyMask mask) {
      return mask == null || mask == KeyMask.None || activeMasks.contains(mask);
   }
}
