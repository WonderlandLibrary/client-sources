package me.vaziak.sensation.client.api.gui.ingame.clickui.components.tab.cheat;

import java.awt.*;

import me.vaziak.sensation.client.api.gui.ingame.clickui.Interface;
import me.vaziak.sensation.client.api.gui.ingame.clickui.components.Component;
import me.vaziak.sensation.client.api.property.impl.Value;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

/**
 * @author antja03
 */
public abstract class PropertyComponent extends Component {

    private Value property;

    public PropertyComponent(Interface theInterface, Value value, double x, double y, double width, double height) {
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

    public void drawLabel() {
        Fonts.f14.drawCenteredString(property.getId(), theInterface.getPositionX() + positionX + maxWidth / 2,
                theInterface.getPositionY() + positionY + 4, new Color(230, 230, 230, 230).getRGB());
    }

    public Value getProperty() {
        return property;
    }

}
