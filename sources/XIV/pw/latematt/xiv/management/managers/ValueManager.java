package pw.latematt.xiv.management.managers;

import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.value.Value;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class ValueManager extends ListManager<Value> {
    public ValueManager() {
        super(new ArrayList<>());
    }

    public Value find(String name) {
        for (Value value : contents) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
