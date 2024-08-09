package ez.h.ui.clickgui.options;

import ez.h.features.*;

public class OptionMode extends Option
{
    int index;
    String name;
    String value;
    public String[] values;
    
    public boolean isMode(final String s) {
        return this.getMode().equalsIgnoreCase(s);
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String[] getValues() {
        return this.values;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public OptionMode(final Feature feature, final String name, final String s, final String[] values, final int index) {
        super(feature, name, s);
        this.name = name;
        this.value = s;
        this.values = values;
        this.index = index;
        this.setMode(s);
    }
    
    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setMode(final String value) {
        this.value = value;
    }
    
    public void setValues(final String[] values) {
        this.values = values;
    }
    
    public String getMode() {
        return this.value;
    }
    
    public void setModeWithIndex(final int n) {
        this.setMode(this.values[n]);
    }
    
    public String getDes() {
        return this.name;
    }
}
