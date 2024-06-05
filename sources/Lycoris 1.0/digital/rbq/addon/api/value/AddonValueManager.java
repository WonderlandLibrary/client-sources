package digital.rbq.addon.api.value;

import com.soterdev.SoterObfuscator;
import digital.rbq.module.value.Value;
import digital.rbq.module.value.ValueManager;

import java.util.ArrayList;
import java.util.List;

public class AddonValueManager {
    private List<AddonValue> allValues;
    public AddonValueManager() {
        ValueManager.apiValues.clear();
    }

    
    public void registerValue(AddonValue value) {
        ValueManager.apiValues.add(value.getValue());
    }

    
    public List<AddonValue> getAllValues() {
        if (allValues == null) {
            List<AddonValue> values = new ArrayList<>();
            for (Value value : ValueManager.getValues()) {
                values.add(new AddonValue(value));
            }
            allValues = values;
        }
        return allValues;
    }
}
