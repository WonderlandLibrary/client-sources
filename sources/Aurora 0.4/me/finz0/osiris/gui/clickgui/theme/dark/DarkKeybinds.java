package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.KeybindMods;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class DarkKeybinds extends ComponentRenderer {

    public DarkKeybinds(Theme theme) {

        super(ComponentType.KEYBIND, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        KeybindMods keybind = (KeybindMods) component;

        int mainColor = ClickGuiModule.isLight ? ColourUtils.color(255, 255, 255, 255) : ColourUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGuiModule.isLight ? ColourUtils.color(0, 0, 0, 255) : ColourUtils.color(255, 255, 255, 255);

        theme.fontRenderer.drawString("Key", keybind.getX() + 4, keybind.getY() + 2,
                mainColorInv);

        int nameWidth = theme.fontRenderer.getStringWidth("Key") + 7;

        OsirisTessellator.drawRect(keybind.getX() + nameWidth, keybind.getY(), keybind.getX() + keybind.getDimension().width, keybind.getY() + 12,
                mainColor);

        if(keybind.getMod().getBind() == -1) {
            theme.fontRenderer.drawString(keybind.isEditing() ? "|" : "NONE", keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - theme.fontRenderer.getStringWidth("NONE") / 2, keybind.getY() + 2, keybind.isEditing() ?
                    mainColorInv :
                    ColourUtils.color(0.6f, 0.6f, 0.6f, 1.0f));
        }
        else
        {
            theme.fontRenderer.drawString(keybind.isEditing() ? "|" : Keyboard.getKeyName(keybind.getMod().getBind()), keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - theme.fontRenderer.getStringWidth(Keyboard.getKeyName(keybind.getMod().getBind())) / 2, keybind.getY() + 2, keybind.isEditing() ?
                    mainColorInv :
                    mainColorInv);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }

}
