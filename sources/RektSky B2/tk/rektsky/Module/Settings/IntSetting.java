package tk.rektsky.Module.Settings;

import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;

public class IntSetting extends Setting
{
    private int max;
    private int min;
    private int value;
    private int defaultValue;
    
    @Override
    public void setValue(final Object value) {
        if (value instanceof Integer) {
            if ((int)value < this.min || (int)value > this.max) {
                Client.addClientChat(ChatFormatting.RED + "Invalid Value!");
                return;
            }
            this.value = (int)value;
        }
    }
    
    public IntSetting(final String name, final int min, final int max, final int defaultValue) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
    
    @Override
    public Integer getValue() {
        return this.value;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public int getMin() {
        return this.min;
    }
    
    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}
