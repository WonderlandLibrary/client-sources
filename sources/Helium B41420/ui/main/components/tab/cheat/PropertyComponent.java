package rip.helium.ui.main.components.tab.cheat;

import java.awt.*;

import rip.helium.ui.main.Interface;
import rip.helium.ui.main.components.Component;
import rip.helium.utils.font.Fonts;
import rip.helium.utils.property.abs.Property;

/**
 * @author antja03
 */
public abstract class PropertyComponent extends Component {

    private Property property;

    public PropertyComponent(Interface theInterface, Property value, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.property = value;
    }

    public abstract void drawComponent(double x, double y);

    public boolean mouseButtonClicked(int button) {
        return false;
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        return false;
    }


    public Property getProperty() {
        return property;
    }

}
