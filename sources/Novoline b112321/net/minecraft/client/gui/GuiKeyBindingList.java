package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiKeyBindingList$CategoryEntry;
import net.minecraft.client.gui.GuiKeyBindingList$KeyEntry;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended {
   private final GuiControls field_148191_k;
   private final Minecraft mc;
   private final GuiListExtended$IGuiListEntry[] listEntries;
   private int maxListLabelWidth = 0;

   public GuiKeyBindingList(GuiControls var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 63, var1.height - 32, 20);
      this.field_148191_k = var1;
      this.mc = var2;
      KeyBinding[] var3 = (KeyBinding[])ArrayUtils.clone(var2.gameSettings.keyBindings);
      this.listEntries = new GuiListExtended$IGuiListEntry[var3.length + KeyBinding.getKeybinds().size()];
      Arrays.sort(var3);
      int var4 = 0;
      String var5 = null;

      for(KeyBinding var9 : var3) {
         String var10 = var9.getKeyCategory();
         if(!var10.equals(var5)) {
            var5 = var10;
            this.listEntries[var4++] = new GuiKeyBindingList$CategoryEntry(this, var10);
         }

         int var11 = var2.fontRendererObj.d(I18n.format(var9.getKeyDescription(), new Object[0]));
         if(var11 > this.maxListLabelWidth) {
            this.maxListLabelWidth = var11;
         }

         this.listEntries[var4++] = new GuiKeyBindingList$KeyEntry(this, var9);
      }

   }

   protected int getSize() {
      return this.listEntries.length;
   }

   public GuiListExtended$IGuiListEntry getListEntry(int var1) {
      return this.listEntries[var1];
   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 15;
   }

   public int getListWidth() {
      return super.getListWidth() + 32;
   }

   static Minecraft access$100(GuiKeyBindingList var0) {
      return var0.mc;
   }

   static GuiControls access$200(GuiKeyBindingList var0) {
      return var0.field_148191_k;
   }

   static int access$300(GuiKeyBindingList var0) {
      return var0.maxListLabelWidth;
   }
}
