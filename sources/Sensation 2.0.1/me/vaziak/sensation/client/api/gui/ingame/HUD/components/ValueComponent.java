package me.vaziak.sensation.client.api.gui.ingame.HUD.components;

import me.vaziak.sensation.client.api.gui.menu.components.Component;
import me.vaziak.sensation.client.api.property.impl.Value;

/**
 * @author antja03
 */
public abstract class ValueComponent extends Component {

    private Value value;

    public ValueComponent(Value value, int posX, int posY) {
        super(posX, posY);
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

}
