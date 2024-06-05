package koks.utilities.value.values;

import koks.modules.Module;
import koks.utilities.value.Value;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:22
 */
public class BooleanValue<T extends Boolean> extends Value {

    private boolean toggled;

    public BooleanValue(String name, boolean toggled, Module module) {
        setName(name);
        this.toggled = toggled;
        setModule(module);
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
