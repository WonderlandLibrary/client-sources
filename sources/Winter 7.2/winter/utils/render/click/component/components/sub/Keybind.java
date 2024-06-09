/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.click.component.components.sub;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.render.click.component.Component;
import winter.utils.render.click.component.Frame;
import winter.utils.render.click.component.components.Button;

public class Keybind
extends Component {
    private boolean hovered;
    private boolean binding;
    private Button parent;
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
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Render.drawBorderedRect(this.parent.parent.getX() + 2, (double)(this.parent.parent.getY() + this.offset) + 0.5, this.parent.parent.getX() + this.parent.parent.getWidth() * 1 - 2, (double)(this.parent.parent.getY() + this.offset) + 13.5, 0.5, this.hovered ? -11513776 : -12237499, -13421773);
        Minecraft.getMinecraft().fontRendererObj.drawStringShadowed(this.binding ? "Press a key..." : "Key: " + Keyboard.getKeyName(this.parent.mod.getBind()), this.parent.parent.getX() + 5, (float)(this.parent.parent.getY() + this.offset + 2) + 1.0f, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.binding = !this.binding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (this.binding) {
            if (key == 57) {
                this.parent.mod.setBind(0);
                this.binding = false;
                return;
            }
            this.parent.mod.setBind(key);
            this.binding = false;
        }
    }

    public boolean isMouseOnButton(int x2, int y2) {
        if (x2 > this.x && x2 < this.x + 88 && y2 > this.y && y2 < this.y + 14) {
            return true;
        }
        return false;
    }
}

