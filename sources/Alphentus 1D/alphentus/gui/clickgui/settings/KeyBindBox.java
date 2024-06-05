package alphentus.gui.clickgui.settings;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao
 * @since on 31/07/2020.
 */
public class KeyBindBox {

    private final Mod mod;
    private int x, y, width, height;
    private final FontRenderer fontRenderer = Init.getInstance().fontManager.stem19;
    private boolean settingKey;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    public KeyBindBox (Mod mod) {
        this.mod = mod;
    }

    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        String keybind = settingKey ? "..." : Keyboard.getKeyName(mod.getKeybind());
        String text = "Keybind: " + keybind;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (Init.getInstance().modManager.getModuleByClass(ClickGUI.class).modes.getSelectedCombo().equals("Sigma")) {
            Init.getInstance().fontManager.myinghei22.drawStringWithShadow(text, scaledResolution.getScaledWidth() / 2 - 140, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);
        } else {
            Gui.drawRect(x, y, x + fontRenderer.getStringWidth(text) + 4, y + height, new Color(42, 42, 49, 240).getRGB());
            fontRenderer.drawStringWithShadow(text, x + 1, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, -1);
        }
    }

    public void keyTyped (char typedChar, int keyCode) {
        if (!settingKey)
            return;
        int newValue = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
        if (keyCode == Keyboard.KEY_BACK) {
            mod.setKeybind(Keyboard.KEY_NONE);
        } else if (keyCode == Keyboard.KEY_RSHIFT) {
            mod.setKeybind(Keyboard.KEY_RSHIFT);
        } else {
            mod.setKeybind(newValue);
        }
        this.settingKey = false;
    }

    public void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.settingKey = true;
        }

    }

    public void setPosition (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public boolean isHovering (int mouseX, int mouseY) {
        String keybind = settingKey ? "..." : Keyboard.getKeyName(mod.getKeybind());
        String text = "Keybind: " + keybind;
        return mouseX > x && mouseX < x + fontRenderer.getStringWidth(text) + 4 && mouseY > y && mouseY < y + height;
    }

}
