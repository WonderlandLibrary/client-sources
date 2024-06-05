package me.satisfactory.base.hero.clickgui.component.components;

import me.satisfactory.base.module.*;
import me.satisfactory.base.hero.clickgui.component.*;
import me.satisfactory.base.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.hero.clickgui.component.components.sub.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class Button extends Component
{
    public Module mod;
    public Frame parent;
    public int offset;
    public boolean open;
    private boolean isHovered;
    private int animation;
    private ArrayList<Component> subcomponents;
    
    public Button(final Module mod, final Frame parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        int opY = offset + 12;
        if (!mod.getModes().isEmpty()) {
            this.subcomponents.add(new ModeButton(this, mod, opY));
            opY += 12;
        }
        if (Base.INSTANCE.getSettingManager().getSettingsByMod(mod) != null) {
            for (final Setting s : Base.INSTANCE.getSettingManager().getSettingsByMod(mod)) {
                if (s.isSlider()) {
                    this.subcomponents.add(new Slider(s, this, opY));
                    opY += 12;
                }
                if (s.isCheck()) {
                    this.subcomponents.add(new Checkbox(s, this, opY));
                    opY += 12;
                }
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
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
    public void renderComponent() {
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isEnabled() ? new Color(23, 30, 42).getRGB() : new Color(41, 49, 62).getRGB()) : new Color(41, 49, 62).getRGB());
        if (this.mod.isEnabled() || this.isHovered) {
            if (this.animation < this.parent.getWidth()) {
                this.animation += (this.parent.getWidth() - this.animation) / 2 + 1;
            }
        }
        else if (this.animation > 0) {
            this.animation -= (0 + this.animation) / 2;
        }
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, new Color(41, 49, 62).getRGB());
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.animation, this.parent.getY() + 12 + this.offset, new Color(23, 30, 42).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.mod.getName(), (this.parent.getX() + 3) * 2, (this.parent.getY() + this.offset + 2) * 2 + 4, new Color(229, 229, 229).getRGB());
        if (this.subcomponents.size() > 2 || !this.mod.getModes().isEmpty()) {
            Minecraft.getMinecraft().fontRendererObj.drawString(this.open ? "-" : "+", (this.parent.getX() + this.parent.getWidth() - 10) * 2, (this.parent.getY() + this.offset + 2) * 2 + 4, new Color(229, 229, 229).darker().getRGB());
        }
        GL11.glPopMatrix();
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.renderComponent();
            }
            Gui.drawRect(this.parent.getX() + 1, this.parent.getY() + this.offset + 12, this.parent.getX() + 0, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, new Color(41, 49, 62).getRGB());
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
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
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
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
