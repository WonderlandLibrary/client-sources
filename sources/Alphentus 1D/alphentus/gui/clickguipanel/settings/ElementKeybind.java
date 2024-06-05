package alphentus.gui.clickguipanel.settings;

import alphentus.gui.clickguipanel.panel.Element;
import alphentus.init.Init;
import alphentus.mod.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class ElementKeybind extends Element {

    private boolean isChoosingKeyBind;
    private ScaledResolution sr;
    private Mod mod;

    public ElementKeybind(Mod mod) {
        this.mod = mod;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        sr = new ScaledResolution(mc);

        String key = Keyboard.getKeyName(mod.getKeybind());
        Gui.drawRect(x, y, x + width, y + height, 0xFF202020);
        fr.drawStringWithShadow("KeyBind: " + (isChoosingKeyBind ? "... (" + key + ")" : key), x + width / 2 - fr.getStringWidth("KeyBind: " + (isChoosingKeyBind ? "... (" + key + ")" : key)) / 2, y + height / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!isChoosingKeyBind)
            return;

        int newValue = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

        if (keyCode == Keyboard.KEY_BACK) {
            mod.setKeybind(Keyboard.KEY_NONE);
        } else if (keyCode == Keyboard.KEY_RSHIFT) {
            mod.setKeybind(Keyboard.KEY_RSHIFT);
        } else {
            mod.setKeybind(newValue);
        }

        isChoosingKeyBind = false;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            isChoosingKeyBind = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

}