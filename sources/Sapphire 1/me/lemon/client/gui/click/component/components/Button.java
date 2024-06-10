package me.lemon.client.gui.click.component.components;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.module.AbstractModule;
import ac.sapphire.client.module.AbstractModuleMode;
import ac.sapphire.client.property.IProperty;
import ac.sapphire.client.property.impl.ColorProperty;
import ac.sapphire.client.property.impl.primitive.BooleanProperty;
import ac.sapphire.client.property.impl.primitive.number.AbstractNumberProperty;
import ac.sapphire.client.property.impl.primitive.number.FloatProperty;
import ac.sapphire.client.property.impl.primitive.number.IntProperty;
import me.lemon.client.gui.click.component.Component;
import me.lemon.client.gui.click.component.Frame;
import me.lemon.client.gui.click.component.components.sub.Checkbox;
import me.lemon.client.gui.click.component.components.sub.Keybind;
import me.lemon.client.gui.click.component.components.sub.ModeButton;
import me.lemon.client.gui.click.component.components.sub.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Button extends Component {

    public final ArrayList<Component> subcomponents;
    private final int height;
    public AbstractModule mod;
    public Frame parent;
    public int offset;
    public boolean open;
    private boolean isHovered;

    public Button(AbstractModule mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        height = 12;

        onModeChange();
    }

    public void onModeChange() {
        subcomponents.clear();
        AtomicInteger opY = new AtomicInteger(offset + 12);
        if (!mod.getModes().isEmpty()) {
            this.subcomponents.add(new ModeButton(this, mod, opY.get()));
            opY.addAndGet(12);
        }

        addProps(Sapphire.INSTANCE.getPropertyManager().getProperties(mod), opY);

        if (!mod.getModes().isEmpty()) {
            AbstractModuleMode<?> mode = mod.getCurrentMode();
            addProps(Sapphire.INSTANCE.getPropertyManager().getProperties(mode), opY);
        }

        this.subcomponents.add(new Keybind(this, opY.get()));
    }

    private void addProps(Deque<IProperty> properties, AtomicInteger opY) {
        for (IProperty property : properties) {
            if (property instanceof AbstractNumberProperty<?>) {
                Slider slider = new Slider(number -> {
                    if (property instanceof IntProperty) {
                        ((IntProperty) property).setValue(number.intValue());
                    } else if (property instanceof FloatProperty) {
                        ((FloatProperty) property).setValue(number.floatValue());
                    }
                }, () -> ((AbstractNumberProperty<?>) property).getValue(), property.getDisplayName(), ((AbstractNumberProperty<?>) property).getMin().doubleValue(), ((AbstractNumberProperty<?>) property).getMax().doubleValue(), this, opY.get());
                this.subcomponents.add(slider);
                opY.addAndGet(12);
            } else if (property instanceof BooleanProperty) {
                Checkbox check = new Checkbox((BooleanProperty) property, this, opY.get());
                this.subcomponents.add(check);
                opY.addAndGet(12);
            } else if (property instanceof ColorProperty) {
                Slider colorSlider = new Slider(number -> {
                    Color newColor = new Color(number.intValue(), ((ColorProperty) property).getValue().getGreen(), ((ColorProperty) property).getValue().getBlue());
                    ((ColorProperty) property).setValue(newColor);
                }, () -> ((ColorProperty) property).getValue().getRed(), property.getDisplayName() + " R", 0, 255, this, opY.get());
                this.subcomponents.add(colorSlider);
                opY.addAndGet(12);

                colorSlider = new Slider(number -> {
                    Color newColor = new Color(((ColorProperty) property).getValue().getRed(), number.intValue(), ((ColorProperty) property).getValue().getBlue());
                    ((ColorProperty) property).setValue(newColor);
                }, () -> ((ColorProperty) property).getValue().getGreen(), property.getDisplayName() + " G", 0, 255, this, opY.get());
                this.subcomponents.add(colorSlider);
                opY.addAndGet(12);

                colorSlider = new Slider(number -> {
                    Color newColor = new Color(((ColorProperty) property).getValue().getRed(), ((ColorProperty) property).getValue().getGreen(), number.intValue());
                    ((ColorProperty) property).setValue(newColor);
                }, () -> ((ColorProperty) property).getValue().getBlue(), property.getDisplayName() + " B", 0, 255, this, opY.get());
                this.subcomponents.add(colorSlider);
                opY.addAndGet(12);
            }
        }
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
        AtomicInteger opY = new AtomicInteger(offset + 12);
        for (Component comp : this.subcomponents) {
            comp.setOff(opY.get());
            opY.addAndGet(12);
        }
    }

    @Override
    public void renderComponent() {
        Color nonToggledHovered = new Color(34, 34, 34, 150);
        Color toggledHovered = new Color(23, 23, 23, 150);

        Color toggledNoHover = new Color(14, 14, 14, 150);
        Color nonToggledNoHover = new Color(17, 17, 17, 150);

        int color = this.isHovered ? (this.mod.isToggled() ? toggledHovered.getRGB() : nonToggledHovered.getRGB()) : (this.mod.isToggled() ? toggledNoHover.getRGB() : nonToggledNoHover.getRGB());

        Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, color);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getDisplayName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, this.mod.isToggled() ? 0x999999 : -1);
        if (this.subcomponents.size() > 2)
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
        GL11.glPopMatrix();
        if (this.open) {
            if (!this.subcomponents.isEmpty()) {
                for (Component comp : this.subcomponents) {
                    comp.renderComponent();
                }
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return (12 * (this.subcomponents.size() + 1));
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }

        List<Component> comps = new ArrayList<>(subcomponents);
        for (Component comp : comps) {
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

    @Override
    public void onGuiClose() {
        for (Component comp : this.subcomponents) {
            comp.onGuiClose();
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
