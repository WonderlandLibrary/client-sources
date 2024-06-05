package me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.cheat;

import java.util.ArrayList;
import java.util.Collections;

import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.Component;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.base.BaseContainer;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.cheat.TabDefaultCheat;
import me.vaziak.sensation.client.api.property.impl.*;
import me.vaziak.sensation.utils.anthony.Draw;

/**
 * @author antja03
 */
public class ContainerProperties extends BaseContainer {

    private TabDefaultCheat parentTab;
    ArrayList<Component> activeComponents;
    public Module module;
    public int scrollIndex;

    public ContainerProperties(Interface theInterface, TabDefaultCheat parentTab, Module module, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.parentTab = parentTab;
        this.module = module;
        this.scrollIndex = 0;
        double valueY = y;
        for (Value value : module.getPropertyRegistry().values()) {
            if (value instanceof BooleanProperty) {
                components.add(new PropertyCheckbox(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            } else if (value instanceof DoubleProperty) {
                components.add(new PropertySlider(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            } else if (value instanceof StringsProperty) {
                components.add(new PropertyComboBox(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            } else if (value instanceof ItemsProperty) {
                components.add(new PropertyItemSelection(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            } else if (value instanceof ColorProperty) {
                components.add(new PropertyColorPicker(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            }
        }
    }

    public void drawComponent(double x, double y) {
        if (parentTab.getSelectedCheat() != module)
            return;

        activeComponents = getActiveComponents();

        //Quick workaround for a scrolling bug
        if (scrollIndex + 6 > getActiveComponents().size()) {
            if (getActiveComponents().size() - 8 < 0) {
                scrollIndex = 0;
            } else {
                scrollIndex = getActiveComponents().size() - 8;
            }
        }

        //Scroll bar
        Draw.drawRectangle(x + maxWidth - 1.5, y, x + maxWidth, theInterface.getPositionY() + maxHeight, theInterface.getColor(40, 40, 40, 255));
        if (module.getPropertyRegistry().size() > 7) {
            double barHeight = 150;
            double div = barHeight / components.size();
            if (activeComponents.size() > 8) {
                barHeight -= (activeComponents.size() - 8) * div;
            }
            double barPosition = div * scrollIndex;

            Draw.drawRectangle(x + this.maxWidth - 2, theInterface.getPositionY() + barPosition - 1, x + this.maxWidth, theInterface.getPositionY() + barPosition + barHeight + 0.5, theInterface.getColor(110, 110, 110, 255));

        }

        //Drawing components
        double height = 20 * (activeComponents.size() - 1);
        for (int i = activeComponents.size() - 1; i > -1; i--) {
            Component component = activeComponents.get(i);
            if (component instanceof PropertyComponent) {
                PropertyComponent vComponent = (PropertyComponent) component;
                if (vComponent.getProperty().checkDependency()) {
                    if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 8) {
                        component.drawComponent(theInterface.getPositionX() + component.positionX, theInterface.getPositionY() + height - scrollIndex * 20);
                    }
                    height -= 20;
                }
            }
        }
    }

    public boolean mouseButtonClicked(int button) {
        if (parentTab.getSelectedCheat() != module)
            return false;

        ArrayList<Component> activeComponents = getActiveComponents();
        for (Component component : activeComponents) {
            if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 8) {
                if (component.mouseButtonClicked(button)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mouseReleased() {
        ArrayList<Component> activeComponents = getActiveComponents();
        for (Component component : activeComponents) {
            component.mouseReleased();
        }
    }

    public void mouseScrolled(final int scrollDirection) {
        if (parentTab.getSelectedCheat() != module)
            return;

        ArrayList<Component> activeComponents = getActiveComponents();

        for (Component component : activeComponents) {
            if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 8) {
                component.mouseScrolled(scrollDirection);

                if (component instanceof PropertyItemSelection && ((PropertyItemSelection) component).isExtended()) {
                    return;
                }
            }
        }

        if (theInterface.getCurrentFrameMouseX() < theInterface.getPositionX() + theInterface.getWidth() / 2)
            return;

        if (scrollDirection == 1) {
            if (scrollIndex < activeComponents.size() - 8) {
                scrollIndex += 1;
            }
        } else {
            if (scrollIndex > 0) {
                scrollIndex -= 1;
            }
        }
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        if (parentTab.getSelectedCheat() != module)
            return false;

        ArrayList<Component> activeComponents = getActiveComponents();
        for (Component component : activeComponents) {
            if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 8) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Component> getActiveComponents() {
        ArrayList<Component> activeComponents = new ArrayList<>();
        for (int i = this.components.size() - 1; i > -1; i--) {
            Component component = components.get(i);
            if (component instanceof PropertyComponent) {
                PropertyComponent vComponent = (PropertyComponent) component;
                if (vComponent.getProperty().checkDependency()) {
                    activeComponents.add(component);
                }
            } else {
                activeComponents.add(component);
            }
        }
        Collections.reverse(activeComponents);
        return activeComponents;
    }
}
