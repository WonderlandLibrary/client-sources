package net.minecraft.util;

public abstract class LazyLoadBase {
   private boolean isLoaded = false;
   private Object value;
   private static final String __OBFID = "CL_00002263";

   protected abstract Object load();

   public Object getValue() {
      if (!this.isLoaded) {
         this.isLoaded = true;
         this.value = this.load();
      }

      return this.value;
   }
}
