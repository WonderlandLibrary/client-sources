package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.resources.I18n;

public class GuiKeyBindingList$CategoryEntry implements GuiListExtended$IGuiListEntry {
   private final String labelText;
   private final int labelWidth;
   final GuiKeyBindingList this$0;

   public GuiKeyBindingList$CategoryEntry(GuiKeyBindingList var1, String var2) {
      this.this$0 = var1;
      this.labelText = I18n.format(var2, new Object[0]);
      this.labelWidth = GuiKeyBindingList.access$100(var1).fontRendererObj.d(this.labelText);
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      GuiKeyBindingList.access$100(this.this$0).fontRendererObj.drawString(this.labelText, (float)(GuiKeyBindingList.access$100(this.this$0).currentScreen.width / 2 - this.labelWidth / 2), (float)(var3 + var5 - GuiKeyBindingList.access$100(this.this$0).fontRendererObj.getHeight() - 1), 16777215);
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      return false;
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public void setSelected(int var1, int var2, int var3) {
   }
}
