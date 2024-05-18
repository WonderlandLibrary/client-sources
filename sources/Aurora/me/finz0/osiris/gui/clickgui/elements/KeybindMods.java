package me.finz0.osiris.gui.clickgui.elements;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class KeybindMods extends Component {

    private Module mod;

    private boolean editing;

    public KeybindMods(int xPos, int yPos, int width, int height, Component component, Module mod) {

        super(xPos, yPos, width, height, ComponentType.KEYBIND, component, "");
        this.mod = mod;
    }

    @Override
    public void onUpdate() {
        if (Keyboard.getEventKeyState()) {
            if (editing) {
                if (Keyboard.getEventKey() == Keyboard.KEY_DELETE)
                    mod.setBind(-1);
                else
                    mod.setBind(Keyboard.getEventKey());
                editing = false;
            }
        }
    }


    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (x > this.getX() + Minecraft.getMinecraft().fontRenderer.getStringWidth("Key") + 6 && x < this.getX() + this.getDimension().width && y > this.getY() && y < this.getY() + this.getDimension().height) {
            editing = !editing;
        }
    }

    public Module getMod() {
        return mod;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}
