package arsenic.module.property.impl;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.module.property.Property;
import arsenic.utils.render.RenderInfo;

public class StringProperty extends Property<String> {
    public StringProperty(String value) { super(value); }

    @Override
    public PropertyComponent<StringProperty> createComponent() {
        return new PropertyComponent<StringProperty>(this) {
            @Override
            protected float draw(RenderInfo ri) {
                return height;
            }
        };
    }
}
