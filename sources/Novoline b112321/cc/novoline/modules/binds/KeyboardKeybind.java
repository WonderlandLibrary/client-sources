package cc.novoline.modules.binds;

import cc.novoline.modules.binds.ModuleKeybind;
import java.util.Objects;

public final class KeyboardKeybind implements ModuleKeybind {
   private int key;
   private static String b;

   private KeyboardKeybind(int var1) {
      this.key = var1;
   }

   public static KeyboardKeybind of(int var0) {
      return new KeyboardKeybind(var0);
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int var1) {
      this.key = var1;
   }

   public boolean equals(Object var1) {
      String var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         KeyboardKeybind var3 = (KeyboardKeybind)var1;
         return this.key == var3.key;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(this.key)});
   }

   public String toString() {
      return "KeyboardKeybind{key=" + this.key + '}';
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("FcIPUc");
      }

   }
}
