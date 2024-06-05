package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiOptionsRowList$Row;
import net.minecraft.client.settings.GameSettings$Options;

public class GuiOptionsRowList extends GuiListExtended {
   private final List field_148184_k = Lists.newArrayList();

   public GuiOptionsRowList(Minecraft var1, int var2, int var3, int var4, int var5, int var6, GameSettings$Options... var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.field_148163_i = false;

      for(int var8 = 0; var8 < var7.length; var8 += 2) {
         GameSettings$Options var9 = var7[var8];
         GameSettings$Options var10 = var8 < var7.length - 1?var7[var8 + 1]:null;
         GuiButton var11 = this.a(var1, var2 / 2 - 155, 0, var9);
         GuiButton var12 = this.a(var1, var2 / 2 - 155 + 160, 0, var10);
         this.field_148184_k.add(new GuiOptionsRowList$Row(var11, var12));
      }

   }

   private GuiButton a(Minecraft var1, int var2, int var3, GameSettings$Options var4) {
      return null;
   }

   public GuiOptionsRowList$Row getListEntry(int var1) {
      return (GuiOptionsRowList$Row)this.field_148184_k.get(var1);
   }

   protected int getSize() {
      return this.field_148184_k.size();
   }

   public int getListWidth() {
      return 400;
   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 32;
   }
}
