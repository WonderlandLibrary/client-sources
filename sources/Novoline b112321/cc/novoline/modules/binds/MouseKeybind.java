package cc.novoline.modules.binds;

import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import java.util.Objects;
import net.acE;

public final class MouseKeybind implements ModuleKeybind {
   private int key;
   private long lastTime;

   private MouseKeybind(int var1) {
      this.key = var1;
   }

   public static MouseKeybind of(int var0) {
      return new MouseKeybind(var0);
   }

   public boolean click() {
      String var1 = KeyboardKeybind.b();
      if(System.currentTimeMillis() > this.lastTime + 200L) {
         this.lastTime = System.currentTimeMillis();
         return true;
      } else {
         return false;
      }
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int var1) {
      this.key = var1;
   }

   public long getLastTime() {
      return this.lastTime;
   }

   public void setLastTime(long var1) {
      this.lastTime = var1;
   }

   public boolean equals(Object var1) {
      String var2 = KeyboardKeybind.b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         MouseKeybind var3 = (MouseKeybind)var1;
         return this.key == var3.key;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(this.key)});
   }

   public String toString() {
      String var1 = KeyboardKeybind.b();
      String var10000 = "MouseKeybind{key=" + this.key + ", lastTime=" + this.lastTime + '}';
      if(acE.b() == null) {
         KeyboardKeybind.b("VxYGQ");
      }

      return var10000;
   }
}
