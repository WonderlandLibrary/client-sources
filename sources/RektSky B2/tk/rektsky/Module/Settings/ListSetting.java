package tk.rektsky.Module.Settings;

import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;

public class ListSetting extends Setting
{
    private String[] values;
    private String value;
    private String defaultValue;
    
    @Override
    public void setValue(final Object value) {
        if (value instanceof String) {
            for (final String v : this.values) {
                if (v.equalsIgnoreCase((String)value)) {
                    this.value = v;
                    return;
                }
            }
            Client.addClientChat(ChatFormatting.RED + "Invalid Value!");
        }
    }
    
    public ListSetting(final String name, final String[] values, final String defaultValue) {
        this.name = name;
        this.values = values;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public String[] getValues() {
        return this.values;
    }
    
    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}
