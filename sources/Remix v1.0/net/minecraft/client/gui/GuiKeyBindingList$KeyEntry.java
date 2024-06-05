package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;

public class KeyEntry implements IGuiListEntry
{
    private final KeyBinding field_148282_b;
    private final String field_148283_c;
    private final GuiButton btnChangeKeyBinding;
    private final GuiButton btnReset;
    
    private KeyEntry(final KeyBinding p_i45029_2_) {
        this.field_148282_b = p_i45029_2_;
        this.field_148283_c = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
        this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18, I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
        this.btnReset = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
    }
    
    KeyEntry(final GuiKeyBindingList this$0, final KeyBinding p_i45030_2_, final Object p_i45030_3_) {
        this(this$0, p_i45030_2_);
    }
    
    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        final boolean var9 = GuiKeyBindingList.access$100(GuiKeyBindingList.this).buttonId == this.field_148282_b;
        GuiKeyBindingList.access$000(GuiKeyBindingList.this).fontRendererObj.drawString(this.field_148283_c, x + 90 - GuiKeyBindingList.access$200(GuiKeyBindingList.this), y + slotHeight / 2 - GuiKeyBindingList.access$000(GuiKeyBindingList.this).fontRendererObj.FONT_HEIGHT / 2, 16777215);
        this.btnReset.xPosition = x + 190;
        this.btnReset.yPosition = y;
        this.btnReset.enabled = (this.field_148282_b.getKeyCode() != this.field_148282_b.getKeyCodeDefault());
        this.btnReset.drawButton(GuiKeyBindingList.access$000(GuiKeyBindingList.this), mouseX, mouseY);
        this.btnChangeKeyBinding.xPosition = x + 105;
        this.btnChangeKeyBinding.yPosition = y;
        this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.field_148282_b.getKeyCode());
        boolean var10 = false;
        if (this.field_148282_b.getKeyCode() != 0) {
            for (final KeyBinding var14 : GuiKeyBindingList.access$000(GuiKeyBindingList.this).gameSettings.keyBindings) {
                if (var14 != this.field_148282_b && var14.getKeyCode() == this.field_148282_b.getKeyCode()) {
                    var10 = true;
                    break;
                }
            }
        }
        if (var9) {
            this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
        }
        else if (var10) {
            this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
        }
        this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.access$000(GuiKeyBindingList.this), mouseX, mouseY);
    }
    
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.access$000(GuiKeyBindingList.this), p_148278_2_, p_148278_3_)) {
            GuiKeyBindingList.access$100(GuiKeyBindingList.this).buttonId = this.field_148282_b;
            return true;
        }
        if (this.btnReset.mousePressed(GuiKeyBindingList.access$000(GuiKeyBindingList.this), p_148278_2_, p_148278_3_)) {
            GuiKeyBindingList.access$000(GuiKeyBindingList.this).gameSettings.setOptionKeyBinding(this.field_148282_b, this.field_148282_b.getKeyCodeDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
        this.btnChangeKeyBinding.mouseReleased(x, y);
        this.btnReset.mouseReleased(x, y);
    }
    
    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
}
