package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiKeyBindingList$1;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class GuiKeyBindingList$KeyEntry implements GuiListExtended$IGuiListEntry {
   private final KeyBinding keybinding;
   private final String keyDesc;
   private final GuiButton btnChangeKeyBinding;
   private final GuiButton btnReset;
   final GuiKeyBindingList this$0;

   private GuiKeyBindingList$KeyEntry(GuiKeyBindingList var1, KeyBinding var2) {
      this.this$0 = var1;
      this.keybinding = var2;
      this.keyDesc = I18n.format(var2.getKeyDescription(), new Object[0]);
      this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(var2.getKeyDescription(), new Object[0]));
      this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      boolean var9 = GuiKeyBindingList.access$200(this.this$0).buttonId == this.keybinding;
      GuiKeyBindingList.access$100(this.this$0).fontRendererObj.drawString(this.keyDesc, (float)(var2 + 90 - GuiKeyBindingList.access$300(this.this$0)), (float)(var3 + var5 / 2 - GuiKeyBindingList.access$100(this.this$0).fontRendererObj.getHeight() / 2), 16777215);
      this.btnReset.xPosition = (double)(var2 + 190);
      this.btnReset.yPosition = (double)var3;
      this.btnReset.enabled = this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault();
      this.btnReset.drawButton(GuiKeyBindingList.access$100(this.this$0), var6, var7);
      this.btnChangeKeyBinding.xPosition = (double)(var2 + 105);
      this.btnChangeKeyBinding.yPosition = (double)var3;
      this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
      boolean var10 = false;
      if(this.keybinding.getKeyCode() != 0) {
         for(KeyBinding var14 : GuiKeyBindingList.access$100(this.this$0).gameSettings.keyBindings) {
            if(var14 != this.keybinding && var14.getKeyCode() == this.keybinding.getKeyCode()) {
               var10 = true;
               break;
            }
         }
      }

      this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
      this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.access$100(this.this$0), var6, var7);
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.access$100(this.this$0), var2, var3)) {
         GuiKeyBindingList.access$200(this.this$0).buttonId = this.keybinding;
         return true;
      } else if(this.btnReset.mousePressed(GuiKeyBindingList.access$100(this.this$0), var2, var3)) {
         GuiKeyBindingList.access$100(this.this$0).gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
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

   public void setSelected(int var1, int var2, int var3) {
   }

   GuiKeyBindingList$KeyEntry(GuiKeyBindingList var1, KeyBinding var2, GuiKeyBindingList$1 var3) {
      this(var1, var2);
   }
}
