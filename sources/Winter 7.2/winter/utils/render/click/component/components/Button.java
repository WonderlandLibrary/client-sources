/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.click.component.components;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import winter.module.Module;
import winter.module.modules.modes.Mode;
import winter.utils.Render;
import winter.utils.render.click.component.Component;
import winter.utils.render.click.component.Frame;
import winter.utils.render.click.component.components.sub.Checkbox;
import winter.utils.render.click.component.components.sub.Keybind;
import winter.utils.render.click.component.components.sub.ModeButton;
import winter.utils.render.click.component.components.sub.Slider;
import winter.utils.render.click.component.components.sub.VisibleButton;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Button
extends Component {
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    private int height;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList();
        this.open = false;
        this.height = 14;
        int opY = offset + 14;
        if (!mod.getModes().isEmpty()) {
            this.subcomponents.add(new ModeButton(this, mod, opY));
            opY += 12;
        }
        if (!mod.getValues().isEmpty()) {
            for (Value val : mod.getValues()) {
                if (val instanceof NumberValue) {
                    NumberValue num = (NumberValue)val;
                    Slider slider = new Slider(num, this, opY);
                    this.subcomponents.add(slider);
                    opY += 12;
                }
                if (!(val instanceof BooleanValue)) continue;
                BooleanValue bool = (BooleanValue)val;
                Checkbox check = new Checkbox(bool, this, opY);
                this.subcomponents.add(check);
                opY += 12;
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
        int opY = this.offset + 13;
        for (Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 14;
        }
    }

    @Override
    public void renderComponent() {
        Render.drawBorderedRectWithoutATop((double)this.parent.getX() - 0.5, this.parent.getY() + this.offset - 2, (double)(this.parent.getX() + this.parent.getWidth()) + 0.5, this.parent.getY() + 12 + this.offset + 2 + (this.open ? this.getHeight() - 12 : 0), 0.5, -13421773, -16777216);
        Gui.drawRect(this.parent.getX() + 1, (double)(this.parent.getY() + this.offset) - 0.5, this.parent.getX() + this.parent.getWidth() - 1, (double)(this.parent.getY() + 12 + this.offset) + 0.5 + (double)(this.open ? this.getHeight() - 12 : 0), this.isHovered ? (this.mod.isEnabled() ? -15620650 : -11513776) : (this.mod.isEnabled() ? -15820860 : -12237499));
        Minecraft.getMinecraft().fontRendererObj.drawStringShadowed(this.mod.getName(), this.parent.getX() + 3, this.parent.getY() + this.offset + 2, -1);
        if (this.subcomponents.size() > 2) {
            Minecraft.getMinecraft().fontRendererObj.drawStringShadowed(this.open ? "-" : "+", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2, -1);
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 14 * this.subcomponents.size() + 13;
        }
        return 14;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x2, int y2) {
        if (x2 > this.parent.getX() && x2 < this.parent.getX() + this.parent.getWidth() && y2 > this.parent.getY() + this.offset && y2 < this.parent.getY() + 12 + this.offset) {
            return true;
        }
        return false;
    }
}

