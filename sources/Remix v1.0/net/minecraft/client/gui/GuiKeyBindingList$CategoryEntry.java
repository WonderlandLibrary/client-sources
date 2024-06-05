package net.minecraft.client.gui;

import net.minecraft.client.resources.*;

public class CategoryEntry implements IGuiListEntry
{
    private final String labelText;
    private final int labelWidth;
    
    public CategoryEntry(final String p_i45028_2_) {
        this.labelText = I18n.format(p_i45028_2_, new Object[0]);
        this.labelWidth = GuiKeyBindingList.access$000(GuiKeyBindingList.this).fontRendererObj.getStringWidth(this.labelText);
    }
    
    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        final FontRenderer fontRendererObj = GuiKeyBindingList.access$000(GuiKeyBindingList.this).fontRendererObj;
        final String labelText = this.labelText;
        final GuiScreen currentScreen = GuiKeyBindingList.access$000(GuiKeyBindingList.this).currentScreen;
        fontRendererObj.drawString(labelText, GuiScreen.width / 2 - this.labelWidth / 2, y + slotHeight - GuiKeyBindingList.access$000(GuiKeyBindingList.this).fontRendererObj.FONT_HEIGHT - 1, 16777215);
    }
    
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        return false;
    }
    
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
    
    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
}
