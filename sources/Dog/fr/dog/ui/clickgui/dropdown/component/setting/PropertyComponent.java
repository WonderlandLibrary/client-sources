package fr.dog.ui.clickgui.dropdown.component.setting;

import fr.dog.property.Property;
import fr.dog.ui.framework.Component;
import lombok.Getter;

import java.awt.*;

@Getter
public class PropertyComponent<T extends Property<?>> extends Component {
    protected final int index;
    protected final T property;

    protected final Color background = new Color(30, 30, 30).darker();

    public PropertyComponent(int index, T property) {
        this.index = index;
        this.property = property;
    }
}