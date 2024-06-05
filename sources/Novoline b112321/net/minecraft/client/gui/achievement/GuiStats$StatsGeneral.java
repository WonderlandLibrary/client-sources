package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

class GuiStats$StatsGeneral extends GuiSlot {
   final GuiStats this$0;

   public GuiStats$StatsGeneral(GuiStats var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 32, var1.height - 64, 10);
      this.this$0 = var1;
      this.setShowSelectionBox(false);
   }

   protected int getSize() {
      return StatList.generalStats.size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
   }

   protected boolean isSelected(int var1) {
      return false;
   }

   protected int getContentHeight() {
      return this.getSize() * 10;
   }

   protected void drawBackground() {
      this.this$0.drawDefaultBackground();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      StatBase var7 = (StatBase)StatList.generalStats.get(var1);
      this.this$0.drawString(GuiStats.access$1300(this.this$0), var7.getStatName().getUnformattedText(), var2 + 2, var3 + 1, var1 % 2 == 0?16777215:9474192);
      String var8 = var7.format(GuiStats.access$100(this.this$0).readStat(var7));
      this.this$0.drawString(GuiStats.access$1400(this.this$0), var8, var2 + 2 + 213 - GuiStats.access$1500(this.this$0).d(var8), var3 + 1, var1 % 2 == 0?16777215:9474192);
   }
}
