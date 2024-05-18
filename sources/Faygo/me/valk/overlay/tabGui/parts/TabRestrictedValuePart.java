package me.valk.overlay.tabGui.parts;

import me.valk.Vital;
import me.valk.overlay.tabGui.TabPart;
import me.valk.utils.value.RestrictedValue;

public class TabRestrictedValuePart extends TabPart
{
    private RestrictedValue value;
    
    public TabRestrictedValuePart(final String text, final TabPanel parent, final RestrictedValue value) {
        super(text, parent);
        this.value = value;
    }
    
    @Override
    public void onKeyPress(final int key) {
        if (key == 200) {
            RestrictedValue.increase(this.value);
            Vital.getManagers().getModDataManager().save();
        }
        else if (key == 208) {
            RestrictedValue.descrease(this.value);
            Vital.getManagers().getModDataManager().save();
        }
    }
    
    public RestrictedValue getValue() {
        return this.value;
    }
}
