package ca.commencal.ware.gui.click.elements;

import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.base.ComponentType;

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
