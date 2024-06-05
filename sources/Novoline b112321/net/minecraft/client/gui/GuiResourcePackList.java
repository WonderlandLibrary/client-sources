package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
   protected final Minecraft mc;
   protected final List field_148204_l;

   public GuiResourcePackList(Minecraft var1, int var2, int var3, List var4) {
      super(var1, var2, var3, 32, var3 - 55 + 4, 36);
      this.mc = var1;
      this.field_148204_l = var4;
      this.field_148163_i = false;
      this.setHasListHeader(true, (int)((float)var1.fontRendererObj.getHeight() * 1.5F));
   }

   protected void drawListHeader(int var1, int var2, Tessellator var3) {
      String var4 = EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + this.getListHeader();
      this.mc.fontRendererObj.drawString(var4, (float)(var1 + this.width / 2 - this.mc.fontRendererObj.d(var4) / 2), (float)Math.min(this.top + 3, var2), 16777215);
   }

   protected abstract String getListHeader();

   public List getList() {
      return this.field_148204_l;
   }

   protected int getSize() {
      return this.getList().size();
   }

   public ResourcePackListEntry getListEntry(int var1) {
      return (ResourcePackListEntry)this.getList().get(var1);
   }

   public int getListWidth() {
      return this.width;
   }

   protected int getScrollBarX() {
      return this.right - 6;
   }
}
