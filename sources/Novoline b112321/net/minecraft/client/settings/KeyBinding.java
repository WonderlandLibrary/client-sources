package net.minecraft.client.settings;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.KeyPressEvent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable {
   private static final List keybindArray = Lists.newArrayList();
   private static final IntHashMap hash = new IntHashMap();
   private static final Set keybindSet = Sets.newHashSet();
   private final String keyDescription;
   private final int keyCodeDefault;
   private final String keyCategory;
   private int keyCode;
   private boolean pressed;
   private int pressTime;

   public static void onTick(int var0) {
      KeyBinding var1 = (KeyBinding)hash.lookup(var0);
      ++var1.pressTime;
   }

   public static void setKeyBindState(int var0, boolean var1) {
      KeyBinding var2 = (KeyBinding)hash.lookup(var0);
      var2.pressed = var1;
   }

   public static void unPressAllKeys() {
      for(KeyBinding var1 : keybindArray) {
         var1.unpressKey(var1.getKeyCode());
      }

   }

   public static void resetKeyBindingArrayAndHash() {
      hash.clearMap();

      for(KeyBinding var1 : keybindArray) {
         hash.addKey(var1.keyCode, var1);
      }

   }

   public static Set getKeybinds() {
      return keybindSet;
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

   public boolean isKeyDown() {
      return this.pressed;
   }

   public void setKeyDown(boolean var1) {
      this.pressed = var1;
   }

   public String getKeyCategory() {
      return this.keyCategory;
   }

   public boolean isPressed() {
      if(this.pressTime == 0) {
         return false;
      } else {
         --this.pressTime;
         return true;
      }
   }

   private void unpressKey(int var1) {
      if(this.isKeyDown()) {
         KeyPressEvent var2 = new KeyPressEvent(var1);
         EventManager.call(var2);
         if(!var2.isCancelled()) {
            this.pressTime = 0;
            this.pressed = false;
         }
      }

   }

   public String getKeyDescription() {
      return this.keyDescription;
   }

   public int getKeyCodeDefault() {
      return this.keyCodeDefault;
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public void setKeyCode(int var1) {
      this.keyCode = var1;
   }

   public int compareTo(KeyBinding var1) {
      int var2 = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(var1.keyCategory, new Object[0]));
      var2 = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(var1.keyDescription, new Object[0]));
      return var2;
   }
}
