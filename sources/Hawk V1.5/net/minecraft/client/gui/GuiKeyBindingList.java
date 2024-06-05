package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended {
   private static final String __OBFID = "CL_00000732";
   private final GuiControls field_148191_k;
   private int maxListLabelWidth = 0;
   private final Minecraft mc;
   private final GuiListExtended.IGuiListEntry[] listEntries;

   protected int getSize() {
      return this.listEntries.length;
   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 15;
   }

   static int access$2(GuiKeyBindingList var0) {
      return var0.maxListLabelWidth;
   }

   static GuiControls access$1(GuiKeyBindingList var0) {
      return var0.field_148191_k;
   }

   public int getListWidth() {
      return super.getListWidth() + 32;
   }

   public GuiListExtended.IGuiListEntry getListEntry(int var1) {
      return this.listEntries[var1];
   }

   public GuiKeyBindingList(GuiControls var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 63, var1.height - 32, 20);
      this.field_148191_k = var1;
      this.mc = var2;
      KeyBinding[] var3 = (KeyBinding[])ArrayUtils.clone(var2.gameSettings.keyBindings);
      this.listEntries = new GuiListExtended.IGuiListEntry[var3.length + KeyBinding.getKeybinds().size()];
      Arrays.sort(var3);
      int var4 = 0;
      String var5 = null;
      KeyBinding[] var6 = var3;
      int var7 = var3.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         KeyBinding var9 = var6[var8];
         String var10 = var9.getKeyCategory();
         if (!var10.equals(var5)) {
            var5 = var10;
            this.listEntries[var4++] = new GuiKeyBindingList.CategoryEntry(this, var10);
         }

         int var11 = var2.fontRendererObj.getStringWidth(I18n.format(var9.getKeyDescription()));
         if (var11 > this.maxListLabelWidth) {
            this.maxListLabelWidth = var11;
         }

         this.listEntries[var4++] = new GuiKeyBindingList.KeyEntry(this, var9, (Object)null);
      }

   }

   static Minecraft access$0(GuiKeyBindingList var0) {
      return var0.mc;
   }

   public class CategoryEntry implements GuiListExtended.IGuiListEntry {
      final GuiKeyBindingList this$0;
      private final int labelWidth;
      private static final String __OBFID = "CL_00000734";
      private final String labelText;

      public CategoryEntry(GuiKeyBindingList var1, String var2) {
         this.this$0 = var1;
         this.labelText = I18n.format(var2);
         this.labelWidth = GuiKeyBindingList.access$0(var1).fontRendererObj.getStringWidth(this.labelText);
      }

      public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
         return false;
      }

      public void setSelected(int var1, int var2, int var3) {
      }

      public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
      }

      public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         GuiKeyBindingList.access$0(this.this$0).fontRendererObj.drawString(this.labelText, (double)(GuiKeyBindingList.access$0(this.this$0).currentScreen.width / 2 - this.labelWidth / 2), (double)(var3 + var5 - GuiKeyBindingList.access$0(this.this$0).fontRendererObj.FONT_HEIGHT - 1), 16777215);
      }
   }

   public class KeyEntry implements GuiListExtended.IGuiListEntry {
      private final GuiButton btnChangeKeyBinding;
      private final String field_148283_c;
      private final GuiButton btnReset;
      private static final String __OBFID = "CL_00000735";
      final GuiKeyBindingList this$0;
      private final KeyBinding field_148282_b;

      private KeyEntry(GuiKeyBindingList var1, KeyBinding var2) {
         this.this$0 = var1;
         this.field_148282_b = var2;
         this.field_148283_c = I18n.format(var2.getKeyDescription());
         this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18, I18n.format(var2.getKeyDescription()));
         this.btnReset = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset"));
      }

      public void setSelected(int var1, int var2, int var3) {
      }

      public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
         if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.access$0(this.this$0), var2, var3)) {
            GuiKeyBindingList.access$1(this.this$0).buttonId = this.field_148282_b;
            return true;
         } else if (this.btnReset.mousePressed(GuiKeyBindingList.access$0(this.this$0), var2, var3)) {
            GuiKeyBindingList.access$0(this.this$0).gameSettings.setOptionKeyBinding(this.field_148282_b, this.field_148282_b.getKeyCodeDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
         } else {
            return false;
         }
      }

      public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.btnChangeKeyBinding.mouseReleased(var2, var3);
         this.btnReset.mouseReleased(var2, var3);
      }

      public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
         boolean var9 = GuiKeyBindingList.access$1(this.this$0).buttonId == this.field_148282_b;
         GuiKeyBindingList.access$0(this.this$0).fontRendererObj.drawString(this.field_148283_c, (double)(var2 + 90 - GuiKeyBindingList.access$2(this.this$0)), (double)(var3 + var5 / 2 - GuiKeyBindingList.access$0(this.this$0).fontRendererObj.FONT_HEIGHT / 2), 16777215);
         this.btnReset.xPosition = var2 + 190;
         this.btnReset.yPosition = var3;
         this.btnReset.enabled = this.field_148282_b.getKeyCode() != this.field_148282_b.getKeyCodeDefault();
         this.btnReset.drawButton(GuiKeyBindingList.access$0(this.this$0), var6, var7);
         this.btnChangeKeyBinding.xPosition = var2 + 105;
         this.btnChangeKeyBinding.yPosition = var3;
         this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.field_148282_b.getKeyCode());
         boolean var10 = false;
         if (this.field_148282_b.getKeyCode() != 0) {
            KeyBinding[] var11 = GuiKeyBindingList.access$0(this.this$0).gameSettings.keyBindings;
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               KeyBinding var14 = var11[var13];
               if (var14 != this.field_148282_b && var14.getKeyCode() == this.field_148282_b.getKeyCode()) {
                  var10 = true;
                  break;
               }
            }
         }

         if (var9) {
            this.btnChangeKeyBinding.displayString = String.valueOf((new StringBuilder()).append(EnumChatFormatting.WHITE).append("> ").append(EnumChatFormatting.YELLOW).append(this.btnChangeKeyBinding.displayString).append(EnumChatFormatting.WHITE).append(" <"));
         } else if (var10) {
            this.btnChangeKeyBinding.displayString = String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append(this.btnChangeKeyBinding.displayString));
         }

         this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.access$0(this.this$0), var6, var7);
      }

      KeyEntry(GuiKeyBindingList var1, KeyBinding var2, Object var3) {
         this(var1, var2);
      }
   }
}
