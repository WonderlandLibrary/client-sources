package net.silentclient.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.silentclient.client.Client;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.MouseCursorHandler;

import java.util.ArrayList;
import java.util.List;

public class SilentScreen extends GuiScreen {
    protected boolean defaultCursor = true;
    protected ArrayList<Input> silentInputs = new ArrayList<>();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(defaultCursor) {
            Client.getInstance().getMouseCursorHandler().enableCursor(getCursor(silentInputs, buttonList));
        }
    }

    public static MouseCursorHandler.CursorType getCursor(ArrayList<Input> silentInputs, List<GuiButton> buttonList) {
        MouseCursorHandler.CursorType cursorType = MouseCursorHandler.CursorType.NORMAL;

        for(Input input : silentInputs) {
            if(input.isHovered()) {
                cursorType = MouseCursorHandler.CursorType.EDIT_TEXT;
            }
        }

        for(GuiButton button : buttonList) {
            if(button instanceof Button && button.isMouseOver() && !(((Button) button).escMenu && Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Vanilla ESC Menu Layout").getValBoolean())) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
        }

        return cursorType;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Client.getInstance().getMouseCursorHandler().disableCursor();
    }

    public int getFpsLimit() {
        return 120;
    }
}
