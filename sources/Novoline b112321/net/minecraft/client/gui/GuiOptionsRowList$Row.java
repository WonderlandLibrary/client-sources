package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings$Options;

public class GuiOptionsRowList$Row implements GuiListExtended$IGuiListEntry {
   private final Minecraft field_148325_a = Minecraft.getInstance();
   private final GuiButton field_148323_b;
   private final GuiButton field_148324_c;

   public GuiOptionsRowList$Row(GuiButton var1, GuiButton var2) {
      this.field_148323_b = var1;
      this.field_148324_c = var2;
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      if(this.field_148323_b != null) {
         this.field_148323_b.yPosition = (double)var3;
         this.field_148323_b.drawButton(this.field_148325_a, var6, var7);
      }

      if(this.field_148324_c != null) {
         this.field_148324_c.yPosition = (double)var3;
         this.field_148324_c.drawButton(this.field_148325_a, var6, var7);
      }

   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(this.field_148323_b.mousePressed(this.field_148325_a, var2, var3)) {
         if(this.field_148323_b instanceof GuiOptionButton) {
            this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), 1);
            this.field_148323_b.displayString = this.field_148325_a.gameSettings.b(GameSettings$Options.getEnumOptions(this.field_148323_b.id));
         }

         return true;
      } else if(this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, var2, var3)) {
         if(this.field_148324_c instanceof GuiOptionButton) {
            this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), 1);
            this.field_148324_c.displayString = this.field_148325_a.gameSettings.b(GameSettings$Options.getEnumOptions(this.field_148324_c.id));
         }

         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(this.field_148323_b != null) {
         this.field_148323_b.mouseReleased(var2, var3);
      }

      if(this.field_148324_c != null) {
         this.field_148324_c.mouseReleased(var2, var3);
      }

   }

   public void setSelected(int var1, int var2, int var3) {
   }
}
