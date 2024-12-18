package me.finz0.osiris.gui.clickgui.elements;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.base.Container;
import me.finz0.osiris.gui.clickgui.listener.ComboBoxListener;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class ComboBox extends Container {

    public ArrayList<ComboBoxListener> listeners = new ArrayList<ComboBoxListener>();

    private String[] elements;

    private int selectedIndex;

    private boolean selected;

    public ComboBox(int xPos, int yPos, int width, int height, Component component, String text, String... elements) {

        super(xPos, yPos, width, height, ComponentType.COMBO_BOX, component, text);
        this.elements = elements;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {

        if (this.isMouseOver(x, y)) {
            if (buttonID == 1) {
                selected = !selected;
            }

            if (buttonID == 0) {
                //LogHelper.info("hu");
                int offset = getDimension().height + 2;
                String[] elements = getElements();
                for (int i = 0; i < elements.length; i++) {
                    if (i == getSelectedIndex()) {
                        continue;
                    }

                    if (y >= offset && y <= offset + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) {
                        setSelectedIndex(i);
                        setSelected(false);
                        break;
                    }

                    offset += Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2;
                }

            }
        }


    }

    public String[] getElements() {

        return elements;
    }

    public void setElements(String[] elements) {

        this.elements = elements;
        selectedIndex = 0;
    }

    public int getSelectedIndex() {

        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {

        this.selectedIndex = selectedIndex;
        for (ComboBoxListener listener : listeners) {
            listener.onComboBoxSelectionChange(this);
        }
    }

    public String getSelectedElement() {

        return elements[selectedIndex];
    }

    public boolean isSelected() {

        return selected;
    }

    public void setSelected(boolean selected) {

        this.selected = selected;
        for (ComboBoxListener listener : listeners) {
            listener.onComboBoxSelectionChange(this);
        }
    }

    public ArrayList<ComboBoxListener> getListeners() {

        return listeners;
    }

    public void addListeners(ComboBoxListener listeners) {

        this.listeners.add(listeners);
    }
}
