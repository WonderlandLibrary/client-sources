package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiPageButtonList$GuiListEntry;

public class GuiPageButtonList$GuiButtonEntry extends GuiPageButtonList$GuiListEntry {
   private final boolean field_178941_a;

   public GuiPageButtonList$GuiButtonEntry(int var1, String var2, boolean var3, boolean var4) {
      super(var1, var2, var3);
      this.field_178941_a = var4;
   }

   public boolean func_178940_a() {
      return this.field_178941_a;
   }
}
