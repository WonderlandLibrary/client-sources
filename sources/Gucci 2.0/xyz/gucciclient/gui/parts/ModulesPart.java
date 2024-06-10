package xyz.gucciclient.gui.parts;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.gui.component.*;
import xyz.gucciclient.gui.component.Component;
import xyz.gucciclient.gui.component.Frame;
import xyz.gucciclient.values.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import xyz.gucciclient.utils.*;
import xyz.gucciclient.modules.mods.render.*;

public class ModulesPart extends Component
{
    private Screen color;
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean binding;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    
    public ModulesPart(final Module mod, final Frame parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        int opY = offset + 14;
        if (!mod.getValues().isEmpty()) {
            for (final NumberValue num : mod.getValues()) {
                final SliderPart sliderPart = new SliderPart(num, this, opY);
                this.subcomponents.add(sliderPart);
                opY += 12;
            }
        }
        if (!mod.getBooleans().isEmpty()) {
            for (final BooleanValue bool : mod.getBooleans()) {
                final CheckboxPart check = new CheckboxPart(bool, this, opY);
                this.subcomponents.add(check);
                opY += 12;
            }
        }
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 12;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }
    
    @Override
    public void render() {
        if (this.isHovered && Mouse.isButtonDown(2)) {
            this.binding = true;
        }
        Gui.drawRect(this.parent.getX() + 1, this.parent.getY() - 2 + this.offset, this.parent.getX() + this.parent.getWidth() - 2, this.parent.getY() + 12 + this.offset, this.isHovered ? new Color(50, 50, 50, 150).getRGB() : new Color(15, 15, 15, 255).getRGB());
        Wrapper.drawCenteredString(this.binding ? "" : this.mod.getName(), this.parent.getX() + 35, this.parent.getY() + 1 + this.offset, this.mod.getState() ? new Color(255, 10, 10).getRGB() : new Color(150, 150, 150).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(this.binding ? ("Press a key.. " + Keyboard.getKeyName(this.mod.getKey())) : "", (float)(this.parent.getX() * 2 + 5), (float)((this.parent.getY() + this.offset) * 2 + 6), new Color(150, 150, 150).getRGB());
        GL11.glPopMatrix();
        if (!this.mod.getBooleans().isEmpty()) {
            Render.drawArrow((float)(this.parent.getX() + 65), (float)(this.parent.getY() + this.offset + 2), this.open, new Color(150, 150, 150).getRGB());
        }
        if (!this.mod.getValues().isEmpty()) {
            Render.drawArrow((float)(this.parent.getX() + 65), (float)(this.parent.getY() + this.offset + 2), this.open, new Color(150, 150, 150).getRGB());
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.render();
            }
        }
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.parent.refresh();
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 2 && this.parent.open) {
            this.binding = !this.binding;
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            final Module mod = this.mod;
            if (!Module.getModule(G0ui.class).getState()) {
                this.mod.setState(!this.mod.getState());
            }
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.binding) {
            if (key == 14) {
                this.mod.setKey(0);
                this.binding = false;
                return;
            }
            this.mod.setKey(key);
            this.binding = false;
            if (key == 42) {
                this.mod.setKey(0);
                this.binding = false;
            }
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
