package ez.h.ui.clickgui.options;

import ez.h.features.*;

public class Option
{
    public String name;
    Feature feature;
    public Object value;
    public boolean display;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public Option(final Feature feature, final String name, final Object value) {
        this.display = true;
        this.name = name;
        this.value = value;
        this.feature = feature;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public Feature getFeature() {
        return this.feature;
    }
}
