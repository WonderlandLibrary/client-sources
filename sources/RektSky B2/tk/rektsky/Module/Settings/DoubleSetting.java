package tk.rektsky.Module.Settings;

import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;

public class DoubleSetting extends Setting
{
    private Double max;
    private Double min;
    private Double value;
    private Double defaultValue;
    
    @Override
    public void setValue(final Object value) {
        if (value instanceof Double) {
            if ((double)value < this.min || (double)value > this.max) {
                Client.addClientChat(ChatFormatting.RED + "Invalid Value!");
                return;
            }
            this.value = (Double)value;
        }
    }
    
    public DoubleSetting(final String name, final Double min, final Double max, final Double defaultValue) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
    
    @Override
    public Double getValue() {
        return this.value;
    }
    
    public Double getMax() {
        return this.max;
    }
    
    public Double getMin() {
        return this.min;
    }
    
    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}
