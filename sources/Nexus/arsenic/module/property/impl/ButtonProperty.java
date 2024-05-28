package arsenic.module.property.impl;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.module.property.Property;
import arsenic.utils.render.RenderInfo;

public class ButtonProperty extends Property<String> {

    //mostly here as a reminder to make this at some point in the future
    public ButtonProperty(String value) { super(value); }

    @Override
    public PropertyComponent<ButtonProperty> createComponent() {
        return new PropertyComponent<ButtonProperty>(this) {
            @Override
            protected float draw(RenderInfo ri) {

                return height;
            }
        };
    }
}