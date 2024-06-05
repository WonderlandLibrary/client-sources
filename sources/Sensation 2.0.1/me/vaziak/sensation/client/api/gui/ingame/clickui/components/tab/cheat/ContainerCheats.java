package me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.cheat;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.Component;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.base.BaseContainer;
import me.vaziak.sensation.client.api.gui.ingame.clickui.tab.cheat.TabDefaultCheat;
import me.vaziak.sensation.utils.anthony.Draw;

/**
 * @author antja03
 */
public class ContainerCheats extends BaseContainer {

    private TabDefaultCheat parentTab;
    public Category cheatCategory;
    private int scrollIndex;

    public ContainerCheats(Interface theInterface, TabDefaultCheat parentTab, Category cheatCategory, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.parentTab = parentTab;
        this.cheatCategory = cheatCategory;
        this.scrollIndex = 0;
        double modY = y;
        for (Module cheat : Sensation.instance.cheatManager.getCheatRegistry().values()) {
            if (parentTab.getSelectedCheat() == null) {
                parentTab.setSelectedCheat(cheat);
            }
            if (cheat.getCategory().equals(cheatCategory)) {
                components.add(new ButtonCheat(theInterface, parentTab, this, cheat, x, modY, 148, 25, null));
                modY += 20;
            }
        }
    }

    public void drawComponent(double x, double y) {

        Draw.drawRectangle(x + maxWidth - 1.5, y, x + maxWidth, theInterface.getPositionY() + maxHeight, theInterface.getColor(40, 40, 40, 255));
        if (components.size() > 7) {
            double barHeight = maxHeight;
            double div = barHeight / components.size();
            if (components.size() > 7) {
                barHeight -= (components.size() - 10) * div;
            }
            double barPosition = div * scrollIndex;

            Draw.drawRectangle(x + maxWidth - 1.5, theInterface.getPositionY() + barPosition + 1, x + maxWidth, (theInterface.getPositionY() + barPosition + barHeight - 1) > theInterface.getPositionY() + theInterface.getHeight() ? theInterface.getPositionY() + theInterface.getHeight(): theInterface.getPositionY() + barPosition + barHeight - 1, theInterface.getColor(255, 0, 0, 255));
        }

        int index = 0;
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 6) {
                component.drawComponent(theInterface.getPositionX() + component.positionX, theInterface.getPositionY() + (25 * index));
                index += 1;
            }
        }
    }

    public boolean mouseButtonClicked(int button) {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 6) {
                if (component.mouseButtonClicked(button)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mouseReleased() {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 6) {
                component.mouseReleased();
            }
        }
    }

    public void mouseScrolled(final int scrollDirection) {
        if (theInterface.getCurrentFrameMouseX() < theInterface.getPositionX() + positionX || theInterface.getCurrentFrameMouseX() > theInterface.getPositionX() + maxWidth)
            return;

        if (scrollDirection == 1) {
            if (scrollIndex < components.size() - 6) {
                scrollIndex += 1;
            }
        } else {
            if (scrollIndex > 0) {
                scrollIndex -= 1;
            }
        }

        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 6) {
                component.mouseScrolled(scrollDirection);
            }
        }
    }

    public boolean handleMouseInput() {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 6) {
                if (component.handleMouseInput())
                    return true;
            }
        }
        return false;
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 6) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}
