package net.minecraft.client.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable {
   private static final String __OBFID = "CL_00000628";
   private static final Set keybindSet = Sets.newHashSet();
   private final String keyDescription;
   public boolean pressed;
   private final String keyCategory;
   private final int keyCodeDefault;
   private static final List keybindArray = Lists.newArrayList();
   private static final IntHashMap hash = new IntHashMap();
   private int keyCode;
   private int pressTime;

   public static void resetKeyBindingArrayAndHash() {
      hash.clearMap();
      Iterator var0 = keybindArray.iterator();

      while(var0.hasNext()) {
         KeyBinding var1 = (KeyBinding)var0.next();
         hash.addKey(var1.keyCode, var1);
      }

   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public int compareTo(KeyBinding var1) {
      int var2 = I18n.format(this.keyCategory).compareTo(I18n.format(var1.keyCategory));
      if (var2 == 0) {
         var2 = I18n.format(this.keyDescription).compareTo(I18n.format(var1.keyDescription));
      }

      return var2;
   }

   public boolean isPressed() {
      if (this.pressTime == 0) {
         return false;
      } else {
         --this.pressTime;
         return true;
      }
   }

   public String getKeyDescription() {
      return this.keyDescription;
   }

   public KeyBinding(String var1, int var2, String var3) {
      this.keyDescription = var1;
      this.keyCode = var2;
      this.keyCodeDefault = var2;
      this.keyCategory = var3;
      keybindArray.add(this);
      hash.addKey(var2, this);
      keybindSet.add(var3);
   }

   public static void onTick(int var0) {
      if (var0 != 0) {
         KeyBinding var1 = (KeyBinding)hash.lookup(var0);
         if (var1 != null) {
            ++var1.pressTime;
         }
      }

   }

   public static Set getKeybinds() {
      return keybindSet;
   }

   public void setKeyCode(int var1) {
      this.keyCode = var1;
   }

   public int compareTo(Object var1) {
      return this.compareTo((KeyBinding)var1);
   }

   public boolean getIsKeyPressed() {
      return this.pressed;
   }

   private void unpressKey() {
      this.pressTime = 0;
      this.pressed = false;
   }

   public int getKeyCodeDefault() {
      return this.keyCodeDefault;
   }

   public static void setKeyBindState(int var0, boolean var1) {
      if (var0 != 0) {
         KeyBinding var2 = (KeyBinding)hash.lookup(var0);
         if (var2 != null) {
            var2.pressed = var1;
         }
      }

   }

   public static void unPressAllKeys() {
      Iterator var0 = keybindArray.iterator();

      while(var0.hasNext()) {
         KeyBinding var1 = (KeyBinding)var0.next();
         var1.unpressKey();
      }

   }

   public String getKeyCategory() {
      return this.keyCategory;
   }
}
