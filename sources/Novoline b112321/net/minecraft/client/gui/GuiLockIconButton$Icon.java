package net.minecraft.client.gui;

enum GuiLockIconButton$Icon {
   LOCKED(0, 146),
   LOCKED_HOVER(0, 166),
   LOCKED_DISABLED(0, 186),
   UNLOCKED(20, 146),
   UNLOCKED_HOVER(20, 166),
   UNLOCKED_DISABLED(20, 186);

   private final int field_178914_g;
   private final int field_178920_h;
   private static final GuiLockIconButton$Icon[] $VALUES = new GuiLockIconButton$Icon[]{LOCKED, LOCKED_HOVER, LOCKED_DISABLED, UNLOCKED, UNLOCKED_HOVER, UNLOCKED_DISABLED};

   private GuiLockIconButton$Icon(int var3, int var4) {
      this.field_178914_g = var3;
      this.field_178920_h = var4;
   }

   public int func_178910_a() {
      return this.field_178914_g;
   }

   public int func_178912_b() {
      return this.field_178920_h;
   }
}
