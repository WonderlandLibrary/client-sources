package alphentus.gui.customhud.settings.settings;

import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class ValueManager {

    private ArrayList<Value> valueArrayList;

    public ValueManager() {
        valueArrayList = new ArrayList<>();
    }

    public final void addValue(Value value) {
        valueArrayList.add(value);
    }

    public final ArrayList<Value> getValues() {
        return valueArrayList;
    }

}