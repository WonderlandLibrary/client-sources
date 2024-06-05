package net.minecraft.client.gui;

import net.aHG;
import net.minecraft.client.gui.GuiSlot;

class GuiSnooper$List extends GuiSlot {
   final aHG x;

   public GuiSnooper$List(aHG var1) {
      super(var1.mc, var1.width, var1.height, 80, var1.height - 40, var1.fontRendererObj.getHeight() + 1);
      this.x = var1;
   }

   protected int getSize() {
      return aHG.a(this.x).size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
   }

   protected boolean isSelected(int var1) {
      return false;
   }

   protected void drawBackground() {
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.x.fontRendererObj.drawString((String)aHG.a(this.x).get(var1), 10.0F, (float)var3, 16777215);
      this.x.fontRendererObj.drawString((String)aHG.b(this.x).get(var1), 230.0F, (float)var3, 16777215);
   }

   protected int getScrollBarX() {
      return this.width - 10;
   }
}
