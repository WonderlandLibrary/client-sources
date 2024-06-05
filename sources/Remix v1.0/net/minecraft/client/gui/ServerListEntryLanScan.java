package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.resources.*;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry
{
    private final Minecraft field_148288_a;
    
    public ServerListEntryLanScan() {
        this.field_148288_a = Minecraft.getMinecraft();
    }
    
    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        final int var9 = y + slotHeight / 2 - this.field_148288_a.fontRendererObj.FONT_HEIGHT / 2;
        final FontRenderer fontRendererObj = this.field_148288_a.fontRendererObj;
        final String format = I18n.format("lanServer.scanning", new Object[0]);
        final GuiScreen currentScreen = this.field_148288_a.currentScreen;
        fontRendererObj.drawString(format, GuiScreen.width / 2 - this.field_148288_a.fontRendererObj.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2, var9, 16777215);
        String var10 = null;
        switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            default: {
                var10 = "O o o";
                break;
            }
            case 1:
            case 3: {
                var10 = "o O o";
                break;
            }
            case 2: {
                var10 = "o o O";
                break;
            }
        }
        final FontRenderer fontRendererObj2 = this.field_148288_a.fontRendererObj;
        final String text = var10;
        final GuiScreen currentScreen2 = this.field_148288_a.currentScreen;
        fontRendererObj2.drawString(text, GuiScreen.width / 2 - this.field_148288_a.fontRendererObj.getStringWidth(var10) / 2, var9 + this.field_148288_a.fontRendererObj.FONT_HEIGHT, 8421504);
    }
    
    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
    
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        return false;
    }
    
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
}
