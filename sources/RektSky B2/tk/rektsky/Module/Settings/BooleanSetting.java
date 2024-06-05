package tk.rektsky.Module.Settings;

import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;

public class BooleanSetting extends Setting
{
    private boolean value;
    private boolean defaultValue;
    
    @Override
    public void setValue(final Object value) {
        if (value instanceof String) {
            if (((String)value).equalsIgnoreCase("true")) {
                this.value = true;
            }
            else if (((String)value).equalsIgnoreCase("false")) {
                this.value = false;
            }
            else {
                Client.addClientChat(ChatFormatting.RED + "Invalid Value!");
            }
        }
        if (value instanceof Boolean) {
            this.value = (boolean)value;
        }
    }
    
    public BooleanSetting(final String name, final Boolean defaultValue) {
        this.name = name;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }
    
    @Override
    public Boolean getValue() {
        return this.value;
    }
    
    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}
