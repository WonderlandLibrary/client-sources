package koks.utilities.value;

import koks.modules.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:09
 */
public class ValueManager {

    private final List<Value> values = new ArrayList<>();

    public ValueManager() {
    }

    public List<Value> getValues() {
        return values;
    }

    public void addValue(Value value){
        this.values.add(value);
    }

    public Value getValue(Module module, String name) {
        for(Value value : getValues()) {
            if(value.getName().equalsIgnoreCase(name) && value.getModule() == module) {
                return value;
            }
        }
        return null;
    }
}
