package koks.utilities.value.values;

import koks.modules.Module;
import koks.utilities.value.Value;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 10:01
 */
public class TitleValue extends Value {

    private boolean expanded;
    private Value[] objects;

    public TitleValue(String name, boolean expanded, Value[] objects, Module module) {
        setName(name);
        this.expanded = expanded;
        this.objects = objects;
        setModule(module);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Value[] getObjects() {
        return objects;
    }

    public void setObjects(Value[] objects) {
        this.objects = objects;
    }
}
