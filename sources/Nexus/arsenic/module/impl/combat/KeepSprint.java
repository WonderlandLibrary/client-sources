package arsenic.module.impl.combat;

import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;

@ModuleInfo(name = "KeepSprint", category = ModuleCategory.Combat)
public class KeepSprint extends Module {
    public final DoubleProperty value = new DoubleProperty("Reduce Value", new DoubleValue(0, 1, 0, 0.01));
    public final BooleanProperty sprint = new BooleanProperty("Sprint", false);
}
