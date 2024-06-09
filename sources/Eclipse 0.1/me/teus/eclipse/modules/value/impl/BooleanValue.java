package me.teus.eclipse.modules.value.impl;

import lombok.Getter;
import lombok.Setter;
import me.teus.eclipse.modules.value.Value;
@Getter
@Setter
public class BooleanValue extends Value {
    public boolean enabled;
    public BooleanValue(String name, boolean enabled) {
        super(name, false);
        this.name = name;
        this.enabled = enabled;
    }

    public void toggle(){
        setEnabled(!this.enabled);
    }
}
