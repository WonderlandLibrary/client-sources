package us.dev.direkt.gui.tab.handling.handlers;

import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.handling.TabFactory;
import us.dev.direkt.gui.tab.tab.AbstractTab;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.gui.tab.tab.tabs.*;

import java.util.Map;

/**
 * @author Foundry
 */
public class PropertyTabFactory implements TabFactory<Property> {

    @Override
    public Tab<Property> parse(TabHandler handler, Property stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        if (Boolean.class.isAssignableFrom(stateObject.getType())) {
            return (Tab<Property>) (Tab) new BooleanPropertyTab(handler, (Property<Boolean>) stateObject, parent, children, container);
        } else if (Integer.class.isAssignableFrom(stateObject.getType())) {
            return (Tab<Property>) (Tab) new IntegerPropertyTab(handler, (Property<Integer>) stateObject, parent, children, container);
        } else if (Double.class.isAssignableFrom(stateObject.getType())) {
            return (Tab<Property>) (Tab) new DecimalPropertyTab(handler, (Property<Double>) stateObject, parent, children, container);
        } else if (Enum.class.isAssignableFrom(stateObject.getType())) {
            return (Tab<Property>) (Tab) new EnumPropertyTab(handler, (Property<Enum>) stateObject, parent, children, container);
        } else if (stateObject instanceof MultiProperty) {
            return (Tab<Property>) (Tab) new MultiPropertyTab(handler, (Property<Map<String, Property>>) stateObject, parent, children, container);
        } else {
            return new AbstractTab<Property>(handler, null, parent, children, container) {
                public void doInvocation() {}
                public void renderTabFront() {}
            };
        }
    }

    @Override
    public Class<? extends Property> getHandledType() {
        return Property.class;
    }
}
