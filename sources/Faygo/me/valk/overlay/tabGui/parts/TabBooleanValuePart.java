package me.valk.overlay.tabGui.parts;

import me.valk.Vital;
import me.valk.overlay.tabGui.TabPart;
import me.valk.utils.value.BooleanValue;

public class TabBooleanValuePart extends TabPart
{
    private BooleanValue value;
    
    public TabBooleanValuePart(final String text, final TabPanel parent, final BooleanValue value) {
        super(text, parent);
        this.value = value;
    }
    
    @Override
    public void onKeyPress(final int key) {
        if (key == 28) {
            this.value.setValue(!this.value.getValue());
            Vital.getManagers().getModDataManager().save();
        }
    }
    
    public BooleanValue getValue() {
        return this.value;
    }
}
