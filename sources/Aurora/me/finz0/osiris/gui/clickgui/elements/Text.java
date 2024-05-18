package me.finz0.osiris.gui.clickgui.elements;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentType;

public class Text extends Component {

    private String[] text;

    public Text(int xPos, int yPos, int width, int height, Component component, String[] text) {

        super(xPos, yPos, width, height, ComponentType.TEXT, component, "");
        this.text = text;
    }

    public String[] getMessage() {

        return text;
    }
}
