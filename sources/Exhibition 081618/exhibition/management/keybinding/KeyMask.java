package exhibition.management.keybinding;

public enum KeyMask {
   None((int[])null),
   Shift(new int[]{42, 54}),
   Control(new int[]{29, 157}),
   Alt(new int[]{56, 184});

   private final int[] keys;

   private KeyMask(int[] keys) {
      this.keys = keys;
   }

   public int[] getKeys() {
      return this.keys;
   }

   public static KeyMask getMask(String name) {
      KeyMask[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         KeyMask mask = var1[var3];
         if (mask.name().toLowerCase().startsWith(name.toLowerCase())) {
            return mask;
         }
      }

      return None;
   }
}
