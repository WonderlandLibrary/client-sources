package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry {
    private final Minecraft mc = Minecraft.getMinecraft();

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        int i = y + slotHeight / 2 - this.mc.fontRendererObj.FONT_HEIGHT / 2;
        this.mc.fontRendererObj.drawString("Searching for local servers hosted by", this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.getStringWidth("Searching for local servers hosted by") / 2, i, 16777215);
        String s;

        switch ((int) (Minecraft.getSystemTime() / 300L % 12L)) {
            case 0:
            default:
                s = "Niggers";
                break;
            case 1:
                s = "nIggers";
                break;
            case 2:
                s = "niGgers";
                break;
            case 3:
                s = "nigGers";
                break;
            case 4:
                s = "niggErs";
                break;
            case 5:
                s = "niggeRs";
                break;
            case 6:
                s = "niggerS";
                break;
            case 7:
                s = "niggeRs";
                break;
            case 8:
                s = "niggErs";
                break;
            case 9:
                s = "nigGers";
                break;
            case 10:
                s = "niGgers";
                break;
            case 11:
                s = "nIggers";
                break;
        }

        this.mc.fontRendererObj.drawString(EnumChatFormatting.RED + s, this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, i + this.mc.fontRendererObj.FONT_HEIGHT, 8421504);
    }

    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }

    /**
     * Returns true if the mouse has been pressed on this control.
     */
    public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        return false;
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }
}
