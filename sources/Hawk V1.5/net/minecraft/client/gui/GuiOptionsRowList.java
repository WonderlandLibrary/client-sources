package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionsRowList extends GuiListExtended {
   private final List field_148184_k = Lists.newArrayList();
   private static final String __OBFID = "CL_00000677";

   public GuiOptionsRowList(Minecraft var1, int var2, int var3, int var4, int var5, int var6, GameSettings.Options... var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.field_148163_i = false;

      for(int var8 = 0; var8 < var7.length; var8 += 2) {
         GameSettings.Options var9 = var7[var8];
         GameSettings.Options var10 = var8 < var7.length - 1 ? var7[var8 + 1] : null;
         GuiButton var11 = this.func_148182_a(var1, var2 / 2 - 155, 0, var9);
         GuiButton var12 = this.func_148182_a(var1, var2 / 2 - 155 + 160, 0, var10);
         this.field_148184_k.add(new GuiOptionsRowList.Row(var11, var12));
      }

   }

   public GuiOptionsRowList.Row func_180792_c(int var1) {
      return (GuiOptionsRowList.Row)this.field_148184_k.get(var1);
   }

   public int getListWidth() {
      return 400;
   }

   protected int getSize() {
      return this.field_148184_k.size();
   }

   public GuiListExtended.IGuiListEntry getListEntry(int var1) {
      return this.func_180792_c(var1);
   }

   private GuiButton func_148182_a(Minecraft var1, int var2, int var3, GameSettings.Options var4) {
      if (var4 == null) {
         return null;
      } else {
         int var5 = var4.returnEnumOrdinal();
         return (GuiButton)(var4.getEnumFloat() ? new GuiOptionSlider(var5, var2, var3, var4) : new GuiOptionButton(var5, var2, var3, var4, var1.gameSettings.getKeyBinding(var4)));
      }
   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 32;
   }

   public static class Row implements GuiListExtended.IGuiListEntry {
      private final GuiButton field_148323_b;
      private final GuiButton field_148324_c;
      private static final String __OBFID = "CL_00000678";
      private final Minecraft field_148325_a = Minecraft.getMinecraft();

      public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         if (this.field_148323_b != null) {
            this.field_148323_b.yPosition = var3;
            this.field_148323_b.drawButton(this.field_148325_a, var6, var7);
         }

         if (this.field_148324_c != null) {
            this.field_148324_c.yPosition = var3;
            this.field_148324_c.drawButton(this.field_148325_a, var6, var7);
         }

      }

      public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
         if (this.field_148323_b != null) {
            this.field_148323_b.mouseReleased(var2, var3);
         }

         if (this.field_148324_c != null) {
            this.field_148324_c.mouseReleased(var2, var3);
         }

      }

      public void setSelected(int var1, int var2, int var3) {
      }

      public Row(GuiButton var1, GuiButton var2) {
         this.field_148323_b = var1;
         this.field_148324_c = var2;
      }

      public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
         if (this.field_148323_b.mousePressed(this.field_148325_a, var2, var3)) {
            if (this.field_148323_b instanceof GuiOptionButton) {
               this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), 1);
               this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
            }

            return true;
         } else if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, var2, var3)) {
            if (this.field_148324_c instanceof GuiOptionButton) {
               this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), 1);
               this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
