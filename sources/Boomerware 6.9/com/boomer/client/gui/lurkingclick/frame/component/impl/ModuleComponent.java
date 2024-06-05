package com.boomer.client.gui.lurkingclick.frame.component.impl;

import com.boomer.client.module.Module;
import com.boomer.client.utils.MouseUtil;
import com.boomer.client.utils.Printer;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.value.Value;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.utils.value.impl.ColorValue;
import com.boomer.client.utils.value.impl.EnumValue;
import com.boomer.client.utils.value.impl.NumberValue;
import com.boomer.client.gui.lurkingclick.frame.component.Component;
import com.boomer.client.gui.lurkingclick.frame.impl.CategoryFrame;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class ModuleComponent extends Component {
    private Module module;
    private boolean binding, extended;
    private float defaultHeight;
    private ArrayList<Component> components = new ArrayList<>();
    private CategoryFrame parentFrame;

    public ModuleComponent(CategoryFrame parentFrame, Module module, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
        super(module.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
        this.module = module;
        this.defaultHeight = height;
        this.parentFrame = parentFrame;
    }

    @Override
    public void initGUI() {
        super.initGUI();
        if (module.getValues().isEmpty()) return;
        float offset = getHeight();
        for (Value value : module.getValues()) {
            if (value instanceof BooleanValue) {
                components.add(new BooleanComponent((BooleanValue) value, getParentX(), getParentY(), 0, offset, getWidth(), 12));
                offset += 12;
            } else if (value instanceof NumberValue) {
                components.add(new SliderComponent((NumberValue) value, getParentX(), getParentY(), 0, offset, getWidth(), 20));
                offset += 20;
            } else if (value instanceof EnumValue) {
                components.add(new EnumComponent((EnumValue) value, getParentX(), getParentY(), 0, offset, getWidth(), 20));
                offset += 20;
            } else if (value instanceof ColorValue) {
                components.add(new ColorComponent((ColorValue) value, getParentX(), getParentY(), 0, offset, getWidth(), 15));
                offset += 15;
            }
        }
    }

    public void updatePosition(float posX, float posY) {
        setParentX(posX);
        setParentY(posY);
        setFinishedX(posX + getOffsetX());
        setFinishedY(posY + getOffsetY());
        components.forEach(component -> component.updatePosition(getFinishedX(), getFinishedY()));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, ScaledResolution scaledResolution) {
        super.drawScreen(mouseX, mouseY, partialTicks, scaledResolution);
        final boolean hoveredmain = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX(), getFinishedY(), getWidth() - (components.isEmpty() ? 0 : 20), getHeight());
        final boolean hoveredbutton = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + getWidth() - 20, getFinishedY(), 20, getHeight());
        RenderUtil.drawRect(getFinishedX(), getFinishedY(), getWidth() - (components.isEmpty() ? 0 : 20), getHeight(), hoveredmain ? new Color(0, 0, 0, 190).getRGB() : new Color(0, 0, 0, 120).getRGB());
        if (!components.isEmpty())
            RenderUtil.drawRect(getFinishedX() + getWidth() - 20, getFinishedY(), 20, getHeight(), hoveredbutton ? new Color(0, 0, 0, 190).getRGB() : new Color(0, 0, 0, 120).getRGB());
        mc.fontRendererObj.drawStringWithShadow(isBinding() ? "Press a key..." : getLabel(), (getFinishedX() + (getWidth() - (components.isEmpty() ? 0 : 20)) / 2 - mc.fontRendererObj.getStringWidth(isBinding() ? "Press a key..." : getLabel()) / 2), getFinishedY() + getHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, getModule().isEnabled() ? 0xff4F4FA8 : -1);
        if (!components.isEmpty())
            mc.fontRendererObj.drawStringWithShadow(isExtended() ? "-" : "+", getFinishedX() + getWidth() - 6.5f - mc.fontRendererObj.getStringWidth(isExtended() ? "-" : "+"), getFinishedY() + getHeight() / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, getModule().isEnabled() ? 0xff4F4FA8 : -1);
        if (isExtended()) {
            float offset = getHeight();
            for (Component component : components) {
                component.setOffsetY(offset);
                offset += component.getHeight();
            }
            components.forEach(component -> component.drawScreen(mouseX, mouseY, partialTicks, scaledResolution));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hoveredmain = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX(), getFinishedY(), getWidth() - (components.isEmpty() ? 0 : 20), getHeight());
        final boolean hoveredbutton = MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX() + getWidth() - 20, getFinishedY(), 20, getHeight());
        switch (mouseButton) {
            case 0:
                if (hoveredmain) getModule().setEnabled(!getModule().isEnabled());
                if (hoveredbutton && !components.isEmpty()) setExtended(!isExtended());
                break;
            case 2:
                if (hoveredmain) setBinding(!isBinding());
                break;
            default:
                break;
        }
        if (isExtended()) components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (isExtended()) components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (isBinding()) {
            Printer.print("Bound " + getModule().getLabel() + " to " + typedChar + "!");
            getModule().setKeybind(keyCode);
            setBinding(false);
        }
    }

    public Module getModule() {
        return module;
    }

    public boolean isBinding() {
        return binding;
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public float getAdditionHeight() {
        float additionHeight = defaultHeight;
        if (isExtended()) {
            for (Component comp : components) {
                additionHeight += comp.getHeight();
            }
        }
        return additionHeight;
    }
}
