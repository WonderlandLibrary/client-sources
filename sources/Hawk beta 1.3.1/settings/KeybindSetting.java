package eze.settings;

public class KeybindSetting extends Setting
{
    public int code;
    
    public KeybindSetting(final int code) {
        this.name = "Keybind";
        this.code = code;
    }
    
    public int getKeyCode() {
        return this.code;
    }
    
    public void setKeyCode(final int code) {
        this.code = code;
    }
}
