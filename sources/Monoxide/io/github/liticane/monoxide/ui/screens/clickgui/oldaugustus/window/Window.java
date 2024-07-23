package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window;

import io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.impl.*;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;

public class Window {

    private final Module module;
    private float posX, posY;
    private float width, height;
    private final float defaultWidth = 100;
    private final ArrayList<ValueComponent> components = new ArrayList<>();

    public Window(Module module, float posX, float posY) {
        this.module = module;
        this.posX = posX;
        this.posY = posY;

        this.width = defaultWidth;

        float yPos = getPosY();

        for(Value value : ValueManager.getInstance().getValues(module)) {
            if(value instanceof BooleanValue) {
                CheckboxComponent component = new CheckboxComponent((BooleanValue) value, posX, yPos, 14);
                this.components.add(component);
                yPos += component.getFinalHeight();
            } else if(value instanceof NumberValue) {
                SliderComponent component = new SliderComponent((NumberValue) value, posX, yPos, 14);
                this.components.add(component);
                yPos += component.getFinalHeight();
            } else if(value instanceof ModeValue) {
                ModeComponent component = new ModeComponent((ModeValue) value, posX, yPos, 14);
                this.components.add(component);
                yPos += component.getFinalHeight();
            } else if(value instanceof MultiBooleanValue) {
                MultiComponent component = new MultiComponent((MultiBooleanValue) value, posX, yPos, 14);
                this.components.add(component);
                yPos += component.getFinalHeight();
            }
        }

        this.height = yPos - getPosY();
    }

    public void draw(int mouseX, int mouseY) {
        RenderUtil.drawRect(posX, posY, width, height, new Color(0, 0, 0, 180).getRGB());

        boolean reset = false;
        for(Value value : ValueManager.getInstance().getValues(module)) {
            boolean found = false;
            for(ValueComponent component : this.components) {
                if(component instanceof ValueComponent) {
                    if(component.getValue() == value)
                        found = true;
                }
            }
            if(!found) {
                reset = true;
                break;
            }
        }
        for(ValueComponent component : this.components) {
            if (component instanceof ValueComponent) {
                boolean found = false;
                for(Value value : ValueManager.getInstance().getValues(module)) {
                    if(component.getValue() == value)
                        found = true;
                }
                if(!found) {
                    reset = true;
                    break;
                }
            }
        }
        if(reset)
            this.components.clear();

        float yPos1 = getPosY();
        if(this.components.isEmpty()) {
            for(Value value : ValueManager.getInstance().getValues(module)) {
                if(value instanceof BooleanValue) {
                    CheckboxComponent component = new CheckboxComponent((BooleanValue) value, posX, yPos1, 14);
                    this.components.add(component);
                    yPos1 += component.getFinalHeight();
                } else if(value instanceof NumberValue) {
                    SliderComponent component = new SliderComponent((NumberValue) value, posX, yPos1, 14);
                    this.components.add(component);
                    yPos1 += component.getFinalHeight();
                } else if(value instanceof ModeValue) {
                    ModeComponent component = new ModeComponent((ModeValue) value, posX, yPos1, 14);
                    this.components.add(component);
                    yPos1 += component.getFinalHeight();
                } else if(value instanceof MultiBooleanValue) {
                    MultiComponent component = new MultiComponent((MultiBooleanValue) value, posX, yPos1, 14);
                    this.components.add(component);
                    yPos1 += component.getFinalHeight();
                }
            }
        }

        float yPos = getPosY();
        for(ValueComponent component : this.components) {
            if(!component.getValue().isVisible())
                continue;
            component.setPosX(posX);
            component.setPosY(yPos);
            float width = component.draw(mouseX, mouseY);
            if(this.width < width && width > defaultWidth)
                this.width = width;
            component.setWidth(this.width);
            yPos += component.getFinalHeight();
        }

        this.height = yPos - getPosY();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(ValueComponent component : this.components) {
            if(!component.getValue().isVisible())
                continue;
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public Module getModule() {
        return module;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

}
