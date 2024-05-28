package arsenic.module.impl.combat;

import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;

@ModuleInfo(name = "Reach", category = ModuleCategory.Combat)
public class Reach extends Module {

    public final DoubleProperty reach = new DoubleProperty("Reach", new DoubleValue(3, 6, 3, 0.05));


    public double getReach() {
        return isEnabled() ? reach.getValue().getInput() : 3.0;
    }
}
