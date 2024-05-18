package us.dev.direkt.gui.font;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.internal.files.FontsFile;
import us.dev.direkt.module.internal.core.ui.InGameUI;

import java.io.IOException;

public class GuiFontPicker extends GuiScreen {
    private GuiScreen lastScreen;

    /**
     * Reference to the GameSettings object.
     */
    private final String title = "Pick Font";
    private List list;

    public GuiFontPicker(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height - 28, 150, 20, "Confirm"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 4, this.height - 28, 150, 20, "Back"));

        this.list = new List();
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                if (list.slot == 0) {
                    FontManager.usingCustomFont = false;
                    FontManager.GLOBAL_FONT.fontName = FontManager.DEFAULT_FONT_NAME;
                    InGameUI.sorted = null;
                } else {
                    FontManager.usingCustomFont = true;
                    FontManager.GLOBAL_FONT.setFont(FontManager.getSystemFonts().get(list.slot));
                }
                Direkt.getInstance().getFileManager().getFile(FontsFile.class).save();
                break;
            case 1:
                this.mc.displayGuiScreen(this.lastScreen);
                break;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private class List extends GuiSlot {
        public int slot = -1;

        public List() {
            super(GuiFontPicker.this.mc, GuiFontPicker.this.width, GuiFontPicker.this.height, 40, GuiFontPicker.this.height - 60, 16);
        }

        protected int getSize() {
            return FontManager.getSystemFonts().size();
        }

        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            this.slot = slotIndex;
            //if (isDoubleClick)
        }

        protected boolean isSelected(int slotIndex) {
            return slotIndex == this.slot;
        }

        protected void drawBackground() {
        }

        protected void drawSlot(int entryID, int x, int y, int p_180791_4_, int mouseXIn, int mouseYIn) {
            if (y > 10 && y < this.height - 60) {
                final boolean wasEnabled = FontManager.usingCustomFont;
                if (this.isSelected(entryID) && entryID != 0) {
                    String currentFont = FontManager.GLOBAL_FONT.fontName;
                    FontManager.usingCustomFont = true;
                    FontManager.GLOBAL_FONT.setFont(FontManager.getSystemFonts().get(entryID));
                    GuiFontPicker.this.fontRendererObj.drawString(FontManager.getSystemFonts().get(entryID), x + 2, y + 2, 16777215);
                    FontManager.GLOBAL_FONT.setFont(currentFont);
                    FontManager.usingCustomFont = wasEnabled;
                } else {
                    FontManager.usingCustomFont = false;
                    GuiFontPicker.this.fontRendererObj.drawString(FontManager.getSystemFonts().get(entryID), x + 2, y + 2, 16777215);
                    FontManager.usingCustomFont = wasEnabled;
                }
            }
        }
    }

}


