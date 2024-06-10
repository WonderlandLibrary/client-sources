package me.lemon.client.gui.click.component.components.sub;

import ac.sapphire.client.bind.impl.keyboard.AbstractKeyboardBinding;
import ac.sapphire.client.bind.impl.keyboard.ModuleToggleKeyboardBinding;
import ac.sapphire.client.bind.impl.mouse.AbstractMouseBinding;
import ac.sapphire.client.bind.impl.mouse.ModuleToggleMouseBinding;
import me.lemon.client.gui.click.component.Component;
import me.lemon.client.gui.click.component.components.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Keybind extends Component {

    private final Button parent;
    private boolean hovered;
    private boolean binding;
    private int offset;
    private int x;
    private int y;

    public Keybind(Button button, int offset) {
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void renderComponent() {
        Color toggledHovered = new Color(23, 23, 23, 125);

        Color toggledNoHover = new Color(14, 14, 14, 125);

        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 12, this.hovered ? toggledHovered.getRGB() : toggledNoHover.getRGB());
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, new Color(0, 0, 0, 125).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);

        String keyName = "";
        if (parent.mod.getBind() == null) {
            keyName = "Bind: NONE";
        } else if (parent.mod.getBind() instanceof AbstractKeyboardBinding) {
            keyName = "Key: " + Keyboard.getKeyName(((AbstractKeyboardBinding) parent.mod.getBind()).getKeyCode());
        } else if (parent.mod.getBind() instanceof AbstractMouseBinding) {
            keyName = "Mouse: " + Mouse.getButtonName(((AbstractMouseBinding) parent.mod.getBind()).getButton());
        }

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(binding ? "Press a key/mouse button..." : (keyName), (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && this.parent.open) {
            if (button == 0) {
                this.binding = !this.binding;
            } else if (button == 1 && !binding) {
                parent.mod.setBind(null);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (this.binding) {
            this.parent.mod.setBind(new ModuleToggleKeyboardBinding(key, parent.mod));
            binding = false;
        }
    }

    @Override
    public void onGuiClose() {
        if (binding) {
            this.binding = false;
            parent.mod.setBind(null);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (binding && mouseButton != 0 && mouseButton != 1) {
            binding = false;
            parent.mod.setBind(new ModuleToggleMouseBinding(mouseButton, parent.mod));
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}
