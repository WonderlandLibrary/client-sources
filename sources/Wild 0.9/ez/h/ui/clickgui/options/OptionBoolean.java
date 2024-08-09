package ez.h.ui.clickgui.options;

import ez.h.features.*;

public class OptionBoolean extends Option
{
    Feature feature;
    public boolean enabled;
    String name;
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public OptionBoolean(final Feature feature, final String name, final boolean enabled) {
        super(feature, name, enabled);
        this.name = name;
        this.feature = feature;
        this.enabled = enabled;
    }
}
