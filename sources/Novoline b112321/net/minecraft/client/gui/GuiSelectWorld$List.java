package net.minecraft.client.gui;

import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.storage.SaveFormatComparator;
import org.apache.commons.lang3.StringUtils;

class GuiSelectWorld$List extends GuiSlot {
   final GuiSelectWorld this$0;

   public GuiSelectWorld$List(GuiSelectWorld var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 32, var1.height - 64, 36);
      this.this$0 = var1;
   }

   protected int getSize() {
      return GuiSelectWorld.access$000(this.this$0).size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      GuiSelectWorld.access$102(this.this$0, var1);
      boolean var5 = GuiSelectWorld.access$100(this.this$0) >= 0 && GuiSelectWorld.access$100(this.this$0) < this.getSize();
      GuiSelectWorld.access$200(this.this$0).enabled = var5;
      GuiSelectWorld.access$300(this.this$0).enabled = var5;
      GuiSelectWorld.access$400(this.this$0).enabled = var5;
      GuiSelectWorld.access$500(this.this$0).enabled = var5;
      this.this$0.func_146615_e(var1);
   }

   protected boolean isSelected(int var1) {
      return var1 == GuiSelectWorld.access$100(this.this$0);
   }

   protected int getContentHeight() {
      return GuiSelectWorld.access$000(this.this$0).size() * 36;
   }

   protected void drawBackground() {
      this.this$0.drawDefaultBackground();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      SaveFormatComparator var7 = (SaveFormatComparator)GuiSelectWorld.access$000(this.this$0).get(var1);
      String var8 = var7.getDisplayName();
      if(StringUtils.isEmpty(var8)) {
         var8 = GuiSelectWorld.access$600(this.this$0) + " " + (var1 + 1);
      }

      String var9 = var7.getFileName();
      var9 = var9 + " (" + GuiSelectWorld.access$700(this.this$0).format(new Date(var7.getLastTimePlayed()));
      var9 = var9 + ")";
      String var10 = "";
      if(var7.requiresConversion()) {
         var10 = GuiSelectWorld.access$800(this.this$0) + " " + var10;
      } else {
         var10 = GuiSelectWorld.access$900(this.this$0)[var7.getEnumGameType().getID()];
         if(var7.isHardcoreModeEnabled()) {
            var10 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
         }

         if(var7.getCheatsEnabled()) {
            var10 = var10 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
         }
      }

      this.this$0.drawString(this.this$0.fontRendererObj, var8, var2 + 2, var3 + 1, 16777215);
      this.this$0.drawString(this.this$0.fontRendererObj, var9, var2 + 2, var3 + 12, 8421504);
      this.this$0.drawString(this.this$0.fontRendererObj, var10, var2 + 2, var3 + 12 + 10, 8421504);
   }
}
