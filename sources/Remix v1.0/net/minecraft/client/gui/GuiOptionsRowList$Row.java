package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.settings.*;

public static class Row implements IGuiListEntry
{
    private final Minecraft field_148325_a;
    private final GuiButton field_148323_b;
    private final GuiButton field_148324_c;
    
    public Row(final GuiButton p_i45014_1_, final GuiButton p_i45014_2_) {
        this.field_148325_a = Minecraft.getMinecraft();
        this.field_148323_b = p_i45014_1_;
        this.field_148324_c = p_i45014_2_;
    }
    
    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        if (this.field_148323_b != null) {
            this.field_148323_b.yPosition = y;
            this.field_148323_b.drawButton(this.field_148325_a, mouseX, mouseY);
        }
        if (this.field_148324_c != null) {
            this.field_148324_c.yPosition = y;
            this.field_148324_c.drawButton(this.field_148325_a, mouseX, mouseY);
        }
    }
    
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        if (this.field_148323_b.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)) {
            if (this.field_148323_b instanceof GuiOptionButton) {
                this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), 1);
                this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
            }
            return true;
        }
        if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, p_148278_2_, p_148278_3_)) {
            if (this.field_148324_c instanceof GuiOptionButton) {
                this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), 1);
                this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
        if (this.field_148323_b != null) {
            this.field_148323_b.mouseReleased(x, y);
        }
        if (this.field_148324_c != null) {
            this.field_148324_c.mouseReleased(x, y);
        }
    }
    
    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
}
